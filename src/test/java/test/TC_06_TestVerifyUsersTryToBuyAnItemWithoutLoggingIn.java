package test;

import com.codeborne.selenide.Selenide;
import dataprovider.UserDataProvider;
import enums.Pages;
import helper.DriverUtils;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

@Slf4j
@Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class)
public class TC_06_TestVerifyUsersTryToBuyAnItemWithoutLoggingIn extends TestBase {

    SoftAssert softAssert = new SoftAssert();
    HomePage homePage = new HomePage();
    ProductsPage productsPage = new ProductsPage();
    ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
    CheckoutPage checkoutPage = new CheckoutPage();
    OrderStatusPage orderStatusPage = new OrderStatusPage();

    public void TestVerifyUsersTryToBuyAnItemWithoutLoggingIn(User user) {

        homePage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.ClickOnAddToCartBtn();

        productsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.fillBillingInfo(user);

        checkoutPage.clickOnPlaceOrderBtn();

        DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName());

        softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed());

        softAssert.assertAll();
    }
}
