package com.automationnexus.dummy.hybrid;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class HybridAutomation {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        System.out.println("This is a dummy class for hybrid automation.");

        AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                ConfigReader.get("appium.server.url"), 320);
        System.out.println(driver.getSessionId());

        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        By buttonViews = AppiumBy.accessibilityId("Views");
        By containerViewsPage = AppiumBy.id("android:id/list");
        By buttonWebView = AppiumBy.accessibilityId("WebView");
        By TextThisPageIsSeleniumSandbox = AppiumBy.xpath("//android.widget.TextView[@text='This page is a Selenium sandbox']");

        driver.findElement(buttonViews).click();
        Thread.sleep(5000);
        boolean canScrollMore = scrollGesture(driver, containerViewsPage, "down", 0.75);
        while(canScrollMore){
            canScrollMore = scrollGesture(driver, containerViewsPage, "down", 0.75);
        }

        driver.findElement(buttonWebView).click();
        Thread.sleep(5000);

        ((AndroidDriver)driver).getContextHandles().forEach(context -> {
            System.out.println("Available context: " + context);
        });

        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(TextThisPageIsSeleniumSandbox));
        String text = driver.findElement(TextThisPageIsSeleniumSandbox).getText();
        System.out.println("Text found: " + text);

        ((AndroidDriver)driver).getContextHandles().forEach(context -> {
            System.out.println("Available context: " + context);
        });

        ((AndroidDriver) driver).context("WEBVIEW");
        System.out.println(driver.findElement(By.cssSelector("body > h1")).getText());

    }

    public static boolean scrollGesture(AppiumDriver driver, By locator, String direction, double percent) {
        WebElement element = driver.findElement(locator);  // Refind right before use
        boolean canScrollMore = (Boolean)  driver.executeScript("mobile: scrollGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction.toLowerCase(),
                "percent", percent
        ));
        return canScrollMore;
    }
}
