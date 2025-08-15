package test;

import com.codeborne.selenide.Selenide;
import dataprovider.UserDataProvider;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

@Slf4j
@Test(dataProvider = "invalidUserData", dataProviderClass = UserDataProvider.class)
public class TC_07_TestErrorHandling extends TestBase {

    SoftAssert softAssert = new SoftAssert();
    HomePage homePage = new HomePage();
    MyAccountPage myAccountPage = new MyAccountPage();
    ProductsPage productsPage = new ProductsPage();
    ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
    CheckoutPage checkoutPage = new CheckoutPage();

    public void TestErrorHandling(User missingInfoUser) {

        homePage.clickOnMyAccountButton();

        myAccountPage.login(User.defaultUser());

        myAccountPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.ClickOnAddToCartBtn();

        productDetailsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.fillBillingInfo(missingInfoUser);

        checkoutPage.clickOnPlaceOrderBtn();

        softAssert.assertTrue(checkoutPage.isErrorMsgMatchMissingField());

        softAssert.assertAll();
    }

}
