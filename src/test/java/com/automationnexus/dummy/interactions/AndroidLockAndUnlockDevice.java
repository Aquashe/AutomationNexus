package com.automationnexus.dummy.interactions;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.time.Duration;

public class AndroidLockAndUnlockDevice {
        public static void main(String[] args) throws MalformedURLException, InterruptedException {
            System.out.println("This is a dummy class to perform Unlock and lock the device.");

            AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                    ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                    ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                    ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                    ConfigReader.get("appium.server.url"), 120);
            System.out.println(driver.getSessionId());


            ((AndroidDriver)driver).lockDevice(Duration.ofSeconds(3));
            Thread.sleep(3000);

            ((AndroidDriver)driver).lockDevice();
            System.out.println("Device is locked: " + ((AndroidDriver) driver).isDeviceLocked());

            ((AndroidDriver)driver).unlockDevice();
            System.out.println("Device is locked: " + ((AndroidDriver) driver).isDeviceLocked());

        }
}
