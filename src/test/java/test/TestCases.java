package test;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.Test;

import pageObject.pages.HomePage;
import pageObject.pages.ShopPage;

public class TestCases {
	HomePage homePage;
	ShopPage shopPage;

	@Test
	public void TC01() {

		open("https://demo.testarchitect.com/");
		homePage = new HomePage();

		homePage.clickOnHeaderBtn("Shop");

		shopPage = new ShopPage();

		shopPage.clickOnCloseBtn().verifyDisplayingPage("shop");
	}

}
