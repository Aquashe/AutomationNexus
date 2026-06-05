package com.automationnexus.dummy.gestures.android;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;

public class DragAndDropGesture {
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

        WebElement element = driver.findElement(buttonDragOne);
        Point center = getElementCenter(element);
        dragGesture(driver, center.x, center.y, center.x + 200, center.y, 1000);
//        dragGesture(driver, element, center.x + 200, center.y, 1000);

    }

    public static void dragGesture(AppiumDriver driver, int startX, int startY, int endX, int endY, int duration) {
        driver.executeScript("mobile: dragGesture", ImmutableMap.of(
                "startX", startX,
                "startY", startY,
                "endX", endX,
                "endY", endY,
                "duration", duration
        ));
    }

    public static void dragGesture(AppiumDriver driver, WebElement element, int endX, int endY, int duration) {
        driver.executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "endX", endX,
                "endY", endY,
                "duration", duration
        ));
    }

    public static Point getElementCenter(WebElement element) {
        Point location = element.getLocation();
        Dimension size = element.getSize();
        
        int centerX = location.x + (size.width / 2);
        int centerY = location.y + (size.height / 2);
        return new Point(centerX, centerY);
    }
}
