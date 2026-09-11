package com.automationnexus.maintenance;

import com.automationnexus.drivers.DriverManager;
import com.automationnexus.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * MaintenanceModeHandler — Shows the interactive R/I/X/S/O/L/RL/AI menu
 * when an element cannot be found on screen within the maintenance
 * timeout, giving the tester a chance to fix the locator live instead
 * of the test just failing outright.
 *
 * Called directly from BasePage.applyMaintenanceCheck() — the ROOT of
 * maintenance mode. BasePage.findTestObject() already confirms
 * GlobalVariable.MAINTAINENCE_MODE is on before ever calling this class,
 * so no separate enabled-check happens here. pageClass/objectName are
 * passed directly as parameters (not fished out of a ThreadLocal),
 * since this is invoked at the exact moment/place of failure.
 *
 * "Ignore" (I) does not fake success — it rethrows the original
 * TimeoutException, letting whatever's further up decide what
 * happens next.
 *
 * @author AutomationNexus
 */
public class MaintenanceModeHandler {

    private static final Logger log =
            LogManager.getLogger(MaintenanceModeHandler.class);

    private static final String IGNORE_FILE = ".mntcignore";
    private static final Set<String> ignoredObjects = loadIgnoredObjects();

    /**
     * Main entry point — called from BasePage the moment an element
     * isn't found within the maintenance timeout.
     *
     * @param pageClass       the page class the object belongs to
     *                        (may be null for cross-page path lookups —
     *                        RL/O degrade gracefully in that case)
     * @param objectName      the object's name (e.g. "ObjectButtonLogin")
     * @param locator         the original By that failed to resolve
     * @param driver          the live AppiumDriver, for screenshot/page-source
     * @param wait            a WebDriverWait to reuse for retries
     * @param originalFailure the TimeoutException that triggered this,
     *                        rethrown if the user chooses to Ignore
     *
     *  @return the working By locator if R/RL eventually succeeds — either
     *          the original locator (confirmed present via R) or a new one
     *         (via RL). Callers should re-resolve a fresh WebElement from
     *        this locator themselves when they actually need to interact
     *        with it — this method only confirms/repairs the LOCATOR,
     *         it does not hand back a live element.
     *
     * @throws RuntimeException (the original failure) if ignored or
     *                          the object is listed in .mntcignore
     */
    public static By
    handle(Class<?> pageClass,
                                    String objectName,
                                    By locator,
                                    WebDriver driver,
                                    WebDriverWait wait,
                                    RuntimeException originalFailure) {

        String objectId = (pageClass != null)
                ? pageClass.getSimpleName() + "." + objectName
                : "UnknownPage." + objectName;

        if (objectName != null && ignoredObjects.contains(objectName)) {
            log.warn(objectId + " is in .mntcignore — skipping maintenance menu");
            throw originalFailure;
        }

        SoundUtils.playWavFile("notification.wav");

        while (true) {
            String choice = showMenu(objectId);

            if (choice.equalsIgnoreCase("R")) {
                if(tryFind(locator, wait) != null)
                    return locator;
                ConsoleUtils.printLine("Still not found — showing menu again.",
                        AnsiColors.WARNING);
                continue;
            }

            if (choice.equalsIgnoreCase("I")) {
                ConsoleUtils.printLine(
                        "Ignoring — original failure will propagate from here.",
                        AnsiColors.WARNING);
                throw originalFailure;
            }

            if (choice.equalsIgnoreCase("X")) {
                dumpPageSource(driver);
                continue;
            }

            if (choice.equalsIgnoreCase("S")) {
                takeAndOpenScreenshot(driver);
                continue;
            }

            if (choice.equalsIgnoreCase("O")) {
                openObjectJsonFile(pageClass);
                continue;
            }

            if (choice.equalsIgnoreCase("L")) {
                printCurrentLocator(pageClass, objectName);
                continue;
            }

            if (choice.toUpperCase().startsWith("RL:")) {
                if (pageClass == null) {
                    ConsoleUtils.printLine(
                            "Cannot update — no page class context available "
                                    + "for this object.", AnsiColors.ERROR);
                    continue;
                }
                By newLocator = By.xpath(choice.substring(3).trim());
                String platform = DriverManager.getExecutionOS();
                ObjectRepository.updateXPath(pageClass, objectName, platform, newLocator.toString().replace("By.xpath: ", ""));
                ConsoleUtils.printLine("Locator updated. Retrying...",
                        AnsiColors.SUCCESS);
                if (tryFind(newLocator, wait) != null){
                    ConsoleUtils.printLine("Updated Xpath:\t"+newLocator.toString().replace("By.xpath: ", "")+" found successfully.",
                            AnsiColors.BRIGHT_GREEN);
                    return newLocator;
                }
                ConsoleUtils.printLine("New locator also not found — showing menu again.",
                        AnsiColors.WARNING);
                continue;
            }

            if (choice.equalsIgnoreCase("AI")) {
                ConsoleUtils.printLine(
                        "AI self-healing is not implemented yet (planned for Phase 4).",
                        AnsiColors.MUTED);
                continue;
            }

            ConsoleUtils.printLine("Invalid option — please try again.",
                    AnsiColors.ERROR);
        }
    }

