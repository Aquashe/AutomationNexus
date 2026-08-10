package com.automationnexus.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.*;

/**
 * ObjectScaffold — Auto generates and manages JSON object repository.
 *
 * Rules:
 * ✅ Auto detects platform from package name
 * ✅ Auto scans all page classes
 * ✅ Creates JSON if not exists
 * ✅ Adds new objects in ascending order
 * ✅ Never overwrites existing values
 *
 * @author AutomationNexus
 */
public class ObjectScaffold {

    private static final Logger log =
            LogManager.getLogger(ObjectScaffold.class);

    // Default placeholder XPath
    private static final String DEFAULT_XPATH = "//*[1=2]";
    private static final String EMPTY = "";

    // Base paths
    private static final String OBJECT_REPO_BASE =
            "src/test/resources/objectrepository/";
    private static final String PAGES_BASE =
            "src/main/java/com/automationnexus/pages/";

    // Package identifiers
    private static final String MOBILE_PACKAGE = "pages.mobile";
    private static final String WEB_PACKAGE    = "pages.web";

    // Platform enum
    public enum PageType { MOBILE, WEB }

    /**
     *
     * Scans all page classes → detects platform →
     * creates/updates JSON files automatically
     *
     */
    public static void scaffold() {
        log.info("========================================");
        log.info("AutomationNexus — ObjectScaffold START");
        log.info("========================================");

        // Step 1 — Scan all page classes automatically
        List<Class<?>> allPageClasses = scanAllPageClasses();

        if (allPageClasses.isEmpty()) {
            log.warn("No page classes found!");
            return;
        }

        log.info("Found " + allPageClasses.size()
                + " page classes total");

        // Step 2 — Process each page class
        for (Class<?> pageClass : allPageClasses) {
            processPageClass(pageClass);
        }

        log.info("========================================");
        log.info("ObjectScaffold COMPLETED!");
        log.info("========================================");
    }

    // =========================================================
    // AUTO SCAN PAGE CLASSES
    // =========================================================

    /**
     * Automatically scans all classes under pages/ package
     */
    private static List<Class<?>> scanAllPageClasses() {
        List<Class<?>> pageClasses = new ArrayList<>();
        scanPackage("com.automationnexus.pages.mobile",
                pageClasses);
        scanPackage("com.automationnexus.pages.web",
                pageClasses);
        return pageClasses;
    }

    /**
     * Scans a package for page classes
     */
    private static void scanPackage(String packageName,
                                    List<Class<?>> result) {
        String path = PAGES_BASE
                + packageName
                .replace("com.automationnexus.pages.", "")
                .replace(".", "/");

        File dir = new File(path);
        if (!dir.exists()) {
            log.warn("Package path not found: " + path);
            return;
        }
        scanDirectory(dir, packageName, result);
    }

