package test;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.MyAccountPage;
import pages.ProductsPage;

@Slf4j
@Test()
public class TC_04_TestVerifyUsersCanSortItemsByPrice extends TestBase {

  List<Double> sortedPrice;

  SoftAssert softAssert = new SoftAssert();
  MyAccountPage myAccountPage = new MyAccountPage();
  HomePage homePage = new HomePage();
  ProductsPage productsPage = new ProductsPage();

  public void TestSortItemsByPriceLowToHigh() {
    homePage.clickOnMyAccountButton();

    myAccountPage.login(User.defaultUser());

    myAccountPage.navigateToShopPage();

    productsPage.switchViewToList();

    productsPage.selectLowToHighSortOption();

    sortedPrice = productsPage.getProductPrices();

    softAssert.assertTrue(productsPage.isSortedLowToHigh(sortedPrice), "sorted");

    softAssert.assertAll();
  }

  public void TestSortItemsByPriceHighToLow() {
    homePage.clickOnMyAccountButton();

    myAccountPage.login(User.defaultUser());

    myAccountPage.navigateToShopPage();

    productsPage.switchViewToList();

    productsPage.selectHighToLowSortOption();

    sortedPrice = productsPage.getProductPrices();

    softAssert.assertTrue(productsPage.isSortedHighToLow(sortedPrice), "sorted");

    softAssert.assertAll();
  }
}
