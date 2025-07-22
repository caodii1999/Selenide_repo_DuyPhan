package test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import helper.Constants;
import helper.DriverUtils;
import pageObject.pages.CheckOutPage;
import pageObject.pages.ElectronicComponentsPage;
import pageObject.pages.HomePage;
import pageObject.pages.MyAccountPage;
import pageObject.pages.ProductPage;
import pageObject.pages.ShopPage;
import pageObject.pages.ShoppingCartPage;

public class TestCases extends BaseTest {
	HomePage homePage;
	ShopPage shopPage;
	MyAccountPage myAccountPage;
	ElectronicComponentsPage electronicComponentsPage;
	ProductPage productPage;
	CheckOutPage checkOutPage;
	ShoppingCartPage shoppingCartPage;

	public TestCases() {
		homePage = new HomePage();
		shopPage = new ShopPage();
		myAccountPage = new MyAccountPage();
		electronicComponentsPage = new ElectronicComponentsPage();
		productPage = new ProductPage();
		checkOutPage = new CheckOutPage();
		shoppingCartPage = new ShoppingCartPage();
	}

	@Test
	public void TC00() {
		open(Constants.WEB_URL);
		DriverUtils.disableAds();
		homePage = new HomePage();
		homePage.clickOnHeaderBtn("Shop");
		DriverUtils.verifyDisplayingPage("shop");
	}

	@Test(dataProvider = "checkoutData", dataProviderClass = CheckOutPage.class)
	public void TC01_verify_users_can_buy_an_item_successfully(String firstName, String lastName, String company,
			String country, String address, String city, String state, String zip, String phone) throws Exception {
		// register new account
		homePage.clickOnLoginSignupBtn();
		myAccountPage.inputRegisEmailAddress();
//		myAccountPage.clickOnLogout();
//		GuerrillamailAPI.registerAccount();
//		open(Constants.PASSWORD_URL);
//		myAccountPage.inputNewPassword();

		// open browser
//		open(Constants.WEB_URL);
		// Login with valid credentials
//		homePage.clickOnLoginSignupBtn();
//		myAccountPage.login(GuerrillamailAPI.getEmailAddress(), GuerrillamailAPI.getPassword());
		// Navigate to All departments section
		homePage.clickOnHeaderBtn("Home");
		homePage.selectDepartment(Constants.Departments.ELECTRONIC_COMPONENT.getDepartmentName());
		// Verify the items should be displayed as a grid
		electronicComponentsPage.isElectronicPageDisplayedGrid();
		// Switch view to list
		electronicComponentsPage.switchViewToList();
		// Verify the items should be displayed as a list
		electronicComponentsPage.isElectronicPageDisplayedList();
		// Select any item randomly to purchase
		electronicComponentsPage.selectProduct();
		// Click 'Add to Cart'
		String productNameBeforeAdd = productPage.getProductName();
		productPage.clickOnAddToCart();
		// Go to the cart
		homePage.clickOnCart();
		// verify item details
		refresh();
		String productNameAfterAdd = shoppingCartPage.getItemName();
		assertEquals(productNameBeforeAdd, productNameAfterAdd);
		// Click on Checkout
		shoppingCartPage.clickOnCheckOut();
		// Verify Checkout page displays
		DriverUtils.verifyDisplayingPage("checkout");
		// verify item in order
		String productNameAtCheckout = checkOutPage.getProductName();

		Assert.assertTrue(productNameAtCheckout.equalsIgnoreCase(productNameBeforeAdd), "not match");

		// fill the billing
		checkOutPage.fillCheckoutInfo(firstName, lastName, company, country, address, city, state, zip, phone);
		// Click on PLACE ORDER
		checkOutPage.clickOnPlaceOrder();
		// Verify Order status page displays
		DriverUtils.verifyDisplayingPage("order-received");
		// Verify the Order details with billing and item information - not yet

		String[] inputData = checkOutPage.getInputedData();
		String[] billingData = checkOutPage.getBillingData();

		assertEquals(billingData, inputData);
	}
}
