package com.automationnexus.base;

import com.automationnexus.drivers.MobileDriverManager;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseMobileTest {
    protected AppiumDriver driver;
    protected static final Logger log = LogManager.getLogger(BaseMobileTest.class);

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = MobileDriverManager.getDriver();
        log.info("Driver initialized successfully with Session ID "+ driver.getSessionId());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        MobileDriverManager.quitDriver();
    }
}
