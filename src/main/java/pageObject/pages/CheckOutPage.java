package pageObject.pages;

import static com.codeborne.selenide.Selenide.$;

import org.testng.annotations.DataProvider;

import helper.ElementUtils;

public class CheckOutPage {

	private String placeOrderBtnLocator = "//button[contains(text(), 'Place order')]";
	private String productNameInOrderLocator = "//td[@class = 'product-name']";

	private String productName;

	public void clickOnPlaceOrder() {
		ElementUtils.clickOnElement(placeOrderBtnLocator);
	}

	public String getProductName() {
		String fullProductName = ElementUtils.getElementText(productNameInOrderLocator);
		String quantityText = $(".product-name .product-quantity").getText();
		productName = fullProductName.replace(quantityText, "").trim();
		return productName;
	}

	public void fillCheckoutInfo(String firstName, String lastName, String company, String country, String address,
			String city, String state, String zip, String phone, String email) {

		$("#billing_first_name").setValue(firstName);
		$("#billing_last_name").setValue(lastName);
		$("#billing_company").setValue(company);
		$("#billing_country").selectOption(country);
		$("#billing_address_1").setValue(address);
		$("#billing_city").setValue(city);
		$("#billing_state").selectOption(state);
		$("#billing_postcode").setValue(zip);
		$("#billing_phone").setValue(phone);
		$("#billing_email").setValue(email);
	}

	@DataProvider(name = "checkoutData")
	public Object[][] checkoutDataProvider() {
		return new Object[][] { { "John", "Doe", "TestCorp", "United States (US)", "123 Main St", "Los Angeles",
				"California", "90001", "1234567890", "john.doe@example.com" }, };
	}
}
