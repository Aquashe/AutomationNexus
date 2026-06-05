package com.automationnexus.dummy.gestures.ios;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class MobileScrollGesture {
    public static void main(String[] args) {
        System.out.println("This is a dummy class for scroll gesture on iOS.");
    }

    public static void scrollGesture(AppiumDriver driver, String direction) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        driver.executeScript("mobile: scroll", params);
    }

    /**
     * If the WebElement is a parent , then need to provide the direction
     * @param driver
     * @param element
     * @param direction
     */
    public static void scrollGesture(AppiumDriver driver, WebElement element, String direction) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        params.put("elementId", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: scroll", params);
    }

    /**
     * the WebElement should be a child element, then no need to provide the direction
     * It should be either name or predicateString
     * Can't use direction as an argument here
     * @param driver
     * @param parentElement
     * @param childElement
     */
    public static void scrollGestureByName(AppiumDriver driver, WebElement parentElement, WebElement childElement) {
        String name = ((RemoteWebElement) childElement).getAttribute("name");

        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) parentElement).getId());
        params.put("name", name);
        driver.executeScript("mobile: scroll", params);
    }

    public static void scrollGestureByPredicateString(AppiumDriver driver, WebElement parentElement, WebElement childElement) {
        String predicateString = ((RemoteWebElement) childElement).getAttribute("predicateString");

        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) parentElement).getId());
        params.put("predicateString", predicateString);
        driver.executeScript("mobile: scroll", params);
    }

    /**
     * the element should have an accessibility id
     * @param driver
     * @param element
     */
    public static void scrollGestureByAccesibiilityId(AppiumDriver driver, WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) element).getId());
        params.put("toVisible", true);
        driver.executeScript("mobile: scroll", params);
    }
}
