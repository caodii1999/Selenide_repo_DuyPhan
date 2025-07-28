package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.User;
import org.openqa.selenium.By;

public class MyAccountPage extends BasePage {

  private final String registerEmailInputLocator = "//input[@id = 'reg_email']";
  private final String registerBtnLocator = "//button[@name = 'register']";
  private final String usernameInputLocator = "//input[@name = 'username']";
  private final String passwordInputLocator = "//input[@name = 'password']";
  private final String loginBtnLocator = "//button[@name = 'login']";
  private final String logoutBtnLocator = "//a[contains(text(), 'Logout')]";

  private final SelenideElement registerEmailInputField = $(By.xpath(registerEmailInputLocator));
  private final SelenideElement usernameInput = $(By.xpath(usernameInputLocator));
  private final SelenideElement passwordInput = $(By.xpath(passwordInputLocator));
  private final SelenideElement loginBtn = $(By.xpath(loginBtnLocator));
  private final SelenideElement registerBtn = $(By.xpath(registerBtnLocator));
  private final SelenideElement logoutBtn = $(By.xpath(logoutBtnLocator));

  @Step("login")
  public void login(User user) {
    usernameInput.setValue(user.getEmail());
    passwordInput.setValue(user.getPassword());
    loginBtn.click();
  }

  public void register(String email) throws Exception {
    registerEmailInputField.setValue(email);
    registerBtn.click();
  }

  public void logout() {
    logoutBtn.scrollIntoView(true).click();
  }

}
