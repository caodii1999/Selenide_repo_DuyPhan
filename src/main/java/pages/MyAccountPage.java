package pages;

import com.codeborne.selenide.SelenideElement;
import enums.AccountNavItems;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.Order;
import model.User;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
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

    @Step("Login with default user")
    public void login() {
        log.info("Attempting to log in with default user");
        try {
            registerEmailInputField.shouldBe(visible, Duration.ofSeconds(5))
                    .setValue(User.defaultUser().getEmail());
            registerBtn.shouldBe(visible, Duration.ofSeconds(5))
                    .shouldBe(enabled, Duration.ofSeconds(5))
                    .click();
        } catch (Exception e) {
            System.err.println("Login step encountered an error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Step("Register new user")
    public void register(String email) {
        log.info("Registering new user with email: {}", email);
        registerEmailInputField.shouldBe(visible, Duration.ofSeconds(5))
                .setValue(email);
        registerBtn.shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(enabled, Duration.ofSeconds(5))
                .click();
    }

    public void clickOnMyAccountNavigationButton(String item) {
        log.info("Clicking on My Account navigation item: {}", item);
        $(By.xpath(String.format(dynamicNavItemLocator, item)))
                .shouldBe(visible, Duration.ofSeconds(5))
                .scrollIntoView(false)
                .shouldBe(enabled, Duration.ofSeconds(5))
                .click();
    }

    @Step("Click on Orders button")
    public void clickOnOrdersBtn() {
        log.info("Clicking on 'Orders' button in My Account");
        clickOnMyAccountNavigationButton(AccountNavItems.ORDERS.getItem());
    }

    @Step("Logout from My Account")
    public void logout() {
        log.info("Logging out from My Account");
        clickOnMyAccountNavigationButton(AccountNavItems.LOGOUT.getItem());
    }

    public Order getOrderInfo(int num) {
        log.info("Retrieving order info for row number: {}", num);
        SelenideElement dynamicOrderNumber = $(By.xpath(String.format(dynamicOrderNumberLocator, num)))
                .shouldBe(visible, Duration.ofSeconds(5));
        int orderNumber = Integer.parseInt(dynamicOrderNumber.getText().replace("#", "").trim());

        String orderDate = $(By.xpath(String.format(dynamicOrderDateLocator, num)))
                .shouldBe(visible, Duration.ofSeconds(5))
                .getText()
                .toLowerCase();

        return new Order(orderNumber, orderDate);
    }

    public List<Order> getMultipleOrdersInfos(int size) {
        log.info("Retrieving multiple orders info, total count: {}", size);
        List<Order> orderList = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Order order = getOrderInfo(i);
            orderList.add(order);
        }
        return orderList;
    }
}
