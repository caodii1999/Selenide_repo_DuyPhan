package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Product;
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

  public String getProductName() {
    return productName.getText().trim();
  }

  public String getPaymentMethod() {
    return paymentMethod.getText().trim();
  }

  public List<Product> getAllProductsNames() {
    return productsNames.stream()
        .map(el -> {
          String name = el.shouldBe(visible, Duration.ofSeconds(3))
              .scrollIntoView(false)
              .getText()
              .toLowerCase()
              .trim();
          return new Product(name);
        })
        .collect(Collectors.toList());
  }

  public List<String> getBillingInfo() {
    List<String> elements = new ArrayList<>();
    String[] parts = billingAddress.getText().split("\n");
    for (String part : parts) {
      elements.add(part.trim());
    }
    return elements;
  }

  public boolean isConfirmationMsgDisplayed() {
    return orderConfirmationMsg.isDisplayed();
  }
}
