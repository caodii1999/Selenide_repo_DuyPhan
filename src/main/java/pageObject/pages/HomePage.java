package pageObject.pages;

import static com.codeborne.selenide.Selenide.$x;

public class HomePage {

	private String headerBtnLocator = "//a[@class = 'item-link' and contains(text(), '%s')]";

	public void clickOnHeaderBtn(String btn) {
		$x(String.format(headerBtnLocator, btn)).click();
	}

}
