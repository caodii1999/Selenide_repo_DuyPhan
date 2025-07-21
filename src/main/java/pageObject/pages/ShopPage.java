package pageObject.pages;

import helper.ElementUtils;

public class ShopPage extends HomePage {

	private String closePopupBtnLocator = "//button[@aria-label = 'Close']";

	public void clickOnCloseBtn() {
		ElementUtils.clickOnElement(closePopupBtnLocator);
	}
}
