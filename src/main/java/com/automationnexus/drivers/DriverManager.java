package com.automationnexus.drivers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static WebDriver driver;

    public static void setDriver(WebDriver driver) {

        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null.");
        }
        DriverManager.driver = driver;
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException(
                    "Driver has not been initialized. "
                            + "Initialize the driver from BaseWebTest or BaseMobileTest first."
            );
        }
        return driver;
    }

    public static String getExecutionOS() {
        WebDriver currentDriver = getDriver();
        if (currentDriver instanceof AndroidDriver) {
            return "android";
        }
        if (currentDriver instanceof IOSDriver) {
            return "ios";
        }
        if (currentDriver instanceof AppiumDriver) {
            throw new IllegalStateException(
                    "Unknown Appium driver type: "
                            + currentDriver.getClass().getName()
            );
        }
        return "web";
    }

    public static void clearDriver() {
        driver = null;
    }
}
