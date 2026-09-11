package com.automationnexus.web.ecommerce.login;

import com.automationnexus.base.BaseWebTest;
import com.automationnexus.constants.GlobalVariable;
import com.automationnexus.pages.web.ecommerce.LoginPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest extends BaseWebTest {
    private LoginPage loginPage;

    @BeforeClass(alwaysRun = true)
    public void setUpPage() {
        this.loginPage = new LoginPage(driver);
    }

    @Test
    public void loginWithIncorrectEmailOrPassword(){
        loginPage.performLoginPage(GlobalVariable.USERNAME_ECOMMERCE,GlobalVariable.INVALID_PASSWORD, true);
        loginPage.verifyErrorMessage(loginPage.getStringIncorrectEmailOrPassword());
    }
}
