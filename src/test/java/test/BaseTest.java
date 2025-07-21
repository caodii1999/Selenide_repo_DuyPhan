package test;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.BeforeMethod;

import com.codeborne.selenide.WebDriverRunner;

import helper.Constants;
import helper.DriverUtils;

public class BaseTest {

	@BeforeMethod
	public void setup() {
		open(Constants.WEB_URL);
		WebDriverRunner.getWebDriver().manage().window().maximize();
		DriverUtils.disableAds();
		DriverUtils.hidePopup();
		DriverUtils.dismissCookieBanner();
	}
}
