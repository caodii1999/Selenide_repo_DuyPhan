package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class ShoppingCartPage extends BasePage {

    private final String productNameLocator = "//a[@class = 'product-title']";
    private final String proceedToCheckoutBtnLocator = "//div[@class = 'cart_totals ']//a[@href = 'https://demo.testarchitect.com/checkout/']";
    private final String productsNamesLocator = "//table[@class = 'shop_table shop_table_responsive cart woocommerce-cart-form__contents']//tbody//tr//td//div//a[@class = 'product-title']";
    private final ElementsCollection productsNames = $$(By.xpath(productsNamesLocator));
    private final String productPriceLocator = "//td[@class = 'product-price']//span//bdi";
    private final String productQuantityLocator = "//td[@class = 'product-quantity']//div//input[@class = 'input-text qty text']";
    private final SelenideElement productQuantity = $(By.xpath(productQuantityLocator));
    private final ElementsCollection productQuantities = $$(By.xpath(productQuantityLocator));
    private final SelenideElement productPrice = $(By.xpath(productPriceLocator));
    private final ElementsCollection productPrices = $$(By.xpath(productPriceLocator));
    private final SelenideElement productName = $(By.xpath(productNameLocator));
    private final SelenideElement proceedToCheckoutBtn = $(By.xpath(proceedToCheckoutBtnLocator));
    private final String clearCartLocator = "//a[@class = 'clear-cart']";
    private final SelenideElement clearCart = $(By.xpath(clearCartLocator));
    private final String emptyCartMsgLocator = "//div[@class = 'cart-empty empty-cart-block']//h1";
    private final SelenideElement emptyCartMsg = $(By.xpath(emptyCartMsgLocator));
    private final String cartContentLocator = "//div[@class = 'container content-page sidebar-mobile-bottom']";
    private final SelenideElement cartContent = $(By.xpath(cartContentLocator));
    private final String loadingSpinnerLocator = "//div[@class = 'blockUI blockOverlay']";
    private final SelenideElement loadingSpinner = $(By.xpath(loadingSpinnerLocator));
    private final String plusBtnLocator = "//span[@class = 'plus']";
    private final SelenideElement plusBtn = $(By.xpath(plusBtnLocator));
    private final String minusBtnLocator = "//span[@class = 'minus']";
    private final SelenideElement minusBtn = $(By.xpath(minusBtnLocator));
    private final String quantityInputLocator = "//input[@class = 'input-text qty text']";
    private final SelenideElement quantityInput = $(By.xpath(quantityInputLocator));
    private final String productSubTotalLocator = "//td[@class = 'product-subtotal']//span//bdi";
    private final SelenideElement productSubTotal = $(By.xpath(productSubTotalLocator));
    private final String updateCartBtnLocator = "//button[@class = 'btn gray medium bordered']";
    private final SelenideElement updateCartBtn = $(By.xpath(updateCartBtnLocator));

    @Step("Get product info")
    public Product getSingleProductInfo() {
        String name = getProductName();
        double price = getProductPrice();
        int quantity = getProductQuantity();
        return new Product(name, price, quantity);
    }

    @Step("Get product name")
    public String getProductName() {
        return productName
                .shouldBe(visible, Duration.ofSeconds(5))
                .scrollIntoView(false)
                .getText()
                .toLowerCase()
                .trim();
    }

    @Step("Get product unit price")
    public double getProductPrice() {
        loadingSpinner.shouldBe(disappear);
        return Double.parseDouble(
                productPrice
                        .shouldBe(visible, Duration.ofSeconds(5))
                        .scrollIntoView(false)
                        .getText()
                        .replace('\u00A0', ' ')
                        .replace("$", "")
                        .replace(",", "")
                        .replaceAll("[^0-9.\\-]", "")
                        .trim()
        );
    }

    @Step("Get product quantity")
    public int getProductQuantity() {
        loadingSpinner.shouldBe(disappear);
        return Integer.parseInt(
                productQuantity
                        .shouldBe(visible, Duration.ofSeconds(5))
                        .scrollIntoView(false)
                        .getValue()
                        .replaceAll("[^0-9]", "")
                        .trim()
        );
    }

    public double getSubTotal() {
        loadingSpinner.shouldBe(disappear);
        return Double.parseDouble(
                productSubTotal
                        .shouldBe(visible, Duration.ofSeconds(5))
                        .scrollIntoView(false)
                        .getText()
                        .replace('\u00A0', ' ')
                        .replace("$", "")
                        .replace(",", "")
                        .replaceAll("[^0-9.\\-]", "")
                        .trim()
        );
    }

    @Step("Get expected qty & subtotal after clicking '+' {numberOfClick} time(s)")
    public Product getProductQtyAndSubAfterPlus(int numberOfClick) {
        double price = getProductPrice();
        int beforeQty = getProductQuantity();
        int afterQty;
        double afterTotal;

        if (numberOfClick == 1) {
            clickOnPlusBtn();
            afterQty = beforeQty + numberOfClick;
        } else {
            setValueToQtyTextBox(numberOfClick);
            afterQty = numberOfClick;
        }

        afterTotal = price * afterQty;
        loadingSpinner.shouldBe(disappear);
        productQuantity.shouldHave(Condition.value(String.valueOf(afterQty)), Duration.ofSeconds(5));

        return new Product(afterQty, afterTotal);
    }

    @Step("Get expected qty & subtotal after clicking '-' {numberOfClick} time(s)")
    public Product getProductQtyAndSubAfterMinus(int numberOfClick) {
        double price = getProductPrice();
        int beforeQty = getProductQuantity();
        int afterQty;
        double afterTotal;

        if (numberOfClick == 1) {
            clickOnMinusBtn();
            afterQty = Math.max(1, beforeQty - numberOfClick);
        } else {
            setValueToQtyTextBox(numberOfClick);
            afterQty = numberOfClick;
        }

        afterTotal = price * afterQty;
        loadingSpinner.shouldBe(disappear);
        productQuantity.shouldHave(Condition.value(String.valueOf(afterQty)), Duration.ofSeconds(5));

        return new Product(afterQty, afterTotal);
    }

    @Step("Get all products (name, price, quantity) in cart")
    public List<Product> getAllProductsInCart() {
        log.info("Retrieving all products in cart");

        return IntStream.range(0, productsNames.size())
                .mapToObj(i -> {
                    String name = productsNames.get(i)
                            .shouldBe(visible, Duration.ofSeconds(5))
                            .scrollIntoView(false)
                            .getText()
                            .toLowerCase()
                            .trim();

                    double price = Double.parseDouble(
                            productPrices.get(i)
                                    .shouldBe(visible, Duration.ofSeconds(5))
                                    .scrollIntoView(false)
                                    .getText()
                                    .replace("$", "")
                                    .trim()
                    );

                    int quantity = Integer.parseInt(
                            productQuantities.get(i)
                                    .shouldBe(visible, Duration.ofSeconds(5))
                                    .scrollIntoView(false)
                                    .getValue()
                                    .trim()
                    );

                    log.info("Product found - Name: {}, Price: {}, Quantity: {}", name, price, quantity);
                    return new Product(name, price, quantity);
                })
                .collect(Collectors.toList());
    }

    @Step("click on Clear cart")
    public void clearShoppingCart() {
        clearCart.scrollIntoView(false).click();
        switchTo().alert().accept();
    }

    public String getEmptyCartMsg() {
        return emptyCartMsg.shouldBe(visible).getText();
    }

    public boolean isCartEmpty() {
        return cartContent.isDisplayed();
    }

    @Step("click on checkout button")
    public void clickCheckoutBtn() {
        log.info("Clicking on 'Proceed to Checkout' button");
        if (isCartNavBarDisplayed()) {
            proceedToCheckoutBtn.shouldBe(visible, Duration.ofSeconds(2))
                    .scrollIntoCenter()
                    .click();
        }
    }

    @Step("Click on plus button")
    public void clickOnPlusBtn() {
        plusBtn.shouldBe(enabled).click();
        loadingSpinner.shouldBe(appear);
    }

    @Step("Click on minus button")
    public void clickOnMinusBtn() {
        minusBtn.shouldBe(enabled).click();
        loadingSpinner.should(appear);
    }

    @Step("Set value to Qty textbox")
    public void setValueToQtyTextBox(int num) {
        loadingSpinner.shouldBe(disappear);
        quantityInput.shouldBe(enabled).setValue(String.valueOf(num));
        updateCartBtn.shouldBe(enabled).click();
        loadingSpinner.shouldBe(appear);
    }
}
