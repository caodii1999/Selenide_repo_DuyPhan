package test;

import com.codeborne.selenide.Selenide;
import dataprovider.UserDataProvider;
import enums.Pages;
import enums.PaymentMethod;
import helper.DriverUtils;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

@Slf4j
@Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class)
public class TC_03_TestVerifyUsersCanBuyAnItemUsingDifferentPaymentMethods extends TestBase {


    MyAccountPage myAccountPage = new MyAccountPage();
    HomePage homePage = new HomePage();
    ProductsPage productsPage = new ProductsPage();
    ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
    CheckoutPage checkoutPage = new CheckoutPage();
    OrderStatusPage orderStatusPage = new OrderStatusPage();

    public void TestBankTransferPaymentMethod(User user) {

        SoftAssert softAssert = new SoftAssert();

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.clickOnAddToCartBtn();

        productDetailsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.choosePaymentMethod(PaymentMethod.DIRECT_BANK_TRANSFER.getMethod());

        checkoutPage.fillBillingInfo(user);

        softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
                "order status page is displayed");
        softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
                "confirmation message is displayed");

        softAssert.assertEquals(PaymentMethod.DIRECT_BANK_TRANSFER.getMethod(),
                orderStatusPage.getPaymentMethod());

        softAssert.assertAll();
    }

    public void TestCHECKPaymentMethod(User user) {

        SoftAssert softAssert = new SoftAssert();

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.clickOnAddToCartBtn();

        productDetailsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.choosePaymentMethod(PaymentMethod.CHECK_PAYMENTS.getMethod());

        checkoutPage.fillBillingInfo(user);

        softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
                "order status page is displayed");
        softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
                "confirmation message is displayed");

        softAssert.assertEquals(PaymentMethod.CHECK_PAYMENTS.getMethod(),
                orderStatusPage.getPaymentMethod());

        softAssert.assertAll();
    }

    public void TestCODPaymentMethod(User user) {

        SoftAssert softAssert = new SoftAssert();

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.clickOnAddToCartBtn();

        productDetailsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.choosePaymentMethod(PaymentMethod.COD.getMethod());

        checkoutPage.fillBillingInfo(user);

        softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
                "order status page is displayed");
        softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
                "confirmation message is displayed");

        softAssert.assertEquals(PaymentMethod.COD.getMethod(), orderStatusPage.getPaymentMethod());

        softAssert.assertAll();
    }
}
