package com.automationnexus.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

/**
 * ObjectRepository — Reads XPath locators out of the JSON files
 * created by ObjectScaffold.
 *
 * Two ways to look something up:
 *  1. getXPath(Class, objectName, platform)
 *     — builds the exact file path from the page class's own package,
 *       used when the calling page class is already known (Versions
 *       1, 3, 4 of findTestObject())
 *
 *  2. getXPathByPath(fullPath, platform)
 *     — resolves an explicit path string like
 *       "mobile/login/LoginPage/ObjectButtonLogin", used when a
 *       developer wants to reference an object from a DIFFERENT
 *       page class than the one currently executing (Version 2)
 *
 * Both are unambiguous by design — neither relies on searching for
 * "any file with this name," which could silently collide if two
 * page classes happen to share a class name.
 *
 * @author AutomationNexus
 */
public class ObjectRepository {

    private static final Logger log =
            LogManager.getLogger(ObjectRepository.class);

    private static final String OBJECT_REPO_BASE =
            "src/test/resources/objectrepository/";

    private static final String PLACEHOLDER_XPATH = "//*[1=2]";

    /**
     * Looks up an XPath using the page class itself — the exact file
     * path is derived directly from the class's own package, so there
     * is no ambiguity even if another page class elsewhere shares the
     * same simple name.
     *
     * @param pageClass  the actual page class (e.g. LoginPage.class)
     * @param objectName the object key inside the JSON
     * @param platform   "android" / "ios" / "web"
     */
    public static String getXPath(Class<?> pageClass,
                                  String objectName,
                                  String platform) {
        String classPath = pageClass.getPackageName()
                .replace("com.automationnexus.pages.", "")
                .replace(".", "/");

        String filePath = OBJECT_REPO_BASE + classPath + "/"
                + pageClass.getSimpleName() + ".json";

        return readAndExtract(new File(filePath), objectName, platform);
    }

    /**
     * Looks up an XPath using an explicit full path string, e.g.
     * "mobile/login/LoginPage/ObjectButtonLogin" — used when a
     * developer deliberately references an object belonging to a
     * DIFFERENT page class than the one currently executing.
     *
     * @param fullPath  e.g. "mobile/login/LoginPage/ObjectButtonLogin"
     * @param platform  "android" / "ios" / "web"
     */
    public static String getXPathByPath(String fullPath, String platform) {
        int lastSlash = fullPath.lastIndexOf('/');
        if (lastSlash == -1) {
            throw new RuntimeException(
                    "Invalid object path — expected format like "
                            + "'mobile/login/LoginPage/ObjectButtonLogin', got: "
                            + fullPath);
        }

        String classPath = fullPath.substring(0, lastSlash);   // mobile/login/LoginPage
        String objectName = fullPath.substring(lastSlash + 1); // ObjectButtonLogin

        String filePath = OBJECT_REPO_BASE + classPath + ".json";

        return readAndExtract(new File(filePath), objectName, platform);
    }


    public static String getXPathFromFile(String filePath,
                                          String objectName,
                                          String platform) {
        return readAndExtract(new File(filePath), objectName, platform);
    }

    // =========================================================
    // SHARED READ + EXTRACT LOGIC
    // =========================================================

    private static String readAndExtract(File jsonFile,
                                         String objectName,
                                         String platform) {
        if (!jsonFile.exists()) {
            throw new RuntimeException(
                    "Object repository JSON not found at: "
                            + jsonFile.getPath()
                            + ". Run ObjectScaffold.scaffold() first.");
        }

        String content = readFile(jsonFile);
        String xpath = extractXPath(content, objectName, platform);

        if (xpath == null) {
            throw new RuntimeException(
                    "Object '" + objectName + "' not found in "
                            + jsonFile.getPath());
        }

        if (xpath.isEmpty() || xpath.equals(PLACEHOLDER_XPATH)) {
            throw new RuntimeException(
                    "XPath not set for '" + objectName + "' ["
                            + platform + "] in " + jsonFile.getPath()
                            + " — still a placeholder. Please add the real locator.");
        }

        return xpath;
    }

    private static String readFile(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to read object repository file: "
                            + file.getPath(), e);
        }
    }

    private static String extractXPath(String content,
                                       String objectName,
                                       String platform) {
        int objIndex = content.indexOf("\"" + objectName + "\"");
        if (objIndex == -1) return null;

        int platformIndex = content.indexOf("\"" + platform + "\"", objIndex);
        if (platformIndex == -1) return null;

        int colonIndex = content.indexOf(":", platformIndex);
        int valueStart = content.indexOf("\"", colonIndex + 1) + 1;
        int valueEnd = content.indexOf("\"", valueStart);

        return content.substring(valueStart, valueEnd);
    }

    /**
     * Updates the XPath for a specific object/platform, permanently,
     * in the same JSON file getXPath() reads from. Used by
     * MaintenanceModeHandler's "RL:" (replace locator) option.
     */
    public static void updateXPath(Class<?> pageClass, String objectName,
                                   String platform, String newXPath) {
        String classPath = pageClass.getPackageName()
                .replace("com.automationnexus.pages.", "")
                .replace(".", "/");
        String filePath = OBJECT_REPO_BASE + classPath + "/"
                + pageClass.getSimpleName() + ".json";

        File jsonFile = new File(filePath);
        if (!jsonFile.exists()) {
            throw new RuntimeException("Cannot update — file not found: " + filePath);
        }

        try {
            String content = new String(Files.readAllBytes(jsonFile.toPath()));

            // Find the specific "platform": "oldValue" entry for this object
            int objIndex = content.indexOf("\"" + objectName + "\"");
            int platformIndex = content.indexOf("\"" + platform + "\"", objIndex);
            int colonIndex = content.indexOf(":", platformIndex);
            int valueStart = content.indexOf("\"", colonIndex + 1);
            int valueEnd = content.indexOf("\"", valueStart + 1);

            String updated = content.substring(0, valueStart + 1)
                    + newXPath
                    + content.substring(valueEnd);

            try (FileWriter writer = new FileWriter(jsonFile)) {
                writer.write(updated);
            }

            log.info("Updated " + objectName + " [" + platform + "] in " + filePath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update XPath in " + filePath, e);
        }
    }

    private ObjectRepository() {
    }
}