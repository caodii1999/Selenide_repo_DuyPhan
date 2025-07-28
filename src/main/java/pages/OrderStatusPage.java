package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;

public class OrderStatusPage extends BasePage {

  private final String orderDetailsLocator = "//section[@class = 'woocommerce-order-details']";
  private final String productNameLocator = orderDetailsLocator + "//table//tbody//tr//td//a";
  private final SelenideElement productName = $(By.xpath(productNameLocator));
  private final ElementsCollection productsNames = $$(By.xpath(productNameLocator));

  private final String billingDetailsLocator = "//section[@class = 'woocommerce-customer-details']";
  private final String billingAddressLocator = billingDetailsLocator + "//address";
  private final SelenideElement billingAddress = $(By.xpath(billingAddressLocator));
  private final String billingPhoneNumberLocator =
      billingAddressLocator + "//p[@class= 'woocommerce-customer-details--phone']";
  private final SelenideElement billingPhoneNumber = $(By.xpath(billingPhoneNumberLocator));
  private final String billingEmailLocator =
      billingAddressLocator + "//p[@class= 'woocommerce-customer-details--email']";
  private final SelenideElement billingEmail = $(By.xpath(billingEmailLocator));

  private final String orderConfirmationMsgLocator = "//div[@class = 'woocommerce-order']//p[@class = 'woocommerce-notice woocommerce-notice--success woocommerce-thankyou-order-received']";
  private final SelenideElement orderConfirmationMsg = $(By.xpath(orderConfirmationMsgLocator));

  public String getProductName() {
    return productName.getText().trim();
  }

  public List<String> getAllProductsNames() {
    return productsNames.stream()
        .map(el -> el.scrollIntoView(false).getText().trim())
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