    /**
     * Recursively scans directory for page classes
     */
    private static void scanDirectory(File dir,
                                      String packageName,
                                      List<Class<?>> result) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file,
                        packageName + "." + file.getName(),
                        result);
            } else if (file.getName().endsWith(".java")) {
                String className = packageName + "."
                        + file.getName()
                        .replace(".java", "");
                try {
                    Class<?> clazz = Class.forName(className);
                    result.add(clazz);
                    log.info("Found: " + className);
                } catch (ClassNotFoundException e) {
                    log.warn("Could not load: " + className);
                }
            }
        }
    }

    // =========================================================
    // PROCESS EACH PAGE CLASS
    // =========================================================

    /**
     * Processes a single page class
     */
    private static void processPageClass(Class<?> pageClass) {
        String className = pageClass.getSimpleName();
        PageType pageType = detectPlatform(pageClass);

        log.info("----------------------------------------");
        log.info("Processing : " + className);
        log.info("Platform   : " + pageType);
        log.info("----------------------------------------");

        // Get all getObject methods sorted ascending
        List<String> objectNames = getObjectMethods(pageClass);

        if (objectNames.isEmpty()) {
            log.warn("No getObject...() methods in: "
                    + className);
            return;
        }

        // Get JSON file
        String filePath = getJsonFilePath(pageClass);
        File jsonFile = new File(filePath);

        if (!jsonFile.exists()) {
            // Create new JSON
            new File(jsonFile.getParent()).mkdirs();
            createJsonFile(jsonFile, className,
                    objectNames, pageType);
        } else {
            // Update — add new objects only
            addNewObjectsOnly(jsonFile, objectNames, pageType);
        }
    }

    // =========================================================
    // PLATFORM DETECTION
    // =========================================================

    /**
     * Auto detects platform from package name
     * pages.mobile → MOBILE
     * pages.web    → WEB
     */
    private static PageType detectPlatform(Class<?> pageClass) {
        String pkg = pageClass.getPackageName();
        if (pkg.contains(MOBILE_PACKAGE)) {
            return PageType.MOBILE;
        } else if (pkg.contains(WEB_PACKAGE)) {
            return PageType.WEB;
        }
        log.warn("Platform unclear for: "
                + pageClass.getSimpleName()
                + " — defaulting to MOBILE");
        return PageType.MOBILE;
    }

    // =========================================================
    // JSON FILE OPERATIONS
    // =========================================================

    /**
     * Creates a fresh JSON file
     */
    private static void createJsonFile(File file,
                                       String className,
                                       List<String> objectNames,
                                       PageType pageType) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"pageName\": \"")
                .append(className).append("\",\n");
        json.append("  \"objects\": {\n");

        for (int i = 0; i < objectNames.size(); i++) {
            json.append(buildObjectEntry(
                    objectNames.get(i), pageType));
            if (i < objectNames.size() - 1) json.append(",");
            json.append("\n");
        }

        json.append("  }\n}");
        writeFile(file, json.toString());
        log.info("✅ Created: " + file.getPath());
    }

    /**
     * Adds only NEW objects to existing JSON
     * Sorted ascending
     * Never touches existing values
     */
    private static void addNewObjectsOnly(File file,
                                          List<String> objectNames,
                                          PageType pageType) {
        try {
            String existing = new String(
                    Files.readAllBytes(file.toPath()));

            // Find which objects are new
            List<String> newObjects = new ArrayList<>();
            for (String name : objectNames) {
                if (existing.contains("\"" + name + "\"")) {
                    log.info("  Exists  — skip : " + name);
                } else {
                    log.info("  New     — add  : " + name);
                    newObjects.add(name);
                }
            }

            if (newObjects.isEmpty()) {
                log.info("No new objects — file unchanged ✅");
                return;
            }

            // Sort ascending
            Collections.sort(newObjects);

            // Build new entries
            StringBuilder additions = new StringBuilder();
            for (String obj : newObjects) {
                additions.append(",\n")
                        .append(buildObjectEntry(obj, pageType));
            }

            // Insert before closing of objects block
            String updated = existing.replace(
                    "  }\n}",
                    additions + "\n  }\n}");

            writeFile(file, updated);
            log.info("✅ Updated: " + file.getPath());

        } catch (IOException e) {
            log.error("Failed to update: "
                    + file.getPath(), e);
        }
    }

    // =========================================================
    // HELPER METHODS
    // =========================================================

    /**
     * Gets object methods sorted ascending
     */
    private static List<String> getObjectMethods(Class<?> pageClass) {
        List<String> names = new ArrayList<>();
        for (Method m : pageClass.getDeclaredMethods()) {
            if (m.getName().startsWith("getObject")) {
                names.add(m.getName().substring(3));
            }
        }
        Collections.sort(names);
        return names;
    }

    /**
     * Builds single object JSON entry
     * Mobile → android + ios = //*[1=2], web = ""
     * Web    → web = //*[1=2], android + ios = ""
     */
    private static String buildObjectEntry(String objectName,
                                           PageType pageType) {
        String android = pageType == PageType.MOBILE
                ? DEFAULT_XPATH : EMPTY;
        String ios     = pageType == PageType.MOBILE
                ? DEFAULT_XPATH : EMPTY;
        String web     = pageType == PageType.WEB
                ? DEFAULT_XPATH : EMPTY;

        return "    \"" + objectName + "\": {\n"
                + "      \"android\": \"" + android + "\",\n"
                + "      \"ios\": \""     + ios     + "\",\n"
                + "      \"web\": \""     + web     + "\"\n"
                + "    }";
    }

    /**
     * Gets JSON file path for a page class
     */
    private static String getJsonFilePath(Class<?> pageClass) {
        String classPath = pageClass.getPackageName()
                .replace("com.automationnexus.pages.", "")
                .replace(".", "/");
        return OBJECT_REPO_BASE + classPath + "/"
                + pageClass.getSimpleName() + ".json";
    }

    /**
     * Writes content to file
     */
    private static void writeFile(File file, String content) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            log.error("Failed to write: "
                    + file.getPath(), e);
        }
    }


    // =========================================================
    // 🔍 CALLER DETECTION — makes zero-argument findTestObject() work
    // =========================================================

    /**
     * Simple holder for the two pieces BasePage actually needs.
     */
    public static class CallerInfo {
        public final String className;
        public final String methodName;

        public CallerInfo(String className, String methodName) {
            this.className = className;
            this.methodName = methodName;
        }
    }

    /**
     * Walks the current thread's call stack to find which page class
     * getter method triggered findTestObject(). This is what lets
     * findTestObject() work with ZERO arguments — the method figures
     * out "who called me" instead of being told explicitly.
     *
     * @return CallerInfo with className + methodName, or null if
     *         no matching page-class getter was found on the stack
     *         (e.g. called directly from a test class, not a page class)
     */
    public static CallerInfo findCallerInfo() {
        StackTraceElement[] stackTrace =
                Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();
            String methodName = element.getMethodName();

            // Skip frames that belong to OUR OWN framework internals —
            // we only care about the actual page class method
            if (className.equals(ObjectScaffold.class.getName())
                    || className.contains("com.automationnexus.base")) {
                continue;
            }

            // Found it — a page class, calling a getter
            if (className.contains("com.automationnexus.pages")
                    && methodName.startsWith("get")) {

                // Strip package down to simple class name
                // e.g. "com.automationnexus.pages.mobile.login.LoginPage"
                //   -> "LoginPage"
                String simpleClassName = className.substring(
                        className.lastIndexOf('.') + 1);

                return new CallerInfo(simpleClassName, methodName);
            }
        }

        // Nothing matched — caller didn't come from a recognized page class
        return null;
    }
}