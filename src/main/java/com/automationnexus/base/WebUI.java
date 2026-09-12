package com.automationnexus.base;

import com.automationnexus.enums.FailureHandling;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebUI {
    private static final Logger log = LogManager.getLogger(WebUI.class);
    private WebDriver driver;

    public WebUI(WebDriver driver) {
        this.driver = driver;
    }

    public boolean waitForElementToBeDisplayed(By object, int timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(object));
        return element.isDisplayed();
    }

    public boolean waitForElementToBeDisplayed(By object, int timeoutInSeconds, FailureHandling failureHandling){
        try {
            return waitForElementToBeDisplayed(object, timeoutInSeconds);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could not find element: " + object.toString(), e);
            return false;
        }
    }

    public boolean waitForElementToBeDisplayed(WebElement object, int timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.visibilityOf(object));
        return element.isDisplayed();
    }

    public boolean waitForElementToBeDisplayed(WebElement object, int timeoutInSeconds, FailureHandling failureHandling){
        try {
            return waitForElementToBeDisplayed(object, timeoutInSeconds);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could not find element: " + object.toString(), e);
            return false;
        }
    }

    public boolean waitForElementNotToBeDisplayed(By object, int timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(object));
    }

    public boolean waitForElementNotToBeDisplayed(By object, int timeoutInSeconds, FailureHandling failureHandling){
        try {
            return waitForElementNotToBeDisplayed(object, timeoutInSeconds);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could find element: " + object.toString(), e);
            return false;
        }
    }

    public boolean waitForElementNotToBeDisplayed(WebElement object, int timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.invisibilityOf(object));
    }

    public boolean waitForElementNotToBeDisplayed(WebElement object, int timeoutInSeconds, FailureHandling failureHandling){
        try {
            return waitForElementNotToBeDisplayed(object, timeoutInSeconds);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could find element: " + object.toString(), e);
            return false;
        }
    }

    public void click(By object, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(object));
        element.click();
    }

    public void click(By object, int timeoutInSeconds, FailureHandling failureHandling) {
        try {
            click(object, timeoutInSeconds);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could not click element: " + object.toString(), e);
        }
    }

    public void setText(By object, String text, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(object));
        element.clear();
        element.sendKeys(text);
    }

    public void setText(By object, String text, int timeoutInSeconds, FailureHandling failureHandling) {
        try {
            setText(object, text, timeoutInSeconds);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could not set text for element: " + object.toString(), e);
        }
    }

    public String getTextByAttribute(By object, String attribute, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(object));
        return element.getAttribute(attribute);
    }

    public String getTextByAttribute(By object, String attribute, int timeoutInSeconds, FailureHandling failureHandling) {
        try {
            return getTextByAttribute(object, attribute, timeoutInSeconds);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could not get attribute : " + attribute + " for element: " + object.toString(), e);
            return null;
        }
    }

    public String getText(By object, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(object));
        return element.getText();
    }

    public void sendKeys(By object, int timeoutInSeconds, CharSequence... keysToSend) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(object));
        element.sendKeys(keysToSend);
    }

    public void sendKeys(By object, int timeoutInSeconds, FailureHandling failureHandling, CharSequence... keysToSend) {
        try {
            sendKeys(object, timeoutInSeconds, keysToSend);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could not set keys for element: " + object.toString(), e);
        }
    }

    public void delay(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds).toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Delay interrupted.", e);
        }
    }

}
