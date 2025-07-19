package test;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.Test;

import helper.Constants;
import helper.DriverUtils;
import pageObject.pages.HomePage;
import pageObject.pages.ShopPage;

public class TestCases {
	HomePage homePage;
	ShopPage shopPage;

	@Test
	public void TC01() {

		open(Constants.WEB_URL);
		DriverUtils.disableAds();
		homePage = new HomePage();
		homePage.clickOnHeaderBtn("Shop");
		DriverUtils.verifyDisplayingPage("shop");
	}

}
