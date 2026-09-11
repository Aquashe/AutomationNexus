package com.automationnexus.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {
    private WebDriver waitHelperdriver;
    private WebDriverWait wait;

    public WaitHelper(WebDriver driver, int timeOutSeconds){
        this.waitHelperdriver = driver;
        this.wait = new WebDriverWait(waitHelperdriver, Duration.ofSeconds(timeOutSeconds));
    }

    public WebElement waitForElementTobeClickable(By locator){
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementTobeClickable(WebElement webElement){
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public WebElement waitForElementTobeVisible(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementTobeVisible(WebElement webElement){
        return wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public boolean waitForElementTobeInvisible(WebElement webElement){
        return wait.until(ExpectedConditions.invisibilityOf(webElement));
    }
}
