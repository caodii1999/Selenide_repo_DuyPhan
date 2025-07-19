package pageObject.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ShopPage extends HomePage {

	private String closePopupBtnLocator = "//button[@aria-label = 'Close']";

	public void clickOnCloseBtn() {
		$x(closePopupBtnLocator).shouldBe(visible).click();
	}
}
