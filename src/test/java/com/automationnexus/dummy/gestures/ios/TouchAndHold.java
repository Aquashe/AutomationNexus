package com.automationnexus.dummy.gestures.ios;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class TouchAndHold {
    public static void main(String[] args) {
        System.out.println("This is a dummy class for touch and hold gesture on iOS.");
    }

    public static void touchAndHold(AppiumDriver driver, WebElement element, int duration) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) element).getId());
        params.put("duration", duration);
        driver.executeScript("mobile: touchAndHold", params);
    }

    public static void touchAndHold(AppiumDriver driver, int duration, float x, float y) {
        Map<String, Object> params = new HashMap<>();
        params.put("duration", duration);
        params.put("x", x);
        params.put("y", y);
        driver.executeScript("mobile: touchAndHold", params);
    }
}
