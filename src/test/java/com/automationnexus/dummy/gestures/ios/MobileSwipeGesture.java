package com.automationnexus.dummy.gestures.ios;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class MobileSwipeGesture {

    public static void main(String[] args) {
        System.out.println("This is a dummy class for swipe gesture on iOS.");
    }

    public static void swipeGesture(AppiumDriver driver, WebElement element, String direction) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        params.put("elementId", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: swipe", params);
    }
}
