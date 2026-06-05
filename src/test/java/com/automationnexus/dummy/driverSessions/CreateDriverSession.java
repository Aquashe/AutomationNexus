package com.automationnexus.dummy.driverSessions;

import com.automationnexus.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class CreateDriverSession {
    public static void main(String[] args) {
        System.out.println("This is a dummy class to create a driver session.");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", ConfigReader.get("platform.name"));
        capabilities.setCapability("appium:deviceName", ConfigReader.get("device.name"));
        capabilities.setCapability("appium:automationName", ConfigReader.get("automation.name"));
        capabilities.setCapability("appium:udid", ConfigReader.get("device.udid"));

        String appPath = ConfigReader.getAppPath();
        capabilities.setCapability("appium:app", appPath);

        try {
            URL url = new URL(ConfigReader.get("appium.server.url"));
            AppiumDriver driver = new AppiumDriver(url, capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
