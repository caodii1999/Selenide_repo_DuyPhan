package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.Billing;
import model.Order;
import model.Product;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Slf4j
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
    private final String orderNumberLocator = "//div[@class = 'woocommerce-order-overview-wrapper']//ul//li[@class = 'woocommerce-order-overview__order order']//strong";
    private final SelenideElement orderNumber = $(By.xpath(orderNumberLocator));
    private final String orderDateLocator = "//div[@class = 'woocommerce-order-overview-wrapper']//ul//li[@class = 'woocommerce-order-overview__date date']//strong";
    private final SelenideElement orderDate = $(By.xpath(orderDateLocator));
    private final String productPriceLocator = "//td[@class='woocommerce-table__product-total product-total']//span//bdi";
    private final String productQuantityLocator = "//strong[@class='product-quantity']";
    private final ElementsCollection productPrice = $$(By.xpath(productPriceLocator));
    private final ElementsCollection productQuantity = $$(By.xpath(productQuantityLocator));

    public String getProductName() {
        log.info("Retrieving product name from order details");
        return productName.shouldBe(visible, Duration.ofSeconds(5))
                .scrollIntoView(false)
                .getText()
                .trim();
    }

    public String getPaymentMethod() {
        log.info("Retrieving selected payment method");
        return paymentMethod.shouldBe(visible, Duration.ofSeconds(5))
                .scrollIntoView(false)
                .getText()
                .trim();
    }

    public List<Product> getMultipleProductsInfo() {
        log.info("Retrieving multiple product details from order");

        return IntStream.range(0, productsNames.size())
                .mapToObj(i -> {
                    String name = productsNames.get(i)
                            .shouldBe(visible, Duration.ofSeconds(5))
                            .scrollIntoView(false)
                            .getText()
                            .toLowerCase()
                            .trim();

                    double price = Double.parseDouble(
                            productPrice.get(i)
                                    .shouldBe(visible, Duration.ofSeconds(5))
                                    .getText()
                                    .replace("$", "")
                                    .trim()
                    );

                    int quantity = Integer.parseInt(
                            productQuantity.get(i)
                                    .shouldBe(visible, Duration.ofSeconds(5))
                                    .getText()
                                    .replace("×", "")
                                    .trim()
                    );

                    return new Product(name, price, quantity);
                })
                .collect(Collectors.toList());
    }


    public Billing getBillingInfo() {
        log.info("Retrieving billing information from order summary");
        String[] parts = billingAddress.shouldBe(visible, Duration.ofSeconds(5))
                .getText()
                .split("\n");

        String fullName = parts[0].trim();
        String address = parts[1].trim();
        String city = parts[2].trim();
        String country = parts[3].trim();
        String phoneNumber = actualPhone.shouldBe(visible, Duration.ofSeconds(5)).getText().trim();
        String email = actualEmail.shouldBe(visible, Duration.ofSeconds(5)).getText().trim();

        return Billing.builder()
                .fullName(fullName)
                .address(address)
                .city(city)
                .country(country)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }

    public int getOrderNumber() {
        log.info("Retrieving order number");
        return Integer.parseInt(orderNumber.scrollIntoView(false).shouldBe(visible, Duration.ofSeconds(5))
                .getText()
                .trim());
    }

    public String getOrderDate() {
        log.info("Retrieving order date");
        return orderDate.scrollIntoView(false)
                .shouldBe(visible, Duration.ofSeconds(5))
                .getText()
                .trim()
                .toLowerCase();
    }

    @Step("Get order information")
    public Order getOrderInfo() {
        log.info("Retrieving order information");
        int number = getOrderNumber();
        String date = getOrderDate();
        return new Order(number, date);
    }

    public boolean isConfirmationMsgDisplayed() {
        log.info("Checking if order confirmation message is displayed");
        return orderConfirmationMsg.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
    }
}
