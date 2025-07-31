package test;

import dataprovider.UserDataProvider;
import enums.NavItems;
import enums.Pages;
import helper.DriverUtils;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CheckoutPage;
import pages.HomePage;
import pages.OrderStatusPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import pages.ShoppingCartPage;

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
//  2. Navigate to 'Shop' or 'Products' section
    homePage.clickOnNavItem(NavItems.SHOP.getItemName());

//  3. Add a product to cart
    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();

//  4. Click on Cart button
    productsPage.clickOnMyCartButton();

//  5. Proceed to complete order
    shoppingCartPage.clickCheckoutBtn();
    checkoutPage.fillBillingInfo(user);
    checkoutPage.clickOnPlaceOrderBtn();

    DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName());
    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed());
    softAssert.assertAll();
  }
}
