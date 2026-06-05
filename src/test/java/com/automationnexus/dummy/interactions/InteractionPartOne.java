package com.automationnexus.dummy.interactions;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.net.MalformedURLException;

public class InteractionPartOne {
        public static void main(String[] args) throws MalformedURLException, InterruptedException {
            System.out.println("This is a dummy class to perform interactions on mobile elements.");

            AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                    ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                    ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                    ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                    ConfigReader.get("appium.server.url"), 120);
            System.out.println(driver.getSessionId());

            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

            By buttonViews = AppiumBy.accessibilityId("Views");
            driver.findElement(buttonViews).click();

            Thread.sleep(5000);
            ((AndroidDriver) driver).terminateApp(ConfigReader.get("app.package.name"));

            Thread.sleep(5000);
            ((AndroidDriver) driver).installApp(ConfigReader.getAppPath());
            System.out.println(((AndroidDriver) driver).isAppInstalled(ConfigReader.get("app.package.name")));
        }
}
