package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import enums.BillingInputs;
import helper.ElementUtils;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import model.User;
import org.openqa.selenium.By;

@Slf4j
public class CheckoutPage extends BasePage {

  private final String productReviewLocator = "//div[@id= 'order_review']//table//tbody//tr";
  private final String productNameLocator = productReviewLocator + "//td[@class = 'product-name']";
  private final SelenideElement productNameAndQuantity = $(By.xpath(productNameLocator));
  private final String productPriceLocator = "//td[@class = 'product-total']//span//bdi";
  private final SelenideElement productPrice = $(By.xpath(productPriceLocator));
  private final String productQuantityLocator = "//strong[@class = 'product-quantity']";
  private final String dynamicBillingInputLocator = "//input[@id = '%s']";
  private final String billingCountryDropdownLocator = "//span[@id = 'select2-billing_country-container']";
  private final String billingCountryLocator = "//ul[@class = 'select2-results__options']//li";
  private final String placeOrderBtnLocator = "//button[@id= 'place_order']";
  private final SelenideElement productQuantity = $(By.xpath(productQuantityLocator));

  private final SelenideElement billingCountryDropdown = $(By.xpath(billingCountryDropdownLocator));
  private final ElementsCollection billingCountries = $$(By.xpath(billingCountryLocator));
//  private final SelenideElement billingEmailInput = $(By.xpath(billingEmailInputLocator));

  private final SelenideElement placeOrderBtn = $(By.xpath(placeOrderBtnLocator));

  private final String dynamicPaymentMethodsLocator = "//div[@id = 'order_review']//div//ul//li//label[contains(text(), '%s')]";

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
}
