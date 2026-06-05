package com.automationnexus.dummy;

import com.automationnexus.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class CreateDriverSessionUsingOptions {
    public static void main(String[] args) {
        System.out.println("This is a dummy class to create a driver session using options.");

        // For Android, we use UiAutomator2Options to set the desired capabilities in a more structured way.
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.get("platform.name"));
        options.setDeviceName(ConfigReader.get("device.name"));
        options.setAutomationName(ConfigReader.get("automation.name"));
        options.setUdid(ConfigReader.get("device.udid"));
        options.setApp(ConfigReader.getAppPath());

        try {
            URL url = new URL(ConfigReader.get("appium.server.url"));
            AppiumDriver driver = new AppiumDriver(url, options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        //For iOS, you would use XCUITestOptions instead of UiAutomator2Options and set the relevant capabilities for iOS.
        XCUITestOptions iosOptions = new XCUITestOptions();
        iosOptions.setPlatformName(ConfigReader.get("platform.name"));
        iosOptions.setDeviceName(ConfigReader.get("device.name"));
        iosOptions.setAutomationName(ConfigReader.get("automation.name"));
        iosOptions.setUdid(ConfigReader.get("device.udid"));
        iosOptions.setApp(ConfigReader.getAppPath());

    }
}
