package test;

import static com.codeborne.selenide.Selenide.refresh;

import dataprovider.UserDataProvider;
import enums.NavItems;
import enums.Pages;
import helper.DriverUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import model.Product;
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

  List<Product> expectedProductsNames;
  List<Product> actualProductsNames;
  List<Product> purchasedProductsNames;

  public void VerifyUsersCanBuyMultipleItemsSuccessfully(User user) {

    homePage.clickOnMyAccountButton();

    myAccountPage.login(user);

    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

    expectedProductsNames = productsPage.addMultipleProductsToCartAndGetNames(3);

    productsPage.clickOnMyCartButton();

    refresh(); //need to refresh due to page does not update product to cart at first

    actualProductsNames = shoppingCartPage.getAllProductsNames();

    softAssert.assertEquals(actualProductsNames, expectedProductsNames, "step 5");

    shoppingCartPage.clickCheckoutBtn();

    checkoutPage.fillBillingInfo(user);

    checkoutPage.clickOnPlaceOrderBtn();

    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");

    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed());

    purchasedProductsNames = orderStatusPage.getAllProductsNames();

    softAssert.assertEquals(purchasedProductsNames, expectedProductsNames, "step 6");

    softAssert.assertAll();
  }
}
