package test;

import helper.Constants;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.MyAccountPage;
import pages.ProductsPage;
import pages.ShoppingCartPage;

import java.util.List;

@Slf4j
@Test
public class TC_08_TestUsersCanClearCart extends TestBase {

    SoftAssert softAssert = new SoftAssert();
    MyAccountPage myAccountPage = new MyAccountPage();
    HomePage homePage = new HomePage();
    ProductsPage productsPage = new ProductsPage();
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage();

    List<Product> expectedProductsNames;
    List<Product> actualProductsNames;

    public void precondition() {

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.navigateToShopPage();

        expectedProductsNames = productsPage.addMultipleProductsToCartAndGetInfo(3);

        productsPage.clickOnMyCartButton();

        actualProductsNames = shoppingCartPage.getAllProductsInCart();
    }

    public void TestUsersCanClearCart() {

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.clickOnMyCartButton();

        softAssert.assertEquals(actualProductsNames, expectedProductsNames,
                "Verify items show in table");

        shoppingCartPage.clearShoppingCart();

        softAssert.assertTrue(shoppingCartPage.isCartEmpty(), "Cart is empty");

        softAssert.assertEquals(shoppingCartPage.getEmptyCartMsg(), Constants.EMPTY_CART_MSG);

        softAssert.assertAll();
    }

}
