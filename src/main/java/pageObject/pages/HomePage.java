package pageObject.pages;

import static com.codeborne.selenide.Selenide.*;


public class HomePage {

	private String headerBtnLocator = "//a[@class = 'item-link' and contains(text(), '%s')]";
	
	public HomePage clickOnHeaderBtn (String btn) {
		$x(String.format(headerBtnLocator, btn)).click();
		return this;
	}
}
