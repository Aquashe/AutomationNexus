package com.automationnexus.pages.mobile.saucelabs.home;

import com.automationnexus.base.BasePage;
import com.automationnexus.constants.GlobalVariable;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class HomePage extends BasePage {

    // ─── Locators ────────────────────────────────────────────
    private By getObjectButtonSidemenu(){
        return findTestObject();
    }
    private By getObjectButtonCart(){
        return findTestObject();
    }
    private By getObjectButtonToggle(){
        return findTestObject();
    }
    private By getObjectButtonFilter(){
        return findTestObject();
    }
    private By getObjectTitleProducts(){
        return findTestObject();
    }

    // ─── Constructor ─────────────────────────────────────────
    public HomePage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // ─── Strings ─────────────────────────────────────────────
    public String getStringTitleProducts() {
        return "PRODUCTS";
    }


    // ─── Page Methods ─────────────────────────────────────────
    public void tapSidemenuButton() {
        mobile.tap(getObjectButtonSidemenu(), GlobalVariable.WAIT_MEDIUM);
    }
    public void tapCartButton() {
        mobile.tap(getObjectButtonCart(), GlobalVariable.WAIT_MEDIUM);
    }
    public void tapToggleButton() {
        mobile.tap(getObjectButtonToggle(), GlobalVariable.WAIT_MEDIUM);
    }
    public void tapFilterButton() {
        mobile.tap(getObjectButtonFilter(), GlobalVariable.WAIT_MEDIUM);
    }
    public void waitForHomePage(){
        new WebDriverWait(driver, Duration.ofSeconds(GlobalVariable.WAIT_LONG)).until(ExpectedConditions.visibilityOfElementLocated(getObjectTitleProducts()));
        Assert.assertEquals(mobile.getText(getObjectTitleProducts(), GlobalVariable.WAIT_MEDIUM), getStringTitleProducts());
    }
}
