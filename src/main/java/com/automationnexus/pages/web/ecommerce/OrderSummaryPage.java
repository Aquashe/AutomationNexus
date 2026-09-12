package com.automationnexus.pages.web.ecommerce;

import com.automationnexus.base.BasePage;
import com.automationnexus.constants.GlobalVariable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class OrderSummaryPage extends BasePage {

    // ─── Constructor ─────────────────────────────────────────
    public OrderSummaryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ─── Strings ─────────────────────────────────────────────
    public String getStringThankYouForShoppingWithUsTitleLabel(){
        return "Thank you for Shopping With Us";
    }
    public String getStringOrderSummaryTextLabel(){
        return "order summary";
    }
    public String getStringOrderIdTextLabel(){
        return "Order Id";
    }
    public String getStringViewOrdersButtonLabel(){
        return "View Orders";
    }

    // ─── Locators ────────────────────────────────────────────
    private By getObjectTitleThankYouForShoppingWithUs() {
        return findTestObject();
    }
    private By getObjectTextOrderSummary() {
        return findTestObject();
    }
    private By getObjectTextOrderIdValue() {
        return findTestObject();
    }
    private By getObjectButtonViewOrders() {
        return findTestObject();
    }

    // ─── Page Methods ─────────────────────────────────────────
    public void verifyOrderId(String orderId){
        webUI.waitForElementToBeDisplayed(getObjectTitleThankYouForShoppingWithUs(), GlobalVariable.WAIT_LONG);
        webUI.delay(GlobalVariable.WAIT_MEDIUM);
        logInfo("Verifying Order Id : "+orderId);
        String actualOrderId = webUI.getText(getObjectTextOrderIdValue(), GlobalVariable.WAIT_SHORT);
        Assert.assertEquals(actualOrderId, orderId);
    }

    public void clickButtonViewOrders(){
        webUI.waitForElementToBeDisplayed(getObjectButtonViewOrders(), GlobalVariable.WAIT_MEDIUM);
        logInfo("Clicking Button : "+getObjectButtonViewOrders());
        webUI.click(getObjectButtonViewOrders(),  GlobalVariable.WAIT_SHORT);
    }

    public void performOrderSummaryPage(boolean clickButtonViewOrders){
        if(clickButtonViewOrders)
            this.clickButtonViewOrders();
    }
}
