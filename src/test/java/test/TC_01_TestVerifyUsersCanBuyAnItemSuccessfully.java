package test;

import static com.codeborne.selenide.Selenide.refresh;

import dataprovider.UserDataProvider;
import enums.Departments;
import enums.Pages;
import helper.DriverUtils;
import java.util.Arrays;
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
import pages.ProductDetailsPage;
import pages.ProductsPage;
import pages.ShoppingCartPage;

@Slf4j
@Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class)
public class TC_01_TestVerifyUsersCanBuyAnItemSuccessfully extends TestBase {

  SoftAssert softAssert = new SoftAssert();
  HomePage homePage = new HomePage();
  MyAccountPage myAccountPage = new MyAccountPage();
  ProductsPage productsPage = new ProductsPage();
  ProductDetailsPage productDetailsPage = new ProductDetailsPage();
  ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
  CheckoutPage checkoutPage = new CheckoutPage();
  OrderStatusPage orderStatusPage = new OrderStatusPage();

  public void verifyUsersCanBuyAnItemSuccessfully(User user) {
    Product detailsProductName;

    String fullName = user.getFullName();
    String address = user.getAddress();
    String city = user.getCity();
    String country = user.getCountry();
    String phoneNumber = user.getPhoneNumber();
    String email = user.getEmail();

    List<String> expectedBillingInfo = Arrays.asList(fullName, address, city, country, phoneNumber,
        email);

    homePage.clickOnMyAccountButton();

    myAccountPage.login(user);

    myAccountPage.selectDepartment(Departments.ELECTRONIC_COMPONENT.getType());

    productsPage.switchViewToGrid();

    softAssert.assertTrue(productsPage.isGridView(), "is grid view");

    productsPage.switchViewToList();

    softAssert.assertTrue(productsPage.isListView(), "is list view");

    productsPage.selectRandomItem();

    detailsProductName = productDetailsPage.getProductName();

    productDetailsPage.ClickOnAddToCartBtn();

    productDetailsPage.clickOnMyCartButton();

    refresh(); //need to refresh due to page does not update product to cart at first

    softAssert.assertTrue(shoppingCartPage.getProductName().equals(detailsProductName),
        "Verify item details in mini content");

    shoppingCartPage.clickCheckoutBtn();

    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.CHECKOUT.getPageName()),
        "checkout page is displayed");

    softAssert.assertTrue(checkoutPage.getProductName().equals(detailsProductName),
        "Verify item details in order");

    checkoutPage.fillBillingInfo(user);

    checkoutPage.clickOnPlaceOrderBtn();

    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");

    softAssert.assertEquals(orderStatusPage.getBillingInfo(), expectedBillingInfo);

    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
        "confirmation message is displayed");

    softAssert.assertAll();
  }
}
