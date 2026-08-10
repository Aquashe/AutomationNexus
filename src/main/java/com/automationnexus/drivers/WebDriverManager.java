package com.automationnexus.drivers;

import com.automationnexus.config.ConfigReader;
import org.openqa.selenium.WebDriver;

public class WebDriverManager   {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null)
            initializeDriver();
        return driver;
    }

    private static void initializeDriver() {
        String browser = ConfigReader.get("web.browser");

        if (browser.equalsIgnoreCase("chrome")) {
//            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
//            driver = new FirefoxDriver();
        } else {
            throw new RuntimeException(
                    "Unsupported browser: " + browser
            );
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
