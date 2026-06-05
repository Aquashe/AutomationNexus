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

public class ClickGesture {
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

        Point viewsCenter = getElementCenter(driver.findElement(buttonViews));
        System.out.println("Center X: "+viewsCenter.getX());
        System.out.println("Center Y: "+viewsCenter.getY());
        clickGesture(driver, viewsCenter.x, viewsCenter.y, 2000);

        Point dragCenter = getElementCenter(driver.findElement(buttonDragAndDrop));
        clickGesture(driver, dragCenter.x, dragCenter.y, 2000);

    }

    public static void clickGesture(AppiumDriver driver, By elementLocator) {
        driver.executeScript("mobile: clickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) driver.findElement(elementLocator)).getId()
        ));
    }

    public static void clickGesture(AppiumDriver driver, int xCoordinate, int yCoordinate, int duration) {
        driver.executeScript("mobile: clickGesture", ImmutableMap.of(
                "x", xCoordinate,
                "y", yCoordinate,
                "duration", duration
        ));
    }

    public static void clickGesture(AppiumDriver driver, By elementLocator, int xCoordinate, int yCoordinate, int duration) {
        driver.executeScript("mobile: clickGesture", ImmutableMap.of(
                "x", xCoordinate,
                "y", yCoordinate,
                "duration", duration,
                "elementId", ((RemoteWebElement) driver.findElement(elementLocator)).getId()
        ));
    }

    /**
     * Calculates the center position of an element
     * @param element The WebElement to get the center coordinates from
     * @return Point representing the center coordinates (x, y)
     */
    public static Point getElementCenter(WebElement element) {
        Point location = element.getLocation();
        Dimension size = element.getSize();
        
        int centerX = location.x + (size.width / 2);
        int centerY = location.y + (size.height / 2);
        
        return new Point(centerX, centerY);
    }
}
