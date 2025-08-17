package test;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.MyAccountPage;
import pages.ProductsPage;

import java.util.List;

@Slf4j
@Test()
public class TC_04_TestVerifyUsersCanSortItemsByPrice extends TestBase {

    List<Double> sortedPrice;

    MyAccountPage myAccountPage = new MyAccountPage();
    HomePage homePage = new HomePage();
    ProductsPage productsPage = new ProductsPage();

    public void TestSortItemsByPriceLowToHigh() {

        SoftAssert softAssert = new SoftAssert();

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.navigateToShopPage();

        productsPage.switchViewToList();

        productsPage.selectLowToHighSortOption();

        sortedPrice = productsPage.getProductPrices();

        softAssert.assertTrue(productsPage.isSortedLowToHigh(sortedPrice), "sorted");

        softAssert.assertAll();
    }

    public void TestSortItemsByPriceHighToLow() {

        SoftAssert softAssert = new SoftAssert();

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.navigateToShopPage();

        productsPage.switchViewToList();

        productsPage.selectHighToLowSortOption();

        sortedPrice = productsPage.getProductPrices();

        softAssert.assertTrue(productsPage.isSortedHighToLow(sortedPrice), "sorted");

        softAssert.assertAll();
    }
}
