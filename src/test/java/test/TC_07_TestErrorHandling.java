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
@Test(dataProvider = "missingUserData", dataProviderClass = UserDataProvider.class)
public class TC_07_TestErrorHandling extends TestBase {

  SoftAssert softAssert = new SoftAssert();
  HomePage homePage = new HomePage();
  MyAccountPage myAccountPage = new MyAccountPage();
  ProductsPage productsPage = new ProductsPage();
  ProductDetailsPage productDetailsPage = new ProductDetailsPage();
  ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
  CheckoutPage checkoutPage = new CheckoutPage();

  public void TestErrorHandling(User missingInfoUser) {
    User validUser = User.builder().build();

    homePage.clickOnMyAccountButton();
    myAccountPage.login(validUser);
    myAccountPage.clickOnNavItem(Pages.SHOP.getPageName());
    productsPage.selectRandomItem();
    productDetailsPage.ClickOnAddToCartBtn();
    productDetailsPage.clickOnMyCartButton();
    shoppingCartPage.clickCheckoutBtn();

    checkoutPage.fillBillingInfo(missingInfoUser);
    checkoutPage.clickOnPlaceOrderBtn();
    softAssert.assertTrue(checkoutPage.isErrorMsgMatchMissingField());

    softAssert.assertAll();
  }

}
