package test;

import com.codeborne.selenide.Selenide;
import dataprovider.UserDataProvider;
import lombok.extern.slf4j.Slf4j;
import model.Order;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class)
public class TC_05_TestVerifyOrdersAppearInOrderHistory extends TestBase {

    SoftAssert softAssert = new SoftAssert();
    MyAccountPage myAccountPage = new MyAccountPage();
    HomePage homePage = new HomePage();
    ProductsPage productsPage = new ProductsPage();
    ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
    CheckoutPage checkoutPage = new CheckoutPage();
    OrderStatusPage orderStatusPage = new OrderStatusPage();

    Order expectedFirstOrder;
    Order expectedSecondOrder;

    List<Order> expectedOrderList;
    List<Order> actualOrderList;

    public void TestVerifyOrdersAppearInOrderHistory(User user) {

//  Precondition:
        homePage.clickOnMyAccountButton();
        myAccountPage.login(User.defaultUser());

//  create first order
        myAccountPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.ClickOnAddToCartBtn();

        productDetailsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.fillBillingInfo(user);

        checkoutPage.clickOnPlaceOrderBtn();

        expectedFirstOrder = orderStatusPage.getOrderInfo();

//  Create second order
        orderStatusPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.ClickOnAddToCartBtn();

        productDetailsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.fillBillingInfo(user);

        checkoutPage.clickOnPlaceOrderBtn();

        expectedSecondOrder = orderStatusPage.getOrderInfo();

        expectedOrderList = Arrays.asList(expectedSecondOrder, expectedFirstOrder);

//  1. Go to My Account page
        orderStatusPage.clickOnMyAccountButton();

        myAccountPage.ClickOnOrdersBtn();

        actualOrderList = myAccountPage.getMultipleOrdersInfos(2);

//  Expected result: The orders are displayed in the user’s order history
        softAssert.assertEquals(actualOrderList, expectedOrderList);

        softAssert.assertAll();
    }
}
