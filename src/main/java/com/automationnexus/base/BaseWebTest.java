package com.automationnexus.base;

import com.automationnexus.constants.GlobalVariable;
import com.automationnexus.drivers.DriverManager;
import com.automationnexus.drivers.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseWebTest {
    protected WebDriver driver;
    protected static final Logger log = LogManager.getLogger(BaseWebTest.class);

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        driver = WebDriverManager.getDriver();
        DriverManager.setDriver(driver);
        driver.get(GlobalVariable.URL_ECOMMERCE);
        log.info("Driver initialized successfully with Url: "+ driver.getCurrentUrl());
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        WebDriverManager.quitDriver();
    }

}
