package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import enums.AccountNavItems;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.User;
import org.openqa.selenium.By;

public class MyAccountPage extends BasePage {

  private final String registerEmailInputLocator = "//input[@id = 'reg_email']";
  private final String registerBtnLocator = "//button[@name = 'register']";
  private final String usernameInputLocator = "//input[@name = 'username']";
  private final String passwordInputLocator = "//input[@name = 'password']";
  private final String loginBtnLocator = "//button[@name = 'login']";
  private final String logoutBtnLocator = "//a[contains(text(), 'Logout')]";
  private final String dynamicNavItemLocator = "//nav[@class = 'woocommerce-MyAccount-navigation']//ul//li//a[contains(text(), '%s')]";
  private final String dynamicOrderNumberLocator = "//table//tbody//tr[%d]//td[@data-title = 'Order']//a";
  private final String dynamicOrderDateLocator = "//table//tbody//tr[%d]//td[@data-title = 'Date']//time";

  private final SelenideElement registerEmailInputField = $(By.xpath(registerEmailInputLocator));
  private final SelenideElement usernameInput = $(By.xpath(usernameInputLocator));
  private final SelenideElement passwordInput = $(By.xpath(passwordInputLocator));
  private final SelenideElement loginBtn = $(By.xpath(loginBtnLocator));
  private final SelenideElement registerBtn = $(By.xpath(registerBtnLocator));
  private final SelenideElement logoutBtn = $(By.xpath(logoutBtnLocator));
  private final SelenideElement navItem = $(By.xpath(dynamicNavItemLocator));

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

  public void clickOnMyAccountNavigationButton(String item) {
    $(By.xpath(String.format(dynamicNavItemLocator, item))).scrollIntoView(false).click();
  }

  @Step("click on order button")
  public void ClickOnOrdersBtn() {
    clickOnMyAccountNavigationButton(AccountNavItems.ORDERS.getItem());
  }

  @Step("logout")
  public void logout() {
    clickOnMyAccountNavigationButton(AccountNavItems.LOGOUT.getItem());
  }

  public Order getOrderInfo(int nums) {
    SelenideElement dynamicOrderNumber = $(
        By.xpath(String.format(dynamicOrderNumberLocator, nums)));
    int orderNumber = Integer.parseInt(dynamicOrderNumber.getText().replace("#", "").trim());
    String orderDate = $(By.xpath(String.format(dynamicOrderDateLocator, nums))).getText()
        .toLowerCase();

    return new Order(orderNumber, orderDate);
  }

  public List<Order> getMultipleOrdersInfos(int size) {
    List<Order> orderList = new ArrayList<>();

    for (int i = 1; i <= size; i++) {
      Order order = getOrderInfo(i);
      orderList.add(order);
    }
    return orderList;
  }
}
