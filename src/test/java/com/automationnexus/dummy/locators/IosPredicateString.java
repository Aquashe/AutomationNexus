package com.automationnexus.dummy.locators;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;

public class IosPredicateString {
    public static void main(String[] args) throws MalformedURLException {

        AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                ConfigReader.get("appium.server.url"), 300);

        String predicateString = "type == 'XCUIElementTypeStaticText' AND name == 'Alert Views'";
        WebElement element = driver.findElement(AppiumBy.iOSNsPredicateString(predicateString));
        System.out.println("Text : "+element.getText());
    }
}
