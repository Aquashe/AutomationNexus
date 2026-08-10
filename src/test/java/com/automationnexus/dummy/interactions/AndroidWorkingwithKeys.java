package com.automationnexus.dummy.interactions;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;

public class AndroidWorkingwithKeys {
        public static void main(String[] args) throws MalformedURLException, InterruptedException {
            System.out.println("This is a dummy class to perform working with keys in Android.");

            AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                    ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                    ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                    ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                    ConfigReader.get("appium.server.url"), 120);
            System.out.println(driver.getSessionId());
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));


            By buttonViews = AppiumBy.accessibilityId("Views");
            By buttonTextFields = AppiumBy.accessibilityId("TextFields");
            By textFieldHintText = AppiumBy.id("io.appium.android.apis:id/edit");

            driver.findElement(buttonViews).click();
            WebElement element = driver.findElement(AppiumBy.id("android:id/list"));
            driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
                    "elementId", ((RemoteWebElement) element).getId(),
                    "direction", "up",
                    "percent", 0.75
            ));
            driver.findElement(buttonTextFields).click();
            driver.findElement(textFieldHintText).click();

            System.out.println(((AndroidDriver) driver).isKeyboardShown());

            ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.A));
            ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.D));
            ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.B));
            Thread.sleep(3000);
            ((AndroidDriver) driver).hideKeyboard();
            ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.HOME));
            ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.CALENDAR));


        }
}
