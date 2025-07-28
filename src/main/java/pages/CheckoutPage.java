package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.openqa.selenium.By;

@Slf4j
public class CheckoutPage extends BasePage {

  private final String productReviewLocator = "//div[@id= 'order_review']//table//tbody//tr";
  private final String productNameLocator = productReviewLocator + "//td[@class = 'product-name']";
  private final SelenideElement productNameAndQuantity = $(By.xpath(productNameLocator));
  private final String productQuantityLocator = "//strong[@class = 'product-quantity']";
  private final String billingFirstNameInputLocator = "//input[@id = 'billing_first_name']";
  private final String billingLastNameInputLocator = "//input[@id = 'billing_last_name']";
  private final String billingCountryDropdownLocator = "//span[@id = 'select2-billing_country-container']";
  private final String billingCountryLocator = "//ul[@class = 'select2-results__options']//li";
  private final String billingAddressInputLocator = "//input[@id = 'billing_address_1']";
  private final String billingCityInputLocator = "//input[@id = 'billing_city']";
  private final String billingPhoneNumberInputLocator = "//input[@id = 'billing_phone']";
  private final String billingEmailInputLocator = "//input[@id = 'billing_email']";
  private final String placeOrderBtnLocator = "//button[@id= 'place_order']";
  private final SelenideElement productQuantityText = $(By.xpath(productQuantityLocator));

  private final SelenideElement billingFirstNameInput = $(By.xpath(billingFirstNameInputLocator));
  private final SelenideElement billingLastNameInput = $(By.xpath(billingLastNameInputLocator));
  private final SelenideElement billingCountryDropdown = $(By.xpath(billingCountryDropdownLocator));
  private final ElementsCollection billingCountries = $$(By.xpath(billingCountryLocator));
  private final SelenideElement billingAddressInput = $(By.xpath(billingAddressInputLocator));
  private final SelenideElement billingCityInput = $(By.xpath(billingCityInputLocator));
  private final SelenideElement billingPhoneNumberInput = $(
      By.xpath(billingPhoneNumberInputLocator));
  private final SelenideElement billingEmailInput = $(By.xpath(billingEmailInputLocator));

  private final SelenideElement placeOrderBtn = $(By.xpath(placeOrderBtnLocator));

  private final String dynamicPaymentMethodsLocator = "//div[@id = 'order_review']//div//ul//li//label[contains(text(), '%s')]";

  public String getProductName() {
    String fullProductName = productNameAndQuantity.getText();
    String quantityText = productQuantityText.getText();
    return fullProductName.replace(quantityText, "").trim();
  }

  @Step("filling bills")
  public void fillBillingInfo(User user) {

    billingFirstNameInput.clear();
    billingFirstNameInput.setValue(user.getFirstName());

    billingLastNameInput.clear();
    billingLastNameInput.setValue(user.getLastName());

    billingCountryDropdown.click();
    billingCountries.findBy(text(user.getCountry())).click();

    billingAddressInput.clear();
    billingAddressInput.setValue(user.getAddress());

    billingCityInput.clear();
    billingCityInput.setValue(user.getCity());

    billingPhoneNumberInput.clear();
    billingPhoneNumberInput.setValue(user.getPhoneNumber());

  }

  @Step("click on Place Order button")
  public void clickOnPlaceOrderBtn() {
    placeOrderBtn.click();
  }

  public void choosePaymentMethod(String payment) {
    SelenideElement paymentOption = $(
        By.xpath(String.format(dynamicPaymentMethodsLocator, payment)));
    if (!paymentOption.isSelected()) {
      paymentOption.click();
    }
  }
}
