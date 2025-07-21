package pageObject.pages;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import com.codeborne.selenide.SelenideElement;

import helper.DriverUtils;
import helper.ElementUtils;

public class HomePage {

	private String headerBtnLocator = "//a[@class = 'item-link' and contains(text(), '%s')]";
	private String loginSignupLocator = "//span[contains(text(), 'Log in / Sign up')]";
	private String allDeparmentsLocator = "//span[contains(text(), 'All departments')]";
	private String dynamicDepartmentLocator = "//ul[@id = 'menu-all-departments-1']//li//a[contains(text(), '%s')]";
	private String cartBtnLocator = "//a[@href = 'https://demo.testarchitect.com/cart/']";

	public void clickOnHeaderBtn(String btn) {
		ElementUtils.clickOnDynamicElement(headerBtnLocator, btn);
	}

	public void clickOnLoginSignupBtn() {
		ElementUtils.clickOnElement(loginSignupLocator);
	}

	public void selectDepartment(String depart) {
		DriverUtils.disableAds();
		SelenideElement menu = $x(allDeparmentsLocator);
		executeJavaScript("arguments[0].style.display='block';", menu);
		System.out.println("found menu");
		$x(String.format(dynamicDepartmentLocator, depart)).click();
	}

	public void clickOnCart() {
		ElementUtils.clickOnElement(cartBtnLocator);
	}
}
