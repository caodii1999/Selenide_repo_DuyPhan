package pages;

import com.codeborne.selenide.SelenideElement;
import helper.Constants;
import helper.ElementUtils;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import model.User;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class CheckoutPage extends BasePage {

    private final String productReviewLocator = "//div[@id='order_review']//table//tbody//tr";

    private final String productNameLocator = productReviewLocator + "//td[@class='product-name']";
    private final SelenideElement productNameAndQuantity = $(By.xpath(productNameLocator));
    private final String productPriceLocator = "//td[@class='product-total']//span//bdi";
    private final String productQuantityLocator = "//strong[@class='product-quantity']";
    private final SelenideElement productPrice = $(By.xpath(productPriceLocator));
    private final SelenideElement productQuantity = $(By.xpath(productQuantityLocator));

    private final String billingCountryDropdownLocator = "//span[@id='select2-billing_country-container']";
    private final String billingCountryLocator = "//input[@class = 'select2-search__field']";

    private final String billingFirstNameInputLocator = "//input[@id='billing_first_name']";
    private final String billingLastNameInputLocator = "//input[@id='billing_last_name']";
    private final String billingAddressInputLocator = "//input[@id='billing_address_1']";
    private final String billingCityInputLocator = "//input[@id='billing_city']";
    private final String billingPhoneNumberInputLocator = "//input[@id='billing_phone']";
    private final String billingEmailInputLocator = "//input[@id='billing_email']";

    private final SelenideElement billingCountryDropdown = $(By.xpath(billingCountryDropdownLocator));
    private final SelenideElement billingCountries = $(By.xpath(billingCountryLocator));

    private final SelenideElement billingFirstNameInput = $(By.xpath(billingFirstNameInputLocator));
    private final SelenideElement billingLastNameInput = $(By.xpath(billingLastNameInputLocator));
    private final SelenideElement billingAddressInput = $(By.xpath(billingAddressInputLocator));
    private final SelenideElement billingCityInput = $(By.xpath(billingCityInputLocator));
    private final SelenideElement billingPhoneNumberInput = $(By.xpath(billingPhoneNumberInputLocator));
    private final SelenideElement billingEmailInput = $(By.xpath(billingEmailInputLocator));

    private final String dynamicPaymentMethodsLocator =
            "//div[@id='order_review']//div//ul//li//label[contains(text(), '%s')]";

    private final String placeOrderBtnLocator = "//button[@id='place_order']";
    private final SelenideElement placeOrderBtn = $(By.xpath(placeOrderBtnLocator));

    private final String errorMsgLocator =
            "//div[@class='woocommerce-NoticeGroup woocommerce-NoticeGroup-checkout']" +
                    "//ul[@class='woocommerce-error']//li[@data-id='%s']";


    public Product getProductInfo() {
        log.info("Getting product details from order summary");

        String fullProductName = productNameAndQuantity
                .shouldBe(visible, Duration.ofSeconds(5))
                .getText()
                .trim();

        String quantityText = productQuantity
                .shouldBe(visible, Duration.ofSeconds(5))
                .getText()
                .replace("×", "")
                .trim();

        int quantity = Integer.parseInt(quantityText);

        String name = fullProductName
                .replace(quantityText, "")
                .replace("×", "")
                .toLowerCase()
                .trim();

        double price = Double.parseDouble(
                productPrice
                        .shouldBe(visible, Duration.ofSeconds(5))
                        .getText()
                        .replace("$", "")
                        .trim()
        );

        return new Product(name, price, quantity);
    }

    @Step("Filling billing information")
    public void fillBillingInfo(User user) {
        log.info("Filling billing form for user: {}", user.getEmail());
        ElementUtils.setValueToInputFields(billingFirstNameInput, user.getFirstName());
        ElementUtils.setValueToInputFields(billingLastNameInput, user.getLastName());
        ElementUtils.setValueToInputFields(billingAddressInput, user.getAddress());
        ElementUtils.setValueToInputFields(billingCityInput, user.getCity());
        ElementUtils.setValueToInputFields(billingPhoneNumberInput, user.getPhoneNumber());
        ElementUtils.setValueToInputFields(billingEmailInput, user.getEmail());
        ElementUtils.selectValueInDropDown(billingCountryDropdown, billingCountries, user.getCountry());
        clickOnPlaceOrderBtn();
    }

    @Step("Click on Place Order button")
    public void clickOnPlaceOrderBtn() {
        log.info("Clicking the 'Place Order' button");
        placeOrderBtn.shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(enabled, Duration.ofSeconds(5))
                .scrollIntoView(false)
                .click();
    }

    public void choosePaymentMethod(String payment) {
        log.info("Selecting payment method: {}", payment);
        SelenideElement paymentOption = $(By.xpath(String.format(dynamicPaymentMethodsLocator, payment)))
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(enabled, Duration.ofSeconds(5));
        if (!paymentOption.isSelected()) {
            paymentOption.click();
        }
    }

    public boolean isErrorMsgMatchMissingField() {
        log.info("Checking for error messages due to missing billing fields");
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
            if (field.shouldBe(visible, Duration.ofSeconds(5)).getValue().isBlank()) {
                String id = field.getAttribute("id").trim();
                String expected = expectedErrorMessages.get(id);
                log.info("Missing value for '{}', expecting error: {}", id, expected);

                SelenideElement errorElement = $(By.xpath(String.format(errorMsgLocator, id)));
                String actual = errorElement.shouldBe(visible, Duration.ofSeconds(5))
                        .scrollIntoView(false)
                        .getText()
                        .replace("\"", "")
                        .trim();
                log.info("Actual error message: {}", actual);

                return actual.equals(expected);
            }
        }
        return false;
    }
}