    // =========================================================
    // MENU DISPLAY
    // =========================================================

    private static String showMenu(String objectId) {
        ConsoleUtils.printLine("");
        ConsoleUtils.printLine("========================================", AnsiColors.INFO);
        ConsoleUtils.printLine("           MAINTENANCE OPTIONS", AnsiColors.INFO);
        ConsoleUtils.printLine("----------------------------------------", AnsiColors.INFO);
        ConsoleUtils.printLine("  R  - Retry finding the object");
        ConsoleUtils.printLine("  I  - Ignore and continue");
        ConsoleUtils.printLine("  X  - Get the page source XML");
        ConsoleUtils.printLine("  S  - Take a screenshot");
        ConsoleUtils.printLine("  O  - Open object JSON file");
        ConsoleUtils.printLine("  L  - Print the current locator");
        ConsoleUtils.printLine("  RL:<xpath> - Replace locator with your input");
        ConsoleUtils.printLine("  AI - Use AI to detect element (coming Phase 4)",
                AnsiColors.HIGHLIGHT);
        ConsoleUtils.printLine("========================================", AnsiColors.INFO);
        ConsoleUtils.print("Object: " + objectId + "\nEnter your selection: ",
                AnsiColors.MUTED);

        Scanner scanner = ScannerSingleton.getInstance();
        return scanner.nextLine().trim();
    }

    // =========================================================
    // MENU ACTIONS
    // =========================================================

    private static WebElement tryFind(By locator, WebDriverWait wait) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            return null;
        }
    }

    private static void dumpPageSource(WebDriver driver) {
        try {
            String source = driver.getPageSource();
            File file = new File("PageSource_" + System.currentTimeMillis() + ".xml");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(source);
            }
            ConsoleUtils.printLine("Page source saved: " + file.getPath(),
                    AnsiColors.SUCCESS);
            openFile(file);
        } catch (Exception e) {
            log.error("Failed to dump page source", e);
        }
    }

    private static void takeAndOpenScreenshot(WebDriver driver) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            File file = new File("Screenshot_" + System.currentTimeMillis() + ".png");
            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(screenshot);
            }
            ConsoleUtils.printLine("Screenshot saved: " + file.getPath(),
                    AnsiColors.SUCCESS);
            openFile(file);
        } catch (Exception e) {
            log.error("Failed to take screenshot", e);
        }
    }

    private static void openObjectJsonFile(Class<?> pageClass) {
        if (pageClass == null) {
            ConsoleUtils.printLine(
                    "No page class context available to open a file.",
                    AnsiColors.WARNING);
            return;
        }
        String classPath = pageClass.getPackageName()
                .replace("com.automationnexus.pages.", "")
                .replace(".", "/");
        File file = new File("src/test/resources/objectrepository/"
                + classPath + "/" + pageClass.getSimpleName() + ".json");
        openFile(file);
    }

    private static void printCurrentLocator(Class<?> pageClass, String objectName) {
        if (pageClass == null) {
            ConsoleUtils.printLine(
                    "No page class context available for this object.",
                    AnsiColors.WARNING);
            return;
        }
        try {
            String platform = DriverManager.getExecutionOS();
            String xpath = ObjectRepository.getXPath(pageClass, objectName, platform);
            ConsoleUtils.printLine("Current XPath [" + platform + "]: " + xpath,
                    AnsiColors.INFO);
        } catch (Exception e) {
            ConsoleUtils.printLine("Could not read current locator: " + e.getMessage(),
                    AnsiColors.ERROR);
        }
    }

    private static void openFile(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception e) {
            log.warn("Could not open file: " + file.getPath());
        }
    }

    // =========================================================
    // STARTUP LOADER
    // =========================================================

    private static Set<String> loadIgnoredObjects() {
        Set<String> result = new HashSet<>();
        File file = new File(IGNORE_FILE);
        if (!file.exists()) return result;
        try {
            for (String line : Files.readAllLines(file.toPath())) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) result.add(trimmed);
            }
        } catch (Exception e) {
            log.warn("Could not read .mntcignore", e);
        }
        return result;
    }

    private MaintenanceModeHandler() {
    }
}