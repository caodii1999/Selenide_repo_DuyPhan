package test;

import static com.codeborne.selenide.Selenide.refresh;

import enums.NavItems;
import model.User;
import org.testng.asserts.SoftAssert;
import pages.CheckoutPage;
import pages.HomePage;
import pages.MyAccountPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import pages.ShoppingCartPage;

public class TC_09_TestUpdateQuantityOfProductInCart extends TestBase {

  SoftAssert softAssert = new SoftAssert();
  HomePage homePage = new HomePage();
  MyAccountPage myAccountPage = new MyAccountPage();
  ProductsPage productsPage = new ProductsPage();
  ProductDetailsPage productDetailsPage = new ProductDetailsPage();
  ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
  CheckoutPage checkoutPage = new CheckoutPage();

  String quantity;

  public void TestUpdateQuantityOfProductInCart(User user) {

    homePage.clickOnMyAccountButton();

    myAccountPage.login(user);

    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

    productsPage.selectRandomItem();

    productDetailsPage.ClickOnAddToCartBtn();

    quantity = productDetailsPage.getProductQuantity();

    productsPage.clickOnMyCartButton();

    refresh(); // Need refresh

    softAssert.assertEquals(shoppingCartPage.getProductQuantity(), quantity,
        "verify quantity of added product");


  }

}
