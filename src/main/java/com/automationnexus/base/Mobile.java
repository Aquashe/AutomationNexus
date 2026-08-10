package com.automationnexus.base;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.constants.ConfigKeys;
import com.automationnexus.enums.FailureHandling;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Mobile {
    private static final Logger log = LogManager.getLogger(Mobile.class);
    private AppiumDriver driver;


    public Mobile(AppiumDriver driver) {
        this.driver = driver;
    }

    public void tap(By object, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(object));
        element.click();
    }

    public void tap(By object, int timeoutInSeconds, FailureHandling failureHandling) {
        try {
            tap(object, timeoutInSeconds);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could not click element: " + object.toString(), e);
        }
    }

    public void doubleTap(By object, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(object));
        ((JavascriptExecutor) driver).executeScript("mobile: doubleClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId()
        ));
    }

    public void doubleTap(By object, int timeoutInSeconds, FailureHandling failureHandling) {
        try {
            doubleTap(object, timeoutInSeconds);
        } catch (Exception e) {
            BasePage.handleFailure(failureHandling,
                    "Could not double click element: " + object.toString(), e);
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
}
