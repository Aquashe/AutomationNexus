package com.automationnexus.dummy.gestures.ios;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class MobileTapGesture {
    public static void main(String[] args) {
        System.out.println("This is a dummy class for tap gesture on iOS.");
    }


    public static void tapGesture(AppiumDriver driver, float x, float y) {
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        driver.executeScript("mobile: tap", params);
    }

    /**
     * x and y tap coordinates will be calulated relatively to the current element position on the screen
     * @param driver
     * @param x
     * @param y
     * @param element
     */
    public static void tapGesture(AppiumDriver driver, float x, float y, WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        params.put("elementId", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: tap", params);
    }
}
