package test;

import dataprovider.UserDataProvider;
import enums.NavItems;
import helper.Constants;
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
public class TC_08_TestUsersCanClearCart extends TestBase {

  SoftAssert softAssert = new SoftAssert();
  MyAccountPage myAccountPage = new MyAccountPage();
  HomePage homePage = new HomePage();
  ProductsPage productsPage = new ProductsPage();
  ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
  CheckoutPage checkoutPage = new CheckoutPage();
  OrderStatusPage orderStatusPage = new OrderStatusPage();

  List<Product> expectedProductsNames;
  List<Product> actualProductsNames;

  public void precondition(User user) {
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
  }

  public void TestUsersCanClearCart(User user) {

    homePage.clickOnMyAccountButton();

    myAccountPage.login(user);

    myAccountPage.clickOnMyCartButton();

    softAssert.assertEquals(actualProductsNames, expectedProductsNames,
        "Verify items show in table");

    shoppingCartPage.clearShoppingCart();

    softAssert.assertTrue(shoppingCartPage.isCartEmpty(), "Cart is empty");

    softAssert.assertEquals(shoppingCartPage.getEmptyCartMsg(), Constants.EMPTY_CART_MSG);

    softAssert.assertAll();
  }

}
