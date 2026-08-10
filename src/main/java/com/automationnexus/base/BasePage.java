package com.automationnexus.base;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.constants.ConfigKeys;
import com.automationnexus.constants.GlobalVariable;
import com.automationnexus.drivers.MobileDriverManager;
import com.automationnexus.enums.FailureHandling;
import com.automationnexus.maintenance.MaintenanceModeHandler;
import com.automationnexus.maintenance.ObjectResolutionHandler;
import com.automationnexus.utils.ObjectRepository;
import com.automationnexus.utils.ObjectScaffold;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * BasePage class provides common methods for interacting with mobile elements.
 * It serves as a base class for all page objects in the mobile automation framework.
 *
 * @author Automation Nexus
 */
public abstract class BasePage {
    protected static final Logger log = LogManager.getLogger(BasePage.class);
    protected static Mobile mobile;
    protected AppiumDriver driver;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        mobile = new Mobile(driver);
    }


    // =========================================================
    // 📋 LOGGING METHODS
    // =========================================================

    protected static void logInfo(String message) {
        log.info(message);
    }

    protected static void markError(String message) {
        log.error("[ERROR] " + message);
    }

    protected static void markWarning(String message) {
        log.warn("[WARNING] " + message);
    }

    protected static void markPassed(String message) {
        log.info("[PASSED] ✅ " + message);
    }

    protected static void markFailed(String message) {
        log.error("[FAILED] ❌ " + message);
    }

    protected static void markFailedAndStop(String message) {
        log.error("[FAILED - STOPPING] ❌ " + message);
        throw new RuntimeException(message);
    }

    protected static void markErrorAndStop(String message) {
        log.error("[ERROR - STOPPING] ❌ " + message);
        throw new RuntimeException(message);
    }


    // =========================================================
    // ⚙️ FAILURE HANDLING
    // =========================================================
    protected static void handleFailure(FailureHandling failureHandling, String message, Exception e) {
        String fullMessage = (e != null)
                ? message + "\n\n| Reason: " + e.getMessage() +"\n\n\n"
                : message;

        switch (failureHandling) {
            case STOP_ON_FAILURE -> markErrorAndStop(fullMessage);
            case CONTINUE_ON_FAILURE -> markError(fullMessage);
            case OPTIONAL -> markWarning(fullMessage);
        }
    }

    // =========================================================
    // 📸 SCREENSHOT
    // =========================================================
    public byte[] takeScreenshot() {
        log.info("Taking screenshot...");
        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
    }


    // =========================================================
    // 🌐 OS SPECIFIC LOCATOR
    // Mirrors: osSpecificTextLocator() from PageBase.groovy
    // =========================================================

    /**
     * Build OS specific XPath locator for a given text.
     *
     * @param text         The text to locate.
     * @param isExactMatch If true, the locator will match the text exactly; if false, it will match any element containing the text.
     * @return The XPath locator string.
     */
    private String osSpecificTextLocator(String text,
                                         boolean isExactMatch) {
        if (isExactMatch) {
            return "//*[@text='" + text + "' or "
                    + "(@name='" + text + "' or "
                    + "@label='" + text + "' or "
                    + "@value='" + text + "')]";
        } else {
            return "//*[contains(@text,'" + text + "') or "
                    + "(contains(@name,'" + text + "') or "
                    + "contains(@label,'" + text + "') or "
                    + "contains(@value,'" + text + "'))]";
        }
    }

    // =========================================================
    // 🔧 HELPER - TEXT NORMALIZATION
    // =========================================================

    /**
     * Normalize text — remove newlines, HTML tags, extra spaces, and trim leading/trailing spaces.
     *
     * @param text The text to normalize.
     * @return The normalized text.
     */
    private String normalizeText(String text) {
        if (text == null) return "";
        return text
                .replaceAll("[\\r\\n]", "")
                .replaceAll("<[^>]*>", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    // =========================================================
    // 🔍 ELEMENT FINDING METHODS
    // =========================================================

    /**
     * Find element by XPath
     */
    protected WebElement findElement(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    /**
     * Find all elements by XPath
     */
    protected List<WebElement> findElements(String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    /**
     * Find elements by text
     */
    protected List<WebElement> getElementsWithText(String text,
                                                   boolean isExactMatch, int timeoutInSeconds, FailureHandling failureHandling) {
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            String xpath = osSpecificTextLocator(text, isExactMatch);
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
        } catch (Exception e) {
            handleFailure(failureHandling,
                    "Text: " + text + " could NOT be located on the page",
                    null);
        }
        return List.of();
    }


    // =========================================================
    // 🖱️ CLICK METHODS
    // =========================================================

    protected void click(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        log.info("Click element: " + element);
        element.click();
    }

    /**
     * Click an element by its text.
     *
     * @param text            The text of the element to click.
     * @param isExactMatch    If true, the text must match exactly; if false, the text can be a substring of the element's text.
     * @param failureHandling Specifies how to handle failures (STOP_ON_FAILURE, CONTINUE_ON_FAILURE, OPTIONAL).
     */
    protected void clickByText(String text,
                               boolean isExactMatch,
                               int timeoutInSeconds,
                               FailureHandling failureHandling) {
        List<WebElement> result = getElementsWithText(text, isExactMatch, timeoutInSeconds, failureHandling);
        if (result.size() >= 1) {
            log.info("Clicking element with text: " + text);
            result.get(0).click();
        }
    }

    /**
     * Click element by XPath with FailureHandling
     */
    protected void click(String xpath, FailureHandling failureHandling) {
        try {
            WebElement element = findElement(xpath);
            log.info("Clicked element: " + xpath);
            element.click();
        } catch (Exception e) {
            handleFailure(failureHandling,
                    "Could not click element: " + xpath, e);
        }
    }

    // =========================================================
    // ⌨️ INPUT METHODS
    // =========================================================
    /**
     * Type text with FailureHandling
     */
    protected void setText(String xpath, String text,int timeoutInSeconds,
                           FailureHandling failureHandling) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            element.clear();
            element.sendKeys(text);
            log.info("Entered text: " + text + " in: " + xpath);
        } catch (Exception e) {
            handleFailure(failureHandling,
                    "Could not enter text in: " + xpath, e);
        }
    }

    // =========================================================
// 🔍 FIND TEST OBJECT — 4 versions
// =========================================================

    /**
     * Version 1 — Auto-detects object name AND page class from the
     * calling method. No ambiguity risk — resolved via the actual
     * page class object, not a name search.
     * Usage: return findTestObject();
     */
    protected By findTestObject() {
        ObjectScaffold.CallerInfo caller = ObjectScaffold.findCallerInfo();

        if (caller == null) {
            throw new RuntimeException(
                    "findTestObject() could not detect a calling page "
                            + "class method. If calling from outside a page "
                            + "class getObject...() method, use "
                            + "findTestObject(String fullPath) instead.");
        }

        String objectName = caller.methodName.startsWith("get")
                ? caller.methodName.substring(3)
                : caller.methodName;

        return resolveObjectWithMaintenanceCheck(this.getClass(), objectName, null);
    }

    /**
     * Version 2 — Explicit FULL PATH, not just an object name.
     * Lets you reference an object belonging to a DIFFERENT page class
     * than the one currently executing, with zero ambiguity.
     * Usage: return findTestObject("mobile/login/LoginPage/ObjectButtonLogin");
     */
    protected By findTestObject(String fullPath) {
        return resolveObjectByPathWithMaintenanceCheck(fullPath, null);
    }

    /**
     * Version 3 — Explicit full path + dynamic XPath variables.
     * Usage: return findTestObject("mobile/dashboard/AmountPage/ObjectTextAmount",
     * Map.of("amount", "500"));
     */
    protected By findTestObject(String fullPath,
                                Map<String, Object> variables) {
        return resolveObjectByPathWithMaintenanceCheck(fullPath, variables);
    }

    /**
     * Version 4 — Auto-detects current page class/object + dynamic variables.
     * Usage: return findTestObject(Map.of("username", "thomas"));
     */
    protected By findTestObject(Map<String, Object> variables) {
        ObjectScaffold.CallerInfo caller = ObjectScaffold.findCallerInfo();

        if (caller == null) {
            throw new RuntimeException(
                    "findTestObject(Map) could not detect a calling page "
                            + "class method.");
        }

        String objectName = caller.methodName.startsWith("get")
                ? caller.methodName.substring(3)
                : caller.methodName;
        return resolveObjectWithMaintenanceCheck(this.getClass(), objectName, variables);
    }

    // =========================================================
    // 🔧 CORE RESOLVERS
    // =========================================================
    private By resolveObjectWithMaintenanceCheck(Class<?> pageClass,
                                                 String objectName,
                                                 Map<String, Object> variables) {
        By locator = resolveObject(pageClass, objectName, variables);
        return applyMaintenanceCheck(pageClass, objectName, locator);
    }

    private By resolveObjectByPathWithMaintenanceCheck(String fullPath,
                                                       Map<String, Object> variables) {
        By locator = resolveObjectByPath(fullPath, variables);
        String objectName = fullPath.contains("/")
                ? fullPath.substring(fullPath.lastIndexOf('/') + 1) : fullPath;
        return applyMaintenanceCheck(null, objectName, locator);
    }

    private By applyMaintenanceCheck(Class<?> pageClass, String objectName, By locator) {
        if (!GlobalVariable.MAINTAINENCE_MODE)
            return locator;
        int timeout = Integer.parseInt(ConfigReader.get(ConfigKeys.MAINTENANCE_TIMEOUT));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return locator;
        } catch (org.openqa.selenium.TimeoutException e) {
            return MaintenanceModeHandler.handle(pageClass, objectName, locator, driver, wait, e);
        }
    }

    /**
     * Used by Versions 1 and 4 — page class is known directly (this.getClass()),
     * so lookup is inherently unambiguous.
     */
    private By resolveObject(Class<?> pageClass,
                             String objectName,
                             Map<String, Object> variables) {
        String platform = MobileDriverManager.getExecutionOS();

        log.info("Finding object: " + objectName
                + " [" + pageClass.getName() + " / " + platform + "]");

        String xpath;
        try {
            xpath = ObjectRepository.getXPath(pageClass, objectName, platform);
        } catch (RuntimeException e) {
            xpath = ObjectResolutionHandler.recover(objectName, platform);
        }
        xpath = applyVariablesIfPresent(xpath, variables);
        return By.xpath(xpath);
    }

    /**
     * Used by Versions 2 and 3 — full path given explicitly, resolves
     * to any page class's JSON, not just the current one.
     */
    private By resolveObjectByPath(String fullPath,
                                   Map<String, Object> variables) {
        String platform = MobileDriverManager.getExecutionOS();

        log.info("Finding object by path: " + fullPath + " [" + platform + "]");

        String xpath;
        try {
            xpath = ObjectRepository.getXPathByPath(fullPath, platform);
        } catch (RuntimeException e) {
            xpath = ObjectResolutionHandler.recoverFromPath(fullPath, platform);
        }
        xpath = applyVariablesIfPresent(xpath, variables);
        return By.xpath(xpath);
    }

    private String applyVariablesIfPresent(String xpath, Map<String, Object> variables) {
        if (variables == null || variables.isEmpty()) return xpath;

        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            String value = String.valueOf(entry.getValue());
            xpath = xpath.replace(placeholder, value);
            log.info("Replaced: " + placeholder + " → " + value);
        }
        return xpath;
    }
}
