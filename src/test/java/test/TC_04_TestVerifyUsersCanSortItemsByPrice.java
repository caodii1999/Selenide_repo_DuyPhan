package test;

import dataprovider.UserDataProvider;
import enums.NavItems;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.MyAccountPage;
import pages.ProductsPage;

@Slf4j
@Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class)
public class TC_04_TestVerifyUsersCanSortItemsByPrice extends TestBase {

  List<Double> sortedPrice;

  SoftAssert softAssert = new SoftAssert();
  MyAccountPage myAccountPage = new MyAccountPage();
  HomePage homePage = new HomePage();
  ProductsPage productsPage = new ProductsPage();

  public void TestSortItemsByPriceLowToHigh(User user) {
    homePage.clickOnMyAccountButton();

    myAccountPage.login(user);

    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

    productsPage.switchViewToList();

    productsPage.selectLowToHighSortOption();

    sortedPrice = productsPage.getProductPrices();

    softAssert.assertTrue(productsPage.isSortedLowToHigh(sortedPrice), "sorted");

    softAssert.assertAll();
  }

  public void TestSortItemsByPriceHighToLow(User user) {
    homePage.clickOnMyAccountButton();

    myAccountPage.login(user);

    myAccountPage.clickOnNavItem(NavItems.SHOP.getItemName());

    productsPage.switchViewToList();

    productsPage.selectHighToLowSortOption();

    sortedPrice = productsPage.getProductPrices();

    softAssert.assertTrue(productsPage.isSortedHighToLow(sortedPrice), "sorted");

    softAssert.assertAll();
  }
}
