package pageObject.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.closeWindow;

import helper.ElementUtils;
import helper.GuerrillamailAPI;

public class MyAccountPage {

	private String regisEmailInputLocator = "//input[@id = 'reg_email']";
	private String regisBtnLocator = "//button[@name = 'register']";
	private String usernameInputLocator = "//input[@name = 'username']";
	private String passwordInputLocator = "//input[@name = 'password']";
	private String loginBtnLocator = "//button[@name = 'login']";
	private String logoutBtnLocator = "//a[contains(text(), 'Logout')]";
	private String dynamicPasswordInputFieldLocator = "//input[@name = 'password_%s']";
	private String saveBtnLocator = "//button[@type = 'submit']";

	public MyAccountPage inputRegisEmailAddress() throws Exception {
		ElementUtils.inputToElement(regisEmailInputLocator, GuerrillamailAPI.getEmailAddress());
		ElementUtils.clickOnElement(regisBtnLocator);
		return this;
	}

//	public void registerNewAccount() throws Exception {
//		MailAPI.registerNewAccount();
//	}

	public void login(String username, String password) {
		ElementUtils.inputToElement(usernameInputLocator, username);
		ElementUtils.inputToElement(passwordInputLocator, password);
		ElementUtils.clickOnElement(loginBtnLocator);
	}

	public void clickOnLogout() {
		$x(logoutBtnLocator).scrollIntoView(true).shouldBe(visible).click();
		closeWindow();
	}

	public void inputNewPassword() {
		for (int i = 0; i < 2; i++) {
			ElementUtils.inputToElement(String.format(dynamicPasswordInputFieldLocator, i),
					GuerrillamailAPI.getPassword());
		}
		ElementUtils.clickOnElement(saveBtnLocator);
		System.out.println("done");
		closeWebDriver();
	}

//	public static void registerNewAccount() {
//		open(Constants.PASSWORD_URL);
//		inputNewPassword();
//	}
}
