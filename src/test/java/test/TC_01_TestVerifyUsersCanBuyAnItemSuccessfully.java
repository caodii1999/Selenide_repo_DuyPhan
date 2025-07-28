package test;

import dataprovider.UserDataProvider;
import enums.Departments;
import enums.Pages;
import helper.DriverUtils;
import java.util.Arrays;
import java.util.List;
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
    String detailsProductName;
    String fullName = user.getFullName();
    String address = user.getAddress();
    String city = user.getCity();
    String country = user.getCountry();
    String phoneNumber = user.getPhoneNumber();
    String email = user.getEmail();

    List<String> expectedBillingInfo = Arrays.asList(fullName, address, city, country, phoneNumber,
        email);

//  2. Login with valid credentials
    homePage.clickOnMyAccountButton();

    myAccountPage.login(user);

//  3. Navigate to All departments section
//  4. Select Electronic Components & Supplies
    myAccountPage.selectDepartment(Departments.ELECTRONIC_COMPONENT.getType());

//  5. Verify the items should be displayed as a grid
    productsPage.switchViewToGrid();

    softAssert.assertTrue(productsPage.isGridView(), "is grid view");

//  6. Switch view to list
    productsPage.switchViewToList();

//  7. Verify the items should be displayed as a list
    softAssert.assertTrue(productsPage.isListView(), "is list view");

//  8. Select any item randomly to purchase
    productsPage.selectRandomItem();

    detailsProductName = productDetailsPage.getProductName();

//  9. Click 'Add to Cart'
    productDetailsPage.ClickOnAddToCartBtn();

//  10. Go to the cart
    productDetailsPage.clickOnMyCartButton();

//  11. Verify item details in mini content
    softAssert.assertTrue(shoppingCartPage.getProductName().equalsIgnoreCase(detailsProductName));

//  12. Click on Checkout
    shoppingCartPage.clickCheckoutBtn();

//  13. Verify Checkout page displays
    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.CHECKOUT.getPageName()),
        "checkout page is displayed");

//  14. Verify item details in order
    softAssert.assertTrue(checkoutPage.getProductName().equalsIgnoreCase(detailsProductName));

//  15. Fill the billing details with default payment method
    checkoutPage.fillBillingInfo(user);

//  16. Click on PLACE ORDER
    checkoutPage.clickOnPlaceOrderBtn();

//  17. Verify Order status page displays
    softAssert.assertTrue(DriverUtils.isPageDisplayed(Pages.ORDER_STATUS.getPageName()),
        "order status page is displayed");

//  18. Verify the Order details with billing and item information
    softAssert.assertEquals(orderStatusPage.getBillingInfo(), expectedBillingInfo);

//  Expected Result: Order confirmation message show correctly
    softAssert.assertTrue(orderStatusPage.isConfirmationMsgDisplayed(),
        "confirmation message is displayed");

    softAssert.assertAll();
  }

}
