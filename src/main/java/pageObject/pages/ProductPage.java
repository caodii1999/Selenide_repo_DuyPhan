package pageObject.pages;

import helper.ElementUtils;

public class ProductPage {

	private String addToCartBtnLocator = "//button[contains(text(), 'Add to cart')]";
	private String selectedProductNameLocator = "//h1[@class = 'product_title entry-title']";

	private String productName;

	public void clickOnAddToCart() {
		ElementUtils.clickOnElement(addToCartBtnLocator);
	}

	public String getProductName() {

		productName = ElementUtils.getElementText(selectedProductNameLocator);
		return productName;
	}
}
