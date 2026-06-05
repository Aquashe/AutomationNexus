package com.automationnexus.dummy;

import com.automationnexus.config.ConfigReader;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;

public class AndroidUiSelector {
    public static void main(String[] args) throws MalformedURLException {

        AppiumDriver driver = IntailizeDriver.getAppiumDriver(
                ConfigReader.get("platform.name"), ConfigReader.get("device.name"), ConfigReader.get("automation.name"),
                ConfigReader.get("device.udid"), ConfigReader.get("avd.name"), ConfigReader.getAppPath(),
                ConfigReader.get("appium.server.url"), 300);

        String uiSelector = "new UiSelector().text(\"Accessibility\")";
        WebElement elementByText =
                driver.findElement(AppiumBy.androidUIAutomator(uiSelector));
        System.out.println("Text : "+elementByText.getText());

        WebElement elementByClassName = driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().className(\"android.widget.TextView\")")).get(1);
        System.out.println("Text : "+elementByClassName.getText());

    }
}
