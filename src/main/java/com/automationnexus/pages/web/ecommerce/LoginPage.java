package com.automationnexus.pages.web.ecommerce;

import com.automationnexus.base.BasePage;
import com.automationnexus.constants.GlobalVariable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage extends BasePage {

    // ─── Constructor ─────────────────────────────────────────
    public  LoginPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ─── Strings ─────────────────────────────────────────────
    public String getStringLoginButtonLabel(){
        return "Login";
    }
    public String getStringIncorrectEmailOrPassword(){
        return "Incorrect email or password.";
    }

    // ─── Locators ────────────────────────────────────────────
    private By getObjectTextFieldUsername() {
        return findTestObject();
    }
    private By getObjectTextFieldPassword() {
        return findTestObject();
    }
    private By getObjectButtonLogin() {
        return findTestObject();
    }
    private By getObjectTextErrorMessage() {
        return findTestObject();
    }
    /*
    @FindBy(css = "div[class*='flyInOut']")
    WebElement textIncorrectEmailOrPassword;
    .ng-tns-c4-13.ng-star-inserted.ng-trigger.ng-trigger-flyInOut.ngx-toastr.toast-error
    */

    // ─── Page Methods ─────────────────────────────────────────
    public void enterUsername(String username) {
        logInfo("Enter username: " + username);
        webUI.setText(getObjectTextFieldUsername(), username, GlobalVariable.WAIT_MEDIUM);
    }
    public void enterPassword(String password) {
        logInfo("Enter password: " + password);
        webUI.setText(getObjectTextFieldPassword(), password, GlobalVariable.WAIT_MEDIUM);
    }
    public void clickLoginButton(){
        logInfo("Click Button : "+getStringLoginButtonLabel());
        webUI.sendKeys(getObjectButtonLogin(), GlobalVariable.WAIT_MEDIUM, Keys.chord(Keys.ENTER));
    }
    public void verifyErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = webUI.getText(getObjectTextErrorMessage(), GlobalVariable.WAIT_MEDIUM);
        Assert.assertEquals(actualErrorMessage.trim(), expectedErrorMessage,
                "Error message does not match.");
    }

    /**
     *
     * @param email String
     * @param password String
     * @param clickLogin String
     */
    public void performLoginPage(String email, String password, boolean clickLogin){
        if (email != null)
            enterUsername(email);
        if (password != null)
            enterPassword(password);
        if (clickLogin)
            clickLoginButton();
    }
// END REGION
}
