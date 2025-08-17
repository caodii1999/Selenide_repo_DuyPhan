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

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.clickOnAddToCartBtn();

        productDetailsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.fillBillingInfo(user);

        expectedFirstOrder = orderStatusPage.getOrderInfo();

        orderStatusPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.clickOnAddToCartBtn();

        productDetailsPage.clickOnMyCartButton();

        Selenide.refresh(); //need to refresh due to page does not update product to cart at first

        shoppingCartPage.clickCheckoutBtn();

        checkoutPage.fillBillingInfo(user);

        expectedSecondOrder = orderStatusPage.getOrderInfo();

        expectedOrderList = Arrays.asList(expectedSecondOrder, expectedFirstOrder);

        orderStatusPage.clickOnMyAccountButton();

        myAccountPage.clickOnOrdersBtn();

        actualOrderList = myAccountPage.getMultipleOrdersInfos(2);

        softAssert.assertEquals(actualOrderList, expectedOrderList);

        softAssert.assertAll();
    }
}
