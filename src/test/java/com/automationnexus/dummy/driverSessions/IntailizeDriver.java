package com.automationnexus.dummy.driverSessions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class IntailizeDriver {

    /**
     * This method initializes the Appium driver based on the provided parameters.
     * @param platformName String
     * @param deviceName String
     * @param automationName String
     * @param udid String
     * @param avdName String
     * @param appPath String
     * @param appiumServerUrl String
     * @param appiumServerUrl String
     * @return commandTimeout int
     * @throws MalformedURLException
     */
    public static AppiumDriver getAppiumDriver(String platformName, String deviceName, String automationName, String udid,
            String avdName, String appPath, String appiumServerUrl, long commandTimeout) throws MalformedURLException {

        AppiumDriver driver= null;
        switch(platformName) {
            case "Android":
                System.out.println("Initializing Android driver with device name: " + deviceName +
                        ", automation name: " + automationName + ", udid: " + udid);

                UiAutomator2Options options = new UiAutomator2Options();
                options.setPlatformName(platformName);
                options.setDeviceName(deviceName);
                options.setAutomationName(automationName);
                options.setUdid(udid);

                options.setAvd(avdName);
                options.setAvdLaunchTimeout(Duration.ofSeconds(280));
                options.setAvdReadyTimeout(Duration.ofSeconds(220));
                options.setAvdArgs("-gpu swiftshader_indirect");

//                options.setAppPackage("com.google.android.apps.maps");
//                options.setAppActivity("com.google.android.maps.MapsActivity");
                options.setApp(appPath);
                options.setNewCommandTimeout(Duration.ofSeconds(commandTimeout));
                URL url = new URL(appiumServerUrl);
                driver = new AppiumDriver(url, options);
                break;
            case "iOS":
                System.out.println("Initializing iOS driver with device name: " + deviceName +
                        ", automation name: " + automationName + ", udid: " + udid);
                break;
            default:
                System.out.println("Unsupported platform: " + platformName);
        }
        return driver;
    }
}
