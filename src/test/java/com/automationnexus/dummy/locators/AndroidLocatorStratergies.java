package com.automationnexus.dummy.locators;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;

public class AndroidLocatorStratergies {
    public static void main(String[] args) throws MalformedURLException {

        AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                ConfigReader.get("platform.name"), ConfigReader.get("device.name"), ConfigReader.get("automation.name"),
                ConfigReader.get("device.udid"), ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                ConfigReader.get("appium.server.url"), 300);

        System.out.println(driver.getSessionId());
        WebElement accessibilityElement = driver.findElement(AppiumBy.accessibilityId("Accessibility"));
        System.out.println("Text : "+accessibilityElement.getText());

        WebElement animationElement = driver.findElement(AppiumBy.accessibilityId("Animation"));
        System.out.println("Text : "+animationElement.getText());



    }
}
