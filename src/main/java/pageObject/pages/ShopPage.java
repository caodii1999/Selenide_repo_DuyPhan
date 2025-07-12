package pageObject.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import com.codeborne.selenide.WebDriverRunner;

public class ShopPage extends HomePage {

	private String closePopupBtnLocator = "//button[@aria-label = 'Close']";

	public ShopPage clickOnCloseBtn() {

		$x(closePopupBtnLocator).shouldBe(visible).click();

		return this;
	}

	public ShopPage verifyDisplayingPage(String title) {

		String currentUrl = WebDriverRunner.url();

		System.out.println(currentUrl);

		assertTrue(currentUrl.contains(title));

		return this;
	}

}
