package com.automationnexus.pages.web.ecommerce;

import com.automationnexus.base.BasePage;
import com.automationnexus.constants.GlobalVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatologuePage extends BasePage {

    private final Actions actions;
    private final Logger log = LogManager.getLogger(ProductCatologuePage.class);

    // ─── Constructor ─────────────────────────────────────────
    public  ProductCatologuePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }

    // ─── Strings ─────────────────────────────────────────────
    public String getStringCartButtonLabel(){
        return "Cart";
    }
    public String getStringOrdersButtonLabel(){
        return "Orders";
    }

    // ─── Locators ────────────────────────────────────────────
    private By getObjectButtonCart() {
        return findTestObject();
    }
    private By getObjectButtonOrders() {
        return findTestObject();
    }
    private By getObjectContainerProducts() {
        return findTestObject();
    }
    private By getObjectTextPopup() {
        return findTestObject();
    }
    private By getObjectIconLoading() {
        return findTestObject();
    }

    // ─── Page Methods ─────────────────────────────────────────
    public void clickButtonCart(){
        logInfo("Click Button : " + getStringCartButtonLabel());
        webUI.click(getObjectButtonCart(), GlobalVariable.WAIT_MEDIUM);
    }
    public void clickButtonOrders(){
        logInfo("Click Button : " + getStringOrdersButtonLabel());
        webUI.click(getObjectButtonOrders(), GlobalVariable.WAIT_MEDIUM);
    }


    public void addProductToCart(String productName){
        logInfo("Adding Product : " + productName);
        List<WebElement> products = driver.findElements(getObjectContainerProducts());
        WebElement productElement = products.stream()
                .map(webElement -> webElement.findElement(By.cssSelector(" h5")))
                .filter(product -> product.getText().equalsIgnoreCase(productName))
                .findFirst()
                .orElse(null);

        if (productElement != null) {
            actions.scrollToElement(productElement).build().perform();
            WebElement buttonProductAddToCart = productElement.findElement(By.xpath(
                    "following-sibling::button[last()]"));

            actions.scrollToElement(buttonProductAddToCart).build().perform();
            System.out.println("Adding Product : "+productName);
            buttonProductAddToCart.click();
        }
        webUI.waitForElementToBeDisplayed(getObjectTextPopup(), GlobalVariable.WAIT_MEDIUM);
        webUI.waitForElementToBeDisplayed(getObjectIconLoading(), GlobalVariable.WAIT_MEDIUM);
    }

    /**
     *
     * @param clickButtonCart Boolean
     * @param clickButtonOrders Boolean
     * @param productNameToAdd Boolean
     */
    public void performProductCatologuePage(boolean clickButtonCart, boolean clickButtonOrders, String productNameToAdd){
        if (clickButtonCart)
            this.clickButtonCart();
        if (clickButtonOrders)
            this.clickButtonOrders();
        if (productNameToAdd != null)
            this.addProductToCart(productNameToAdd);
    }
    // END : PAGE METHODS

}
