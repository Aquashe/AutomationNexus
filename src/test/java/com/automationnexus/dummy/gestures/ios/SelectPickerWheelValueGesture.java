package com.automationnexus.dummy.gestures.ios;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class SelectPickerWheelValueGesture {
    public static void main(String[] args) {
        System.out.println("This is a dummy class for select picker wheel value gesture on iOS.");
    }

    /**
     * the element must be of type XCUIElementTypePickerWheel
     * order can be "next" or "previous"
     * offset is a value between 0 and 1 that represents the percentage of the picker wheel to scroll
     *
     * @param driver
     * @param element
     * @param order
     */
    public static void selectPickerWheelValueGesture(AppiumDriver driver, WebElement element, String order) {
        Map<String, Object> params = new HashMap<>();
        params.put("order", order);
        params.put("element", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: selectPickerWheelValue", params);
    }

    public static void selectPickerWheelValueGesture(AppiumDriver driver, WebElement element, String order, float offset) {
        Map<String, Object> params = new HashMap<>();
        params.put("order", order);
        params.put("offset", offset);
        params.put("element", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: selectPickerWheelValue", params);
    }
}
