package pageObject.pages;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import helper.ElementUtils;

public class ElectronicComponentsPage {

	private String productLocator = "//h2[@class = 'product-title']";
	private String gridBtnLocator = "//div[@class='switch-grid ']";
	private String listBtnLocator = "//div[@class='switch-list ']";

	public void switchViewToGrid() {
		ElementUtils.clickOnElement(gridBtnLocator);
	}

	public void switchViewToList() {
		$x(listBtnLocator).shouldBe(enabled).click();
	}

	public void isElectronicPageDisplayedGrid() {
		$(".switch-grid").shouldHave(cssClass("switcher-active"));

	}

	public void isElectronicPageDisplayedList() {
		$(".switch-list").shouldHave(cssClass("switcher-active"));
	}

	public void selectProduct() {
		$x(productLocator).click();
	}

}
