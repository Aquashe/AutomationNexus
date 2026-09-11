package com.automationnexus.base;

import com.automationnexus.drivers.DriverManager;
import com.automationnexus.drivers.MobileDriverManager;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseMobileTest {
    protected AppiumDriver driver;
    protected static final Logger log = LogManager.getLogger(BaseMobileTest.class);

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        driver = MobileDriverManager.getDriver();
        DriverManager.setDriver(driver);
        log.info("Driver initialized successfully with Session ID "+ driver.getSessionId());
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        MobileDriverManager.quitDriver();
    }
}
