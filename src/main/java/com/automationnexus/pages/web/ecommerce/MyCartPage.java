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
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyCartPage extends BasePage {

    private final Actions actions;

    // ─── Constructor ─────────────────────────────────────────
    public  MyCartPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
    }

    // ─── Strings ─────────────────────────────────────────────
    public String getStringCheckoutButtonLabel(){
        return "Checkout";
    }

    // ─── Locators ────────────────────────────────────────────
    private By getObjectTitleMyCart() {
        return findTestObject();
    }
    private By getObjectContainerCartProduct() {
        return findTestObject();
    }
    private By getObjectButtonCheckout() {
        return findTestObject();
    }

    // ─── Page Methods ─────────────────────────────────────────
    private List<String > addedProductList(){
        webUI.waitForElementToBeDisplayed(getObjectTitleMyCart(), GlobalVariable.WAIT_MEDIUM);
        List<WebElement> cartProductContainersList = driver.findElements(getObjectContainerCartProduct());

        ArrayList<String> cartProductNames = cartProductContainersList.stream()
                .map(webElement -> webElement.findElement(By.xpath(".//h3")))
                .map(WebElement::getText)
                .collect(Collectors.toCollection(ArrayList::new));

        return cartProductNames.stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
    public void checkProductedInsideCartOrNot(String productName, boolean trueCheck){
        List<String > normalizedCartNames  = addedProductList();
        if(trueCheck)
            Assert.assertTrue(normalizedCartNames.contains(productName.trim().toLowerCase()),
                    String.format("%s not added inside cart", productName));
        else
            Assert.assertFalse(normalizedCartNames.contains(productName.trim().toLowerCase()),
                    String.format("Product %s can't be inside cart", productName));
    }
    public void  clickButtonCheckOut(){
        webUI.waitForElementToBeDisplayed(getObjectButtonCheckout(), GlobalVariable.WAIT_MEDIUM);
        actions.scrollToElement(driver.findElement(getObjectButtonCheckout())).build().perform();
        webUI.click(getObjectButtonCheckout(), GlobalVariable.WAIT_MEDIUM);
    }

    /**
     *
     * @param clickButtonCheckout Boolean
     */
    public void performMyCartPage(boolean clickButtonCheckout){
        if(clickButtonCheckout)
            this.clickButtonCheckOut();
    }
}
