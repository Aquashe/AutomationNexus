package com.automationnexus.web.ecommerce.e2e;

import com.automationnexus.base.BaseWebTest;
import com.automationnexus.constants.GlobalVariable;
import com.automationnexus.pages.web.ecommerce.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class E2eProductTest extends BaseWebTest {

    private LoginPage loginPage;
    private ProductCatologuePage productCatologuePage;
    private MyCartPage myCartPage;
    private PlaceOrderPage placeOrderPage;
    private SuccesPage succesPage;
    private OrderPage orderPage;
    private OrderSummaryPage  orderSummaryPage;

    @BeforeClass(alwaysRun = true)
    public void setUpPage() {
        GlobalVariable.MAINTAINENCE_MODE = false;
        this.loginPage = new LoginPage(driver);
        this.productCatologuePage = new ProductCatologuePage(driver);
        this.myCartPage = new MyCartPage(driver);
        this.placeOrderPage = new PlaceOrderPage(driver);
        this.succesPage = new SuccesPage(driver);
        this.orderPage = new OrderPage(driver);
        this.orderSummaryPage = new OrderSummaryPage(driver);
    }

    @Parameters({"productName", "countryName"})
    @Test
    public void performE2eProductTest(String product,  String country) {
        loginPage.performLoginPage(GlobalVariable.USERNAME_ECOMMERCE,GlobalVariable.PASSWORD_ECOMMERCE, true);

        productCatologuePage.performProductCatologuePage(product,true, false);

        myCartPage.checkProductedInsideCartOrNot(product, true);
        myCartPage.performMyCartPage(true);

        placeOrderPage.performPlaceOrderPage(country, true);

        succesPage.verifySuccessPage();
        succesPage.performSuccesPage(true);

        orderPage.checkProductComeInOrders(product);
        orderPage.performOrderPage(GlobalVariable.ORDER_ID, true, false);

        orderSummaryPage.verifyOrderId(GlobalVariable.ORDER_ID);
        orderSummaryPage.performOrderSummaryPage(true);

        orderPage.performOrderPage(GlobalVariable.ORDER_ID, false, true);
    }
}
