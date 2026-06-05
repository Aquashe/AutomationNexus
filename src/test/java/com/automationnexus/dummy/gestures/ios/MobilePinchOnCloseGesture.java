package com.automationnexus.dummy.gestures.ios;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class MobilePinchOnCloseGesture {
    public static void main(String[] args) {
        System.out.println("This is a dummy class for pinch on close gesture on iOS.");
    }

    /**
     * Use a scale between 0 and 1 to "pinch close" or zoom out
     * scale greater than 1 to "pinch open" or zoom in
     * @param driver
     * @param scale
     * @param velocity
     */
    public static void pinchOnCloseGesture(AppiumDriver driver, float scale, float velocity) {
        Map<String, Object> params = new HashMap<>();
        params.put("scale", scale);
        params.put("velocity", velocity);
        driver.executeScript("mobile: pinch", params);
    }

    public static void pinchOnCloseGesture(AppiumDriver driver, WebElement element, float scale, float velocity) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) element).getId());
        params.put("scale", scale);
        params.put("velocity", velocity);
        driver.executeScript("mobile: pinch", params);
    }
}
