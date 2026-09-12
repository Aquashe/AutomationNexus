package com.automationnexus.pages.web.ecommerce;

import com.automationnexus.base.BasePage;
import com.automationnexus.constants.GlobalVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.nio.file.WatchEvent;

public class SuccesPage extends BasePage {

    // ─── Constructor ─────────────────────────────────────────
    public SuccesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ─── Strings ─────────────────────────────────────────────
    public String getStringThankYouForTheOrderTextLabel(){
        return "thankyou for the order.";
    }
    public String getStringOrdersHistoryPageButtonLinkLabel(){
        return " Orders History Page ";
    }

    // ─── Locators ────────────────────────────────────────────
    private By getObjectTitleSuccess() {
        return findTestObject();
    }
    private By getObjectButtonLinkOrdersHistoryPage() {
        return findTestObject();
    }
    private By getObjectTextOrderId() {
        return findTestObject();
    }

    // ─── Page Methods ─────────────────────────────────────────
    public String getSuccessTitle() {
        webUI.waitForElementToBeDisplayed(getObjectTitleSuccess(), GlobalVariable.WAIT_LONG);
        return webUI.getText(getObjectTitleSuccess(), GlobalVariable.WAIT_SHORT);
    }

    public void verifySuccessPage(){
        Assert.assertEquals(this.getSuccessTitle().toLowerCase().trim(),
                getStringThankYouForTheOrderTextLabel().toLowerCase().trim());
    }

    public void clickButtonLinkOrderHistoryPage(){
        webUI.waitForElementToBeDisplayed(getObjectButtonLinkOrdersHistoryPage(), GlobalVariable.WAIT_MEDIUM);
        webUI.waitForElementToBeDisplayed(getObjectTextOrderId(), GlobalVariable.WAIT_MEDIUM);

        GlobalVariable.ORDER_ID = webUI.getText(getObjectTextOrderId(),
                GlobalVariable.WAIT_SHORT).replace("|", "").trim();
        logInfo("Fetching Order Id : "+GlobalVariable.ORDER_ID);
        webUI.delay(GlobalVariable.WAIT_SHORT);

        logInfo("Click Button Link : " + getStringOrdersHistoryPageButtonLinkLabel());
        webUI.click(getObjectButtonLinkOrdersHistoryPage(), GlobalVariable.WAIT_SHORT);
    }

    public void performSuccesPage(boolean clickButtonLinkOrderHistoryPage){
        if(clickButtonLinkOrderHistoryPage)
            this.clickButtonLinkOrderHistoryPage();
    }
}
