package com.automationnexus.dummy.interactions;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import org.openqa.selenium.By;

import java.net.MalformedURLException;
import java.time.Duration;

public class AndroidInteractionWithApp {
        public static void main(String[] args) throws MalformedURLException, InterruptedException {
            System.out.println("This is a dummy class to perform interactions on mobile elements.");

            AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                    ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                    ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                    ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                    ConfigReader.get("appium.server.url"), 520);
            System.out.println(driver.getSessionId());

            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

            By buttonViews = AppiumBy.accessibilityId("Views");
            driver.findElement(buttonViews).click();

            //1. Terminate the app
            Thread.sleep(5000);
            ((AndroidDriver) driver).terminateApp(ConfigReader.get("app.package.name"));

            //2. Check if the app is installed
            Thread.sleep(3000);
            ((AndroidDriver) driver).installApp(ConfigReader.getAppPath());
            System.out.println(((AndroidDriver) driver).isAppInstalled(ConfigReader.get("app.package.name")));

            //3. Launch the app again
            Thread.sleep(3000);
            System.out.println("Launching the app again...");
            ((AndroidDriver) driver).activateApp(ConfigReader.get("app.package.name"));

            //4. Run the app in background
            Thread.sleep(3000);
            System.out.println("Run the app in background...");
            ((AndroidDriver) driver).runAppInBackground(Duration.ofSeconds(5));

            //4. Activate another app
            Thread.sleep(3000);
            System.out.println("Launching the app again...");
            ((AndroidDriver) driver).activateApp("com.android.settings");

            //5.check query app state
            Thread.sleep(3000);
            ApplicationState applicationStateOne = ((AndroidDriver) driver).queryAppState(ConfigReader.get("app.package.name"));
            System.out.println("Application state: " + applicationStateOne);

            Thread.sleep(3000);
            ((AndroidDriver) driver).activateApp(ConfigReader.get("app.package.name"));
            ApplicationState applicationStateTwo = ((AndroidDriver) driver).queryAppState(ConfigReader.get("app.package.name"));
            System.out.println("Application state: " + applicationStateTwo);

             driver.quit();


        }
}
