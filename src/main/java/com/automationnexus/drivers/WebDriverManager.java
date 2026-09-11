package com.automationnexus.drivers;

import com.automationnexus.config.ConfigReader;
import com.automationnexus.constants.ConfigKeys;
import com.automationnexus.constants.GlobalVariable;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class WebDriverManager   {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null)
            initializeDriver();
        return driver;
    }

    private static WebDriver initializeDriver() {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser")
                : ConfigReader.get(ConfigKeys.WEB_BROWSER_NAME);

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            if(GlobalVariable.WEB_ARGUMENTS_ENABLED) {
                chromeOptions.addArguments("--headless");
            }
            driver = new ChromeDriver(chromeOptions);
            driver.manage().window().setSize(new Dimension(1440,900));
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }else if (browser.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();
        }else {
            throw new RuntimeException(
                    "Unsupported browser: " + browser
            );
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
