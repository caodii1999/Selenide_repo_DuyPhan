package test;

import static com.codeborne.selenide.Selenide.refresh;

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

    myAccountPage.login(user);

    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();
    productDetailsPage.clickOnMyCartButton();

    shoppingCartPage.clickCheckoutBtn();

    checkoutPage.choosePaymentMethod(PaymentMethod.DIRECT_BANK_TRANSFER.getMethod());

    checkoutPage.fillBillingInfo(user);
    checkoutPage.clickOnPlaceOrderBtn();

    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");
    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
        "confirmation message is displayed");

    softAssert.assertEquals(PaymentMethod.DIRECT_BANK_TRANSFER.getMethod(),
        orderStatusPage.getPaymentMethod());

    softAssert.assertAll();
  }

  public void TestCHECKPaymentMethod(User user) {
    homePage.clickOnMyAccountButton();

    myAccountPage.login(user);

    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();
    productDetailsPage.clickOnMyCartButton();

    shoppingCartPage.clickCheckoutBtn();

    checkoutPage.choosePaymentMethod(PaymentMethod.CHECK_PAYMENTS.getMethod());

    checkoutPage.fillBillingInfo(user);
    checkoutPage.clickOnPlaceOrderBtn();

    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");
    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
        "confirmation message is displayed");

    softAssert.assertEquals(PaymentMethod.CHECK_PAYMENTS.getMethod(),
        orderStatusPage.getPaymentMethod());

    softAssert.assertAll();
  }

  public void TestCODPaymentMethod(User user) {
    homePage.clickOnMyAccountButton();

    myAccountPage.login(user);

    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

    productsPage.selectRandomItem();

    productDetailsPage.ClickOnAddToCartBtn();

    productDetailsPage.clickOnMyCartButton();

    refresh(); //need to refresh due to page does not update product to cart at first

    shoppingCartPage.clickCheckoutBtn();

    checkoutPage.choosePaymentMethod(PaymentMethod.COD.getMethod());

    checkoutPage.fillBillingInfo(user);
    
    checkoutPage.clickOnPlaceOrderBtn();

    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");
    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
        "confirmation message is displayed");

    softAssert.assertEquals(PaymentMethod.COD.getMethod(), orderStatusPage.getPaymentMethod());

    softAssert.assertAll();
  }
}
