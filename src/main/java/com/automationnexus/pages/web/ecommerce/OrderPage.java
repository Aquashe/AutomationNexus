package com.automationnexus.pages.web.ecommerce;

import com.automationnexus.base.BasePage;
import com.automationnexus.constants.GlobalVariable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class OrderPage extends BasePage {

    private Actions actions;

    // ─── Constructor ─────────────────────────────────────────
    public OrderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }

    // ─── Strings ─────────────────────────────────────────────
    public String getStringYourOrdersTitleLabel(){
        return "Your Orders";
    }
    public String getStringViewButtonLabel(){
        return "View";
    }
    public String getStringDeleteButtonLabel(){
        return "Delete";
    }

    // ─── Locators ────────────────────────────────────────────
    private By getObjectTitleYourOrders() {
        return findTestObject();
    }
    private By getObjectContainerOrders() {
        return findTestObject();
    }
    private By getObjectButtonView(String orderId) {
        return findTestObject(Map.of("orderId", orderId));
    }
    private By getObjectButtonDelete(String orderId) {
        return findTestObject(Map.of("orderId", orderId));
    }
    private By getObjectButtonGoBackToShop() {
        return findTestObject();
    }
    private By getObjectButtonGoBackToCart() {
        return findTestObject();
    }

    // ─── Page Methods ─────────────────────────────────────────
    public boolean checkProductComeInOrders(String productName){
        List<WebElement> ordersRow = driver.findElements(getObjectContainerOrders());
        return ordersRow.stream()
                .map(row->row.findElement(By.xpath(".//td[2]")))
                .anyMatch(webElement -> {
                    System.out.println("Product Inside Orders :"+webElement.getText());
                    return webElement.getText().equalsIgnoreCase(productName);
                });
    }

    public void checkProductOrderPresentOrNot(String productName){
        Assert.assertTrue(this.checkProductComeInOrders(productName),
                String.format("Product : %s is not found in Orders", productName));
    }

    public void clickButtonView(String orderId){
        webUI.waitForElementToBeDisplayed(getObjectButtonView(orderId), GlobalVariable.WAIT_MEDIUM);
        webUI.delay(GlobalVariable.WAIT_SHORT);

        logInfo("Clicking Button : "+getStringViewButtonLabel());
        webUI.click(getObjectButtonView(orderId),  GlobalVariable.WAIT_SHORT);
    }

    public void clickButtonDelete(String orderId){
        webUI.waitForElementToBeDisplayed(getObjectButtonDelete(orderId), GlobalVariable.WAIT_MEDIUM);
        webUI.delay(GlobalVariable.WAIT_SHORT);

        logInfo("Clicking Button : "+getStringDeleteButtonLabel());
        webUI.click(getObjectButtonDelete(orderId),  GlobalVariable.WAIT_SHORT);
    }

    /**
     *
     * @param orderId String
     * @param clickButtonView Boolean
     * @param clickButtonDelete Boolean
     */
    public void performOrderPage(String orderId, boolean clickButtonView, boolean clickButtonDelete){
        if(orderId != null && clickButtonView)
            this.clickButtonView(orderId);
        if(orderId != null && clickButtonDelete)
            this.clickButtonDelete(orderId);
    }
}
