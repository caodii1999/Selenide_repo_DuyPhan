package test;

import model.Product;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import static com.codeborne.selenide.Selenide.refresh;

@Test
public class TC_09_TestUpdateQuantityOfProductInCart extends TestBase {

    SoftAssert softAssert = new SoftAssert();
    HomePage homePage = new HomePage();
    MyAccountPage myAccountPage = new MyAccountPage();
    ProductsPage productsPage = new ProductsPage();
    ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage();

    int expectedProductQuantity;
    int actualProductQuantity;

    Product expectedProductAfterPlus;
    Product actualProductAfterPlus;
    Product expectProductAfterAdding4;
    Product actualProductAfterAdding4;
    Product expectedProductAfterMinus;
    Product actualProductAfterMinus;

    public void TestUpdateQuantityOfProductInCart() {

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.clickOnAddToCartBtn();

        expectedProductQuantity = productDetailsPage.getProductQuantity();

        productsPage.clickOnMyCartButton();

        refresh(); // Need refresh

        actualProductQuantity = shoppingCartPage.getProductQuantity();

        softAssert.assertEquals(actualProductQuantity, expectedProductQuantity,
                "verify quantity of added product");

        expectedProductAfterPlus = shoppingCartPage.getProductQtyAndSubAfterPlus(1);

        actualProductAfterPlus = new Product(shoppingCartPage.getProductQuantity(), shoppingCartPage.getSubTotal());

        softAssert.assertEquals(actualProductAfterPlus, expectedProductAfterPlus);

        expectProductAfterAdding4 = shoppingCartPage.getProductQtyAndSubAfterPlus(4);

        actualProductAfterAdding4 = new Product(shoppingCartPage.getProductQuantity(), shoppingCartPage.getSubTotal());

        softAssert.assertEquals(expectProductAfterAdding4, actualProductAfterAdding4);

        expectedProductAfterMinus = shoppingCartPage.getProductQtyAndSubAfterMinus(1);

        actualProductAfterMinus = new Product(shoppingCartPage.getProductQuantity(), shoppingCartPage.getSubTotal());

        softAssert.assertEquals(expectedProductAfterMinus, actualProductAfterMinus);

        softAssert.assertAll();
    }
}
