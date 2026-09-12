package com.automationnexus.pages.web.ecommerce;

import com.automationnexus.base.BasePage;
import com.automationnexus.constants.GlobalVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class PlaceOrderPage extends BasePage {

    private final Actions actions;

    // ─── Constructor ─────────────────────────────────────────
    public PlaceOrderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.actions =new Actions(driver);
    }

    // ─── Strings ─────────────────────────────────────────────
    public String getStringSelectCountryTextFieldLabel(){
        return "Select Country";
    }
    public String getStringPlaceOrderButtonLabel(){
        return "Place Order";
    }

    // ─── Locators ────────────────────────────────────────────
    private By getObjectTextFieldSelectCountry() {
        return findTestObject();
    }
    private By getObjectButtonPlaceOrder() {
        return findTestObject();
    }

    // ─── Page Methods ─────────────────────────────────────────
    public void chooseCountry(String country){
        log.info("Choose " + getStringSelectCountryTextFieldLabel() + " : " + country);
        webUI.click(getObjectTextFieldSelectCountry(), GlobalVariable.WAIT_MEDIUM);
        int len = country.length();
        WebElement textFieldCoutry = driver.findElement(getObjectTextFieldSelectCountry());

        actions.sendKeys(textFieldCoutry, country.substring(0, len - (len-2) ))
                .build().perform();

        textFieldCoutry.findElements(By.xpath(
                        "following-sibling::section/button")).stream()
                .filter(contryElement->contryElement.getText().equalsIgnoreCase(country))
                .findFirst()
                .ifPresent(WebElement::click);
    }
    public void clickButtonPlaceOrder(){
        log.info("Clicking Button :" + getStringPlaceOrderButtonLabel());
        webUI.waitForElementToBeDisplayed(getObjectButtonPlaceOrder(), GlobalVariable.WAIT_MEDIUM);
        webUI.click(getObjectButtonPlaceOrder(),  GlobalVariable.WAIT_MEDIUM);
    }

    /**
     *
     * @param country String
     * @param clickButtonPlaceOrder Boolean
     */
    public void performPlaceOrderPage(String country, boolean clickButtonPlaceOrder){
        if(country != null)
            this.chooseCountry(country);
        if(clickButtonPlaceOrder)
            this.clickButtonPlaceOrder();
    }
}
