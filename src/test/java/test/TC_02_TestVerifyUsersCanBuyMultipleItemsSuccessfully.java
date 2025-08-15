package test;

import com.codeborne.selenide.Selenide;
import dataprovider.UserDataProvider;
import enums.Pages;
import helper.DriverUtils;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.util.List;

@Slf4j
@Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class)
public class TC_02_TestVerifyUsersCanBuyMultipleItemsSuccessfully extends TestBase {

    SoftAssert softAssert = new SoftAssert();
    MyAccountPage myAccountPage = new MyAccountPage();
    HomePage homePage = new HomePage();
    ProductsPage productsPage = new ProductsPage();
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
    CheckoutPage checkoutPage = new CheckoutPage();
    OrderStatusPage orderStatusPage = new OrderStatusPage();

    List<Product> expectedProductsInfo;
    List<Product> actualProductsInfo;
    List<Product> purchasedProductsNames;

    public void VerifyUsersCanBuyMultipleItemsSuccessfully(User user) {

        homePage.clickOnMyAccountButton();

        myAccountPage.login(User.defaultUser());

        myAccountPage.navigateToShopPage();

        expectedProductsInfo = productsPage.addMultipleProductsToCartAndGetInfo(3);

        productsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        actualProductsInfo = shoppingCartPage.getAllProductsInCart(); // here

        softAssert.assertEquals(actualProductsInfo, expectedProductsInfo, "step 5");

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.fillBillingInfo(user);

        checkoutPage.clickOnPlaceOrderBtn();

        softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
                "order status page is displayed");

        softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed());

        purchasedProductsNames = orderStatusPage.getMultipleProductsInfo();

        softAssert.assertEquals(purchasedProductsNames, expectedProductsInfo, "step 6");

        softAssert.assertAll();
    }
}
