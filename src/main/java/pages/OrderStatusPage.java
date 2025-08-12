package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import model.Billing;
import org.openqa.selenium.By;

public class OrderStatusPage extends BasePage {

  private final String orderDetailsLocator = "//section[@class = 'woocommerce-order-details']";
  private final String productNameLocator = orderDetailsLocator + "//table//tbody//tr//td//a";
  private final SelenideElement productName = $(By.xpath(productNameLocator));
  private final ElementsCollection productsNames = $$(By.xpath(productNameLocator));
  private final String paymentMethodLocator = orderDetailsLocator
      + "//table//tfoot//tr//th[contains(text(), 'Payment method')]//following-sibling::td";
  private final SelenideElement paymentMethod = $(By.xpath(paymentMethodLocator));
  private final String billingDetailsLocator = "//section[@class = 'woocommerce-customer-details']";
  private final String billingAddressLocator = billingDetailsLocator + "//address";
  private final SelenideElement billingAddress = $(By.xpath(billingAddressLocator));
  private final String orderConfirmationMsgLocator = "//div[@class = 'woocommerce-order']//p[@class = 'woocommerce-notice woocommerce-notice--success woocommerce-thankyou-order-received']";
  private final SelenideElement orderConfirmationMsg = $(By.xpath(orderConfirmationMsgLocator));
  private final String actualPhoneLocator = "//p[@class = 'woocommerce-customer-details--phone']";
  private final SelenideElement actualPhone = $(By.xpath(actualPhoneLocator));
  private final String actualEmailLocator = "//p[@class = 'woocommerce-customer-details--email']";
  private final SelenideElement actualEmail = $(By.xpath(actualEmailLocator));

  public String getProductName() {
    return productName.getText().trim();
  }

  public String getPaymentMethod() {
    return paymentMethod.getText().trim();
  }

  public List<String> getProductNames() {
    return productsNames.stream()
        .map(el -> el.shouldBe(visible, Duration.ofSeconds(3))
            .scrollIntoView(false)
            .getText()
            .toLowerCase()
            .trim())
        .collect(Collectors.toList());
  }

  public Billing getBillingInfo() {
    String[] parts = billingAddress.getText().split("\n");

    String fullName = parts[0].trim();
    String address = parts[1].trim();
    String city = parts[2].trim();
    String country = parts[3].trim();
    String phoneNumber = actualPhone.getText().trim();
    String email = actualEmail.getText().trim();

    return Billing.builder()
        .fullName(fullName)
        .address(address)
        .city(city)
        .country(country)
        .phoneNumber(phoneNumber)
        .email(email)
        .build();
  }

  public boolean isConfirmationMsgDisplayed() {
    return orderConfirmationMsg.isDisplayed();
  }
}
