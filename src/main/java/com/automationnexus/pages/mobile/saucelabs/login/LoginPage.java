package com.automationnexus.pages.mobile.saucelabs.login;

import com.automationnexus.base.BasePage;
import com.automationnexus.constants.GlobalVariable;
import com.automationnexus.enums.FailureHandling;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage extends BasePage {

    // ─── Constructor ─────────────────────────────────────────
    public LoginPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
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

    // ─── Strings ─────────────────────────────────────────────
    public String getStringUsernameTextField() {
        return "Username";
    }
    public String getStringPasswordTextField() {
        return "Password";
    }
    public String getStringLoginButtonLabel() {
        return "LOGIN";
    }
    public String getStringUsernameIsRequiredTextLabel() {
        return "Username is required";
    }
    public String getStringPasswordIsRequiredTextLabel() {
        return "Password is required";
    }
    public String getStringUsernameAndPasswordDoNotMatchAnyUserInThisServiceTextLabel() {
        return "Username and password do not match any user in this service.";
    }

    // ─── Page Methods ─────────────────────────────────────────
    public void enterUsername(String username) {
        mobile.setText(getObjectTextFieldUsername(), username, GlobalVariable.WAIT_MEDIUM);
    }
    public void enterPassword(String password) {
        mobile.setText(getObjectTextFieldPassword(), password, GlobalVariable.WAIT_MEDIUM);
    }
    public void tapLoginButton() {
        mobile.tap(getObjectButtonLogin(), GlobalVariable.WAIT_MEDIUM);
    }
    public void verifyErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = mobile.getTextByAttribute(getObjectTextErrorMessage(), "text", GlobalVariable.WAIT_MEDIUM);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
                "Error message does not match.");
    }

    /**
     * Perform login action with the provided username and password.
     *
     * @param username       String
     * @param password       String
     * @param tapLoginButton Boolean
     */
    public void performLoginPage(String username, String password, boolean tapLoginButton) {
        if (username != null)
            enterUsername(username);
        if (password != null)
            enterPassword(password);
        if (tapLoginButton)
            tapLoginButton();
    }
}
