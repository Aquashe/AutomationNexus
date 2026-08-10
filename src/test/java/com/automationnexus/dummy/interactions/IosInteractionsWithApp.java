package com.automationnexus.dummy.interactions;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.ios.IOSDriver;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class IosInteractionsWithApp {
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Performing interactions with the iOS app...");

        AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                ConfigReader.get("appium.server.url"), 120);
        System.out.println(driver.getSessionId());

        System.out.println("1. Terminating the app...");
        ((IOSDriver)driver).terminateApp(ConfigReader.get("app.package"));

        System.out.println("2. Installing the app...");
        ((IOSDriver)driver).installApp(ConfigReader.getAppPath());

        System.out.println("3. Activating the app...");
        ((IOSDriver)driver).activateApp(ConfigReader.get("app.package"));

        System.out.println("4. Running the app in the background for 5 seconds...");
        ((IOSDriver)driver).runAppInBackground(java.time.Duration.ofSeconds(5));

        System.out.println("5. Activating the settings app ...");
        ((IOSDriver)driver).activateApp("com.apple.Preferences");

        System.out.println("6. Querying the app state...");
        ApplicationState appState = ((IOSDriver)driver).queryAppState(ConfigReader.get("app.package"));
        System.out.println("App state: " + appState);

        ((IOSDriver)driver).activateApp(ConfigReader.get("app.package"));
        appState = ((IOSDriver)driver).queryAppState(ConfigReader.get("app.package"));
        System.out.println("App state: " + appState);

        //OR
        Map<String, Object> params = new HashMap<>();
        params.put("appId", ConfigReader.get("app.package"));
        final boolean isAppInstalled = (Boolean) driver.executeScript("mobile: isAppInstalled", params);
        System.out.println("App state: " + isAppInstalled);


    }
}
