package test;

import dataprovider.UserDataProvider;
import enums.NavItems;
import enums.Pages;
import enums.PaymentMethod;
import helper.DriverUtils;
import lombok.extern.slf4j.Slf4j;
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
public class TC_03_TestVerifyUsersCanBuyAnItemUsingDifferentPaymentMethods extends TestBase {

  SoftAssert softAssert = new SoftAssert();
  MyAccountPage myAccountPage = new MyAccountPage();
  HomePage homePage = new HomePage();
  ProductsPage productsPage = new ProductsPage();
  ProductDetailsPage productDetailsPage = new ProductDetailsPage();
  ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
  CheckoutPage checkoutPage = new CheckoutPage();
  OrderStatusPage orderStatusPage = new OrderStatusPage();

  public void TestBankTransferPaymentMethod(User user) {
    homePage.clickOnMyAccountButton();

//  2. Login with valid credentials
    myAccountPage.login(user);

//  3. Go to Shop page
    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

//  4. Select an item and add to cart
    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();
    productDetailsPage.clickOnMyCartButton();

//  5. Go to Checkout page
    shoppingCartPage.clickCheckoutBtn();

//  6. Choose a different payment method (Direct bank transfer, Cash on delivery)
    checkoutPage.choosePaymentMethod(PaymentMethod.DIRECT_BANK_TRANFER.getMethod());

//  7. Complete the payment process
    checkoutPage.fillBillingInfo(user);
    checkoutPage.clickOnPlaceOrderBtn();

//  8. Verify order confirmation message
    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");
    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
        "confirmation message is displayed");

//  Expected result: Payment is processed successfully for each available method

    softAssert.assertEquals(PaymentMethod.DIRECT_BANK_TRANFER.getMethod(),
        orderStatusPage.getPaymentMethod());

    softAssert.assertAll();
  }

  public void TestCHECKPaymentMethod(User user) {
    homePage.clickOnMyAccountButton();

//  2. Login with valid credentials
    myAccountPage.login(user);

//  3. Go to Shop page
    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

//  4. Select an item and add to cart
    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();
    productDetailsPage.clickOnMyCartButton();

//  5. Go to Checkout page
    shoppingCartPage.clickCheckoutBtn();

//  6. Choose a different payment method (Direct bank transfer, Cash on delivery)
    checkoutPage.choosePaymentMethod(PaymentMethod.CHECK_PAYMENTS.getMethod());

//  7. Complete the payment process
    checkoutPage.fillBillingInfo(user);
    checkoutPage.clickOnPlaceOrderBtn();

//  8. Verify order confirmation message
    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");
    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
        "confirmation message is displayed");

//  Expected result: Payment is processed successfully for each available method

    softAssert.assertEquals(PaymentMethod.CHECK_PAYMENTS.getMethod(),
        orderStatusPage.getPaymentMethod());

    softAssert.assertAll();
  }

  public void TestCODPaymentMethod(User user) {
    homePage.clickOnMyAccountButton();

//  2. Login with valid credentials
    myAccountPage.login(user);

//  3. Go to Shop page
    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

//  4. Select an item and add to cart
    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();
    productDetailsPage.clickOnMyCartButton();

//  5. Go to Checkout page
    shoppingCartPage.clickCheckoutBtn();

//  6. Choose a different payment method (Direct bank transfer, Cash on delivery)
    checkoutPage.choosePaymentMethod(PaymentMethod.COD.getMethod());

//  7. Complete the payment process
    checkoutPage.fillBillingInfo(user);
    checkoutPage.clickOnPlaceOrderBtn();

//  8. Verify order confirmation message
    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");
    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
        "confirmation message is displayed");

//  Expected result: Payment is processed successfully for each available method

    softAssert.assertEquals(PaymentMethod.COD.getMethod(), orderStatusPage.getPaymentMethod());

    softAssert.assertAll();
  }
}
