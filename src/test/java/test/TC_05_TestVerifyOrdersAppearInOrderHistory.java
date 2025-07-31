package test;

import dataprovider.UserDataProvider;
import enums.NavItems;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import model.Order;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CheckoutPage;
import pages.HomePage;
import pages.MyAccountPage;
import pages.OrderStatusPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import pages.ShoppingCartPage;

@Slf4j
@Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class)
public class TC_05_TestVerifyOrdersAppearInOrderHistory extends TestBase {

  Order expectedFirstOrder;
  Order expectedSecondOrder;

  List<Order> expectedOrderList;
  List<Order> actualOrderList;

  SoftAssert softAssert = new SoftAssert();
  MyAccountPage myAccountPage = new MyAccountPage();
  HomePage homePage = new HomePage();
  ProductsPage productsPage = new ProductsPage();
  ProductDetailsPage productDetailsPage = new ProductDetailsPage();
  ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
  CheckoutPage checkoutPage = new CheckoutPage();
  OrderStatusPage orderStatusPage = new OrderStatusPage();

  public void TestVerifyOrdersAppearInOrderHistory(User user) {

//  Precondition: User has placed 02 orders
    homePage.clickOnMyAccountButton();
    myAccountPage.login(user);

//  create first order
    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());
    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();
    productDetailsPage.clickOnMyCartButton();
    shoppingCartPage.clickCheckoutBtn();
    checkoutPage.fillBillingInfo(user);
    checkoutPage.clickOnPlaceOrderBtn();
    expectedFirstOrder = orderStatusPage.getOrderInfo();

//  Create second order
    orderStatusPage.clickOnNavItem(NavItems.SHOP.getItemName());
    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();
    productDetailsPage.clickOnMyCartButton();
    shoppingCartPage.clickCheckoutBtn();
    checkoutPage.fillBillingInfo(user);
    checkoutPage.clickOnPlaceOrderBtn();
    expectedSecondOrder = orderStatusPage.getOrderInfo();

    expectedOrderList = Arrays.asList(expectedSecondOrder, expectedFirstOrder);

//  1. Go to My Account page
    orderStatusPage.clickOnMyAccountButton();

//  2. Click on Orders in left navigation
    myAccountPage.ClickOnOrdersBtn();

//  3. Verify order details
    actualOrderList = myAccountPage.getMultipleOrdersInfos(2);

//  Expected result: The orders are displayed in the user’s order history
    softAssert.assertEquals(actualOrderList, expectedOrderList);

    softAssert.assertAll();
  }
}
