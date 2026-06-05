package com.automationnexus.dummy;

import com.automationnexus.config.ConfigReader;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;

public class LongClickGesture {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        System.out.println("This is a dummy class for long click action.");
        AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                ConfigReader.get("appium.server.url"), 120);
        System.out.println(driver.getSessionId());

        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        By buttonViews = AppiumBy.accessibilityId("Views");
        By buttonDragAndDrop = AppiumBy.accessibilityId("Drag and Drop");
        By buttonDragOne = AppiumBy.id("io.appium.android.apis:id/drag_dot_1");


        driver.findElement(buttonViews).click();
        driver.findElement(buttonDragAndDrop).click();


        longClickGesture(driver, buttonDragOne);
        Thread.sleep(3000);

    }

    public static void longClickGesture(AppiumDriver driver, By elementLocator) {
        driver.executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) driver.findElement(elementLocator)).getId(),
                "duration", 2000
        ));
    }
}
