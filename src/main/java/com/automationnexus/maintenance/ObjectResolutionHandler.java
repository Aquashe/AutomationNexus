package com.automationnexus.maintenance;

import com.automationnexus.utils.ConsoleUtils;
import com.automationnexus.utils.ObjectRepository;
import com.automationnexus.utils.ScannerSingleton;
import com.automationnexus.utils.SoundUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * ObjectResolutionHandler — Handles Point A: the object/JSON file itself
 * cannot be found in the object repository (typo, deleted file, deleted
 * entry, forgot to run ObjectScaffold.scaffold()).
 *
 * This is unconditional — runs regardless of maintenance.enabled and
 * ignores .mntcignore, because it's a framework data-integrity problem,
 * not a "is the element on screen" problem. There is no valid way to
 * proceed without a resolvable object reference.
 *
 * Loops: buzzer -> ask for corrected file path -> check object exists
 * in that file -> repeat until found. Does NOT persist anything to disk.
 *
 * @author AutomationNexus
 */
public class ObjectResolutionHandler {

    private static final Logger log =
            LogManager.getLogger(ObjectResolutionHandler.class);

    /**
     * Called when the ORIGINAL page-class-based lookup fails
     * (Versions 1/4 of findTestObject() — we know the objectName,
     * just not a working file for it).
     */
    public static String recover(String objectName, String platform) {
        return promptLoop(objectName, platform);
    }

    /**
     * Called when an EXPLICIT full path lookup fails
     * (Versions 2/3 of findTestObject()). We still only need the
     * object name to check inside whatever corrected file the user gives.
     */
    public static String recoverFromPath(String originalFullPath, String platform) {
        String objectName = originalFullPath.contains("/")
                ? originalFullPath.substring(originalFullPath.lastIndexOf('/') + 1)
                : originalFullPath;
        return promptLoop(objectName, platform);
    }

    private static String promptLoop(String objectName, String platform) {
        Scanner scanner = ScannerSingleton.getInstance();

        while (true) {
            SoundUtils.playWavFile("error.wav");
            ConsoleUtils.printLine(
                    "Object '" + objectName + "' not found in the object repository.",
                    com.automationnexus.utils.AnsiColors.ERROR);
            ConsoleUtils.print(
                    "Please provide the correct object repository JSON path "
                            + "(e.g. objectrepository/mobile/saucelabs/login/LoginPage.json): ",
                    com.automationnexus.utils.AnsiColors.WARNING);

            String correctedFilePath = scanner.nextLine().trim();

            try {
                String xpath = ObjectRepository.getXPathFromFile(
                        correctedFilePath, objectName, platform);
                ConsoleUtils.printLine("✅ Object found — continuing.",
                        com.automationnexus.utils.AnsiColors.SUCCESS);
                return xpath;
            } catch (RuntimeException e) {
                ConsoleUtils.printLine("Still not found: " + e.getMessage(),
                        com.automationnexus.utils.AnsiColors.ERROR);
                // loop repeats
            }
        }
    }

    private ObjectResolutionHandler() {
    }
}