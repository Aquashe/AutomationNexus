package com.automationnexus.dummy;

import com.automationnexus.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class CreateDriverSessionUsingOptionsRealDevice {
    public static void main(String[] args) {
        System.out.println("This is a dummy class to create a Real Device driver session using options.");

        // For Android, we use UiAutomator2Options to set the desired capabilities in a more structured way.
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.get("platform.name"));
        options.setDeviceName(ConfigReader.get("real.device.name"));
        options.setAutomationName(ConfigReader.get("automation.name"));
        options.setUdid(ConfigReader.get("real.device.udid"));
        options.ignoreHiddenApiPolicyError();
        options.skipServerInstallation();
        options.noReset();
        options.setCapability("forceAppLaunch", true);
        options.setNewCommandTimeout(Duration.ofSeconds(180));
//        options.setAppPackage("io.appium.android.apis");
//        options.setAppActivity(".accessibility.CustomViewAccessibilityActivity");
        options.setApp(ConfigReader.getAppPath());

        try {
            URL url = new URL(ConfigReader.get("appium.server.url"));
            AppiumDriver driver = new AppiumDriver(url, options);
            System.out.println(driver.getSessionId());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
