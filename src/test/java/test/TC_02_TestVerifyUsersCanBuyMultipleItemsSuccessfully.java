package test;

import dataprovider.UserDataProvider;
import enums.NavItems;
import enums.Pages;
import helper.DriverUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CheckoutPage;
import pages.HomePage;
import pages.MyAccountPage;
import pages.OrderStatusPage;
import pages.ProductsPage;
import pages.ShoppingCartPage;

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

  List<String> expectedProductsNames;
  List<String> actualProductsNames;
  List<String> purchasedProductsNames;

  public void VerifyUsersCanBuyMultipleItemsSuccessfully(User user) {

    homePage.clickOnMyAccountButton();

//  2. Login with valid credentials
    myAccountPage.login(user);

//  3. Go to Shop page
    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

//  4. Select multiple items and add to cart
    expectedProductsNames = productsPage.addMultipleProductsToCartAndGetNames(3);

//  5. Go to the cart and verify all selected items
    productsPage.clickOnMyCartButton();

    actualProductsNames = shoppingCartPage.getAllProductsNames();

    softAssert.assertEquals(actualProductsNames, expectedProductsNames, "step 5");

//  6. Proceed to checkout and confirm order
    shoppingCartPage.clickCheckoutBtn();

    checkoutPage.fillBillingInfo(user);

    checkoutPage.clickOnPlaceOrderBtn();

//  7. Verify order confirmation message

    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");

    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed());

//  Expected result: All selected items are purchased, and order confirmation is received

    purchasedProductsNames = orderStatusPage.getAllProductsNames();

    softAssert.assertEquals(purchasedProductsNames, expectedProductsNames, "step 6");

    softAssert.assertAll();
  }
}
