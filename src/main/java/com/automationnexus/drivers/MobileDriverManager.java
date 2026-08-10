package com.automationnexus.drivers;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.constants.ConfigKeys;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class MobileDriverManager {

    private static AppiumDriver driver;

    public static AppiumDriver getDriver() {
        if (driver == null)
            initializeDriver();
        return driver;
    }

    private static void initializeDriver() {
        String platform = ConfigReader.get(ConfigKeys.PLATFORM_NAME);

        try {
            URL serverUrl = new URL(ConfigReader.get(ConfigKeys.APPIUM_SERVER_URL));

            if (platform.equalsIgnoreCase("android"))
                driver = createAndroidDriver(serverUrl);
            else if (platform.equalsIgnoreCase("ios"))
                driver = createIOSDriver(serverUrl);
            else
                throw new RuntimeException("Unsupported platform: " + platform);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL", e);
        }
    }

    private static AppiumDriver createAndroidDriver(URL serverUrl) {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName(ConfigReader.get(ConfigKeys.PLATFORM_NAME));
        options.setDeviceName(ConfigReader.get(ConfigKeys.ANDROID_DEVICE_NAME));
        options.setAutomationName(ConfigReader.get(ConfigKeys.ANDROID_AUTOMATION_NAME));

        String udid = ConfigReader.get(ConfigKeys.ANDROID_DEVICE_UDID);
        if (udid != null && !udid.isEmpty()) {
            options.setUdid(udid);
        }

        boolean useEmulator = Boolean.parseBoolean(ConfigReader.get(ConfigKeys.APP_USE_EMULATOR));
        if (useEmulator) {
            options.setAvd(ConfigReader.get(ConfigKeys.ANDROID_AVD_NAME));
            options.setAvdLaunchTimeout(Duration.ofSeconds(
                    Long.parseLong(ConfigReader.get(ConfigKeys.ANDROID_AVD_LAUNCH_TIMEOUT))));
            options.setAvdReadyTimeout(Duration.ofSeconds(
                    Long.parseLong(ConfigReader.get(ConfigKeys.ANDROID_AVD_READY_TIMEOUT))));
            options.setAvdArgs("-gpu swiftshader_indirect");
        }

        options.setAutoGrantPermissions(true);
        options.setFullReset(false);
        options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(
                Long.parseLong(ConfigReader.get(ConfigKeys.ANDROID_UIA2_LAUNCH_TIMEOUT))));
        options.setUiautomator2ServerInstallTimeout(Duration.ofSeconds(
                Long.parseLong(ConfigReader.get(ConfigKeys.ANDROID_UIA2_INSTALL_TIMEOUT))));
        options.setCapability("androidDeviceReadyTimeout",
                Integer.parseInt(ConfigReader.get(ConfigKeys.ANDROID_DEVICE_READY_TIMEOUT)));

        String chromedriverPath = getChromePath();
        if (!chromedriverPath.isEmpty())
            options.setChromedriverExecutable(chromedriverPath);

        boolean isAppInstalled = isAppInstalled(ConfigReader.get(ConfigKeys.ANDROID_APP_PACKAGE_NAME));
        if (!isAppInstalled)
            options.setApp(getAppPath());
        else {
            options.setAppPackage(ConfigReader.get(ConfigKeys.ANDROID_APP_PACKAGE_NAME));
            options.setAppActivity(ConfigReader.get(ConfigKeys.ANDROID_APP_ACTIVITY_NAME));
        }
        // Always set this regardless of installed or fresh install
        options.setAppWaitActivity(ConfigReader.get(ConfigKeys.ANDROID_APP_ACTIVITY_WAIT));

        options.setNewCommandTimeout(Duration.ofSeconds(
                Long.parseLong(ConfigReader.get(ConfigKeys.APP_COMMAND_TIMEOUT))));

        return new AndroidDriver(serverUrl, options);
    }

    private static AppiumDriver createIOSDriver(URL serverUrl) {
        // Will be completed when LambdaTest setup happens
        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName(ConfigReader.get("ios.device.name"));
        options.setPlatformVersion(ConfigReader.get("ios.platform.version"));
        options.setApp(getAppPath());

        return new IOSDriver(serverUrl, options);
    }

    private static String getAppPath() {
        String projectRoot = System.getProperty("user.dir");
        String appRelativePath = ConfigReader.get(ConfigKeys.PLATFORM_NAME).equalsIgnoreCase("android") ?
                ConfigReader.get(ConfigKeys.ANDROID_APP_PATH) : ConfigReader.get(ConfigKeys.IOS_APP_PATH);
        return projectRoot + File.separator + appRelativePath;
    }

    private static String getChromePath() {
        String chromeDriverRelativePath = ConfigReader.get(ConfigKeys.CHROMEDRIVER_PATH);
        if (chromeDriverRelativePath == null || chromeDriverRelativePath.isEmpty()) {
            return "";
        }
        String projectRoot = System.getProperty("user.dir");
        return projectRoot + File.separator + chromeDriverRelativePath;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static boolean isAppInstalled(String packageName) {
        try {
            String adbPath = ConfigReader.get(ConfigKeys.ANDROID_ADB_PATH);
            String[] command = {
                    adbPath, "-s", ConfigReader.get(ConfigKeys.ANDROID_DEVICE_UDID),
                    "shell", "pm", "list", "packages", packageName
            };
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();
            boolean installed = output.toString().contains(packageName);
            System.out.println("App install check [" + packageName + "]: " +
                    (installed ? "INSTALLED" : "NOT INSTALLED"));
            return installed;
        } catch (Exception e) {
            System.out.println("Could not check app install status: " + e.getMessage());
            return false;
        }
    }

    /**
     * Detects the platform of the CURRENT live driver session by
     * checking the actual driver instance type — never a cached/stale
     * config value.
     *
     * @return "android" or "ios"
     * @throws IllegalStateException if the driver hasn't been
     *         initialized yet, or is an unrecognized type
     */
    public static String getExecutionOS() {
        if (driver == null) {
            throw new IllegalStateException(
                    "Driver not initialized yet — cannot detect platform. "
                            + "Call getDriver() first.");
        }
        if (driver instanceof AndroidDriver)
            return "android";
        else if (driver instanceof IOSDriver)
            return "ios";
        throw new IllegalStateException(
                "Unknown driver type: " + driver.getClass().getName());
    }

}