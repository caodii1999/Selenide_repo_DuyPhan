package pageObject.pages;

import static com.codeborne.selenide.Selenide.$;

import org.testng.annotations.DataProvider;

import helper.ElementUtils;

public class CheckOutPage {

	private String placeOrderBtnLocator = "//button[contains(text(), 'Place order')]";
	private String productNameInOrderLocator = "//td[@class = 'product-name']";
	private String billingNameLocator = "//h2[text() = 'Billing address']//following-sibling::address";
	private String billingCompanyLocator = "//h2[text() = 'Billing address']//following-sibling::address//br[1]";
	private String billingAddress = "//h2[text() = 'Billing address']//following-sibling::address//br[2]";
	private String billingCityAndZip = "//h2[text() = 'Billing address']//following-sibling::address//br[3]";
	private String billingPhone = "//h2[text() = 'Billing address']//following-sibling::address//p[1]";

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
			String city, String state, String zip, String phone) {

		$("#billing_first_name").setValue(firstName);
		$("#billing_last_name").setValue(lastName);
		$("#billing_company").setValue(company);
		$("#billing_country").selectOption(country);
		$("#billing_address_1").setValue(address);
		$("#billing_city").setValue(city);
		$("#billing_state").selectOption(state);
		$("#billing_postcode").setValue(zip);
		$("#billing_phone").setValue(phone);
	}

	@DataProvider(name = "checkoutData")
	public Object[][] checkoutDataProvider() {
		return new Object[][] { { "Duy", "Phan", "AGEST", "United States (US)", "253 Hoang Van Thu", "Tan Binh",
				"Ho Chi Minh", "70000", "1234567890", }, };
	}

	public String[] getInputedData() {
		String[] inputedData = new String[5];
		Object[][] data = checkoutDataProvider();

		inputedData[0] = " " + data[0][0].toString() + " " + data[0][1].toString();
		inputedData[1] = data[0][2].toString();
		inputedData[2] = data[0][3].toString();
		inputedData[3] = data[0][5].toString() + "CT " + data[0][7].toString();
		inputedData[4] = data[0][8].toString();

		System.out.println(inputedData);
		return inputedData;
	}

	public String[] getBillingData() {
		String[] billingData = new String[5];

		billingData[0] = ElementUtils.getElementText(billingNameLocator);
		billingData[1] = ElementUtils.getElementText(billingCompanyLocator);
		billingData[2] = ElementUtils.getElementText(billingAddress);
		billingData[3] = ElementUtils.getElementText(billingCityAndZip);
		billingData[4] = ElementUtils.getElementText(billingPhone);

		return billingData;
	}

}
