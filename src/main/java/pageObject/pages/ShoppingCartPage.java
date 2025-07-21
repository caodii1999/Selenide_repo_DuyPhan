package pageObject.pages;

import helper.ElementUtils;

public class ShoppingCartPage {
	private String checkOutBtnLocator = "//a[@href = 'https://demo.testarchitect.com/checkout/']";
	private String itemDetailsLocator = "//a[@class = 'product-title']";

	private String itemName;

	public void clickOnCheckOut() {
		ElementUtils.clickOnElement(checkOutBtnLocator);
	}

	public String getItemName() {
		itemName = ElementUtils.getElementText(itemDetailsLocator);
		return itemName;
	}
}
