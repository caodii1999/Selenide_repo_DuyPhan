package test;

import dataprovider.UserDataProvider;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.MyAccountPage;
import pages.ProductsPage;

@Slf4j
@Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class)
public class TC_05_TestVerifyOrdersAppearInOrderHistory extends TestBase {

  SoftAssert softAssert = new SoftAssert();
  MyAccountPage myAccountPage = new MyAccountPage();
  HomePage homePage = new HomePage();
  ProductsPage productsPage = new ProductsPage();

  public void TestVerifyOrdersAppearInOrderHistory(User user) {
//  Precondition: User has placed 02 orders

//  1. Go to My Account page
    homePage.clickOnMyAccountButton();


  }

}
