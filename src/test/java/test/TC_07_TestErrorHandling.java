package test;

import dataprovider.UserDataProvider;
import enums.Pages;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CheckoutPage;
import pages.HomePage;
import pages.MyAccountPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import pages.ShoppingCartPage;

@Slf4j
public class TC_07_TestErrorHandling extends TestBase {

  SoftAssert softAssert = new SoftAssert();
  HomePage homePage = new HomePage();
  MyAccountPage myAccountPage = new MyAccountPage();
  ProductsPage productsPage = new ProductsPage();
  ProductDetailsPage productDetailsPage = new ProductDetailsPage();
  ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
  CheckoutPage checkoutPage = new CheckoutPage();

  @Test(dataProvider = "missingUserData", dataProviderClass = UserDataProvider.class)
  public void TesErrorHandling(User user) {
    User validUser = new User("tsnqknmq@sharklasers.com", "Test@123");

    homePage.clickOnMyAccountButton();
    myAccountPage.login(validUser);
    myAccountPage.clickOnNavItem(Pages.SHOP.getPageName());
    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();
    productDetailsPage.clickOnMyCartButton();
    shoppingCartPage.clickCheckoutBtn();

    checkoutPage.fillBillingInfo(user);
    checkoutPage.clickOnPlaceOrderBtn();
    softAssert.assertTrue(checkoutPage.isErrorMsgMatchMissingField());

    softAssert.assertAll();
  }

}
