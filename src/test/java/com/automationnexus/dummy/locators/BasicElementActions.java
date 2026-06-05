package com.automationnexus.dummy;

import com.automationnexus.config.ConfigReader;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;

public class BasicElementActions {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                ConfigReader.get("appium.server.url"), 120);

        System.out.println(driver.getSessionId());
        By buttonViews = AppiumBy.accessibilityId("Views");
        By buttonTextFields = AppiumBy.accessibilityId("TextFields");
        By textFieldHintText = AppiumBy.id("io.appium.android.apis:id/edit");

        driver.findElement(buttonViews).click();
        Thread.sleep(5000);

        WebElement element = driver.findElement(AppiumBy.id("android:id/list"));
        driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", "up",
                "percent", 0.75
        ));

        driver.findElement(buttonTextFields).click();
        driver.findElement(textFieldHintText).sendKeys("Hello Appium");
        Thread.sleep(3000);
        driver.findElement(textFieldHintText).clear();

    }
}
