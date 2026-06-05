package com.automationnexus.dummy;

import com.automationnexus.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class CreateDriverSessionWithoutLaunchingAndroidStudio {
    public static void main(String[] args) {
        System.out.println("This is a dummy class to create a driver session " +
                "without launching Android Studio.");
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.get("platform.name"));
        options.setDeviceName(ConfigReader.get("device.name"));
        options.setAutomationName(ConfigReader.get("automation.name"));
        options.setUdid(ConfigReader.get("device.udid"));

        options.setAvd(ConfigReader.get("avd.name"));
        options.setAvdLaunchTimeout(Duration.ofSeconds(180));
        options.setAvdReadyTimeout(Duration.ofSeconds(120));
        options.setAvdArgs("-gpu swiftshader_indirect");

        options.setApp(ConfigReader.getAppPath());
        options.setNewCommandTimeout(Duration.ofSeconds(300));
        try {
            URL url = new URL(ConfigReader.get("appium.server.url"));
            AppiumDriver driver = new AppiumDriver(url, options);
            System.out.println(driver.getSessionId());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
