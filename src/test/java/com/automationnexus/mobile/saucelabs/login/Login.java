package com.automationnexus.mobile.saucelabs.login;

import com.automationnexus.base.BaseMobileTest;
import com.automationnexus.constants.GlobalVariable;
import com.automationnexus.pages.mobile.saucelabs.home.HomePage;
import com.automationnexus.pages.mobile.saucelabs.login.LoginPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Login extends BaseMobileTest {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeClass(alwaysRun = true)
    public void setUpPage() {
        this.loginPage = new LoginPage(driver);
        this.homePage = new HomePage(driver);
    }

    @Test(priority = 1, groups = {"sauce labs", "regression", "regression-login"},
            description = "Verify error message on invalid username")
    public void validateInvalidUsernameLogin() {
        loginPage.performLoginPage(GlobalVariable.INVALID_USERNAME, GlobalVariable.VALID_PASSWORD, true);
        loginPage.verifyErrorMessage(
                loginPage.getStringUsernameAndPasswordDoNotMatchAnyUserInThisServiceTextLabel());
    }

    @Test(priority = 2, groups = {"sauce labs", "regression", "regression-login"},
            description = "Verify error message on invalid password")
    public void validateInvalidPasswordLogin() {
        loginPage.performLoginPage(GlobalVariable.VALID_USERNAME, GlobalVariable.INVALID_PASSWORD, true);
        loginPage.verifyErrorMessage(
                loginPage.getStringUsernameAndPasswordDoNotMatchAnyUserInThisServiceTextLabel());
    }

    @Test(priority = 3, groups = {"sauce labs", "regression", "regression-login"},
            description = "Verify error message on Empty username")
    public void validateEmptyUsernameLogin() {
        loginPage.performLoginPage("", GlobalVariable.VALID_PASSWORD, true);
        loginPage.verifyErrorMessage(
                loginPage.getStringUsernameIsRequiredTextLabel());
    }

    @Test(priority = 4, groups = {"sauce labs", "regression", "regression-login"},
            description = "Verify error message on Empty password")
    public void validateEmptyPasswordLogin() {
        loginPage.performLoginPage(GlobalVariable.VALID_USERNAME, "", true);
        loginPage.verifyErrorMessage(
                loginPage.getStringPasswordIsRequiredTextLabel());
    }

    @Test(priority = 5, groups = {"sauce labs", "smoke", "smoke-login", "regression", "regression-login"},
            description = "Verify user can login with valid credentials")
    public void validateValidLogin() {
        loginPage.performLoginPage(GlobalVariable.VALID_USERNAME, GlobalVariable.VALID_PASSWORD, true);
        homePage.waitForHomePage();
    }
}
