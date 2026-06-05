package com.automationnexus.dummy.gestures.android;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;

public class PinchOpenAndPinchCloseGesture {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        System.out.println("This is a dummy class for long click action.");
        AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                ConfigReader.get("appium.server.url"), 120);
        System.out.println(driver.getSessionId());

        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(20));

        By buttonSkip = AppiumBy.xpath("//android.widget.Button[@text=\"Skip\"]");
        driver.findElement(buttonSkip).click();

        Thread.sleep(10000);
        pinchOpen(driver, 200, 470, 600, 600, 0.75);

        Thread.sleep(5000);
        pinchClose(driver, 200, 470, 600, 600, 0.75);
    }


    public static void pinchOpen(AppiumDriver driver, WebElement element, double percent) {
        driver.executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "percent", percent
        ));
    }

    public static void pinchOpen(AppiumDriver driver, int left, int top, int width, int height,  double percent) {
        driver.executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
                        "left", left,
                        "top", top,
                        "width", width,
                        "height", height,
                        "percent", percent
        ));
    }

    public static void pinchClose(AppiumDriver driver, int left, int top, int width, int height,  double percent) {
        driver.executeScript("mobile: pinchCloseGesture", ImmutableMap.of(
                "left", left,
                "top", top,
                "width", width,
                "height", height,
                "percent", percent
        ));
    }
}
