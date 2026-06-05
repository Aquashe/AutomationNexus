package com.automationnexus.dummy;

import com.automationnexus.config.ConfigReader;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;

public class SwipeGestureAndroid {
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
        By containerViewsPage = AppiumBy.id("android:id/list");
        By buttonGallery = AppiumBy.accessibilityId("Gallery");
        By buttonPhotos = AppiumBy.accessibilityId("1. Photos");
        By containerPhotosPage = AppiumBy.id("io.appium.android.apis:id/gallery");

        driver.findElement(buttonViews).click();
        swipeGesture(driver, containerViewsPage, "up", 0.5);
        swipeGesture(driver, containerViewsPage, "down", 0.5);

        driver.findElement(buttonGallery).click();
        driver.findElement(buttonPhotos).click();

        swipeGesture(driver, containerPhotosPage, "left", 0.5);
        swipeGesture(driver, containerPhotosPage, "right", 0.5);
        swipeGesture(driver, containerPhotosPage, "up", 0.5);



    }

    public static void swipeGesture(AppiumDriver driver, By locator, String direction, double percent) {
        WebElement element = driver.findElement(locator);  // Refind right before use
        driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction.toLowerCase(),
                "percent", percent
        ));
    }

    public static void swipeGesture(AppiumDriver driver, int left, int top, int width, int height,  String direction, double percent) {
        driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "left", left,
                "top", top,
                "width", width,
                "height", height,
                "direction", direction.toLowerCase(),
                "percent", percent
        ));
    }

}
