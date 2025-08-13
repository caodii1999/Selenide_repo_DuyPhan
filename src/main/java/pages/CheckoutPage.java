package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import enums.BillingInputs;
import helper.Constants;
import helper.ElementUtils;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import model.User;
import org.openqa.selenium.By;

@Slf4j
public class CheckoutPage extends BasePage {

  // ===== Base Locators =====
  private final String productReviewLocator = "//div[@id='order_review']//table//tbody//tr";

  // ===== Product Locators =====
  private final String productNameLocator = productReviewLocator + "//td[@class='product-name']";
  private final SelenideElement productNameAndQuantity = $(By.xpath(productNameLocator));
  private final String productPriceLocator = "//td[@class='product-total']//span//bdi";
  private final String productQuantityLocator = "//strong[@class='product-quantity']";
  private final SelenideElement productPrice = $(By.xpath(productPriceLocator));
  private final SelenideElement productQuantity = $(By.xpath(productQuantityLocator));

  // ===== Billing Locators =====
  private final String dynamicBillingInputLocator = "//input[@id='%s']";
  private final String billingCountryDropdownLocator = "//span[@id='select2-billing_country-container']";
  private final String billingCountryLocator = "//ul[@class='select2-results__options']//li";

  private final String billingFirstNameInputLocator = "//input[@id='billing_first_name']";
  private final String billingLastNameInputLocator = "//input[@id='billing_last_name']";
  private final String billingAddressInputLocator = "//input[@id='billing_address_1']";
  private final String billingCityInputLocator = "//input[@id='billing_city']";
  private final String billingPhoneNumberInputLocator = "//input[@id='billing_phone']";
  private final String billingEmailInputLocator = "//input[@id='billing_email']";

  private final SelenideElement billingCountryDropdown = $(By.xpath(billingCountryDropdownLocator));
  private final ElementsCollection billingCountries = $$(By.xpath(billingCountryLocator));

  private final SelenideElement billingFirstNameInput = $(By.xpath(billingFirstNameInputLocator));
  private final SelenideElement billingLastNameInput = $(By.xpath(billingLastNameInputLocator));
  private final SelenideElement billingAddressInput = $(By.xpath(billingAddressInputLocator));
  private final SelenideElement billingCityInput = $(By.xpath(billingCityInputLocator));
  private final SelenideElement billingPhoneNumberInput = $(
      By.xpath(billingPhoneNumberInputLocator));
  private final SelenideElement billingEmailInput = $(By.xpath(billingEmailInputLocator));

  // ===== Payment Locators =====
  private final String dynamicPaymentMethodsLocator =
      "//div[@id='order_review']//div//ul//li//label[contains(text(), '%s')]";

  // ===== Buttons =====
  private final String placeOrderBtnLocator = "//button[@id='place_order']";
  private final SelenideElement placeOrderBtn = $(By.xpath(placeOrderBtnLocator));

  // ===== Error Messages =====
  private final String errorMsgLocator =
      "//div[@class='woocommerce-NoticeGroup woocommerce-NoticeGroup-checkout']" +
          "//ul[@class='woocommerce-error']//li[@data-id='%s']";


  public Product getProductInfo() {
    String fullProductName = productNameAndQuantity.getText();
    String quantity = productQuantity.getText().replace("×", "").trim();
    String name = fullProductName.replace(quantity, "").replace("×", "").toLowerCase().trim();
    String price = productPrice.getText().replace("$", "").trim();

    return new Product(name, price, quantity);

  }

  @Step("filling bills")
  public void fillBillingInfo(User user) {
    ElementUtils.setValueToInputFields(dynamicBillingInputLocator,
        BillingInputs.FIRST_NAME.getInputs(), user.getFirstName());
    ElementUtils.setValueToInputFields(dynamicBillingInputLocator,
        BillingInputs.LAST_NAME.getInputs(), user.getLastName());
    ElementUtils.selectValueInDropDown(billingCountryDropdown, billingCountries, user.getCountry());
    ElementUtils.setValueToInputFields(dynamicBillingInputLocator,
        BillingInputs.ADDRESS.getInputs(), user.getAddress());
    ElementUtils.setValueToInputFields(dynamicBillingInputLocator, BillingInputs.CITY.getInputs(),
        user.getCity());
    ElementUtils.setValueToInputFields(dynamicBillingInputLocator, BillingInputs.PHONE.getInputs(),
        user.getPhoneNumber());
    ElementUtils.setValueToInputFields(dynamicBillingInputLocator, BillingInputs.EMAIL.getInputs(),
        user.getEmail());
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

  public boolean isErrorMsgMatchMissingField() {
    Map<String, String> expectedErrorMessages = Map.of(
        "billing_first_name", Constants.BILLING_FIRST_NAME_ERROR,
        "billing_last_name", Constants.BILLING_LAST_NAME_ERROR,
        "billing_address_1", Constants.BILLING_ADDRESS_ERROR,
        "billing_city", Constants.BILLING_CITY_ERROR,
        "billing_phone", Constants.BILLING_PHONE_ERROR,
        "billing_email", Constants.BILLING_EMAIL_ERROR
    );

    List<SelenideElement> fields = List.of(
        billingFirstNameInput,
        billingLastNameInput,
        billingAddressInput,
        billingCityInput,
        billingPhoneNumberInput,
        billingEmailInput
    );

    for (SelenideElement field : fields) {
      if (field.getValue().isBlank()) {
        String id = field.getAttribute("id").trim();
        String expected = expectedErrorMessages.get(id);
        System.out.println(expected);
        String actual = $(By.xpath(String.format(errorMsgLocator, id)))
            .getText().replace("\"", "").trim();
        System.out.println(actual);
        return actual.equals(expected);
      }
    }
    return false;
  }
}
