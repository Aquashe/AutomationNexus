package com.automationnexus.dummy.hybrid;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.dummy.driverSessions.IntailizeDriver;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class ChomeBrowserAutomation {
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("This is a dummy class for Chrome browser automation.");

        AppiumDriver driver = IntailizeDriver.createBrowserSession(
                ConfigReader.get("platform.name"), ConfigReader.get("device.name"),
                ConfigReader.get("automation.name"), ConfigReader.get("device.udid"),
                ConfigReader.get("avd.name"), ConfigReader.get("appium.server.url"),
                320);
        driver.get("https://tesla.com");
        System.out.println(driver.getSessionId());
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(60));

        By buttonMenu = By.xpath("//span[text()='Menu']");
        By buttonAccept = By.xpath("//button[text()='Accept']");
        By buttonCloseIcon = By.xpath("(//*[text()='Confirm Your Location']/preceding::button)[last()]");
        By buttonVehicles = By.xpath("//span[text()='Vehicles']");
        By buttonOrderModels = By.xpath("(//h3[text()='Model S']/following::a[text()='Order'])[1]");
        By textFieldEnterZip = By.xpath("//p[text()='Vehicle Registration ZIP']/following::input[1]");


        System.out.println("Clicking on Menu button");
        driver.findElement(buttonMenu).click();

        System.out.println("Clicking on Accept button");
        driver.findElement(buttonAccept).click();

        System.out.println("Clicking on Close icon button");
        driver.findElement(buttonCloseIcon).click();

        System.out.println("Clicking on Vehicles button");
        driver.findElement(buttonVehicles).click();

        System.out.println("Clicking on Order Models button");
        driver.findElement(buttonOrderModels).click();

        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(120));
        System.out.println("Clicking on Enter Zip text field");
        wait.until(ExpectedConditions.elementToBeClickable(textFieldEnterZip)).click();

        System.out.println("Entering Zip code in the text field");
        driver.findElement(textFieldEnterZip).sendKeys("12345");
    }
}
