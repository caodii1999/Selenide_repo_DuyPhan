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
        log.info("Cart product -> name='{}', price={}, qty={}", name, price, quantity);
        return new Product(name, price, quantity);
    }

    @Step("Get product name")
    public String getProductName() {
        String name = productName
                .shouldBe(visible, Duration.ofSeconds(5))
                .scrollIntoView(false)
                .getText()
                .toLowerCase()
                .trim();
        log.info("Product name: {}", name);
        return name;
    }

    @Step("Get product unit price")
    public double getProductPrice() {
        loadingSpinner.shouldBe(disappear);
        double price = Double.parseDouble(
                productPrice.shouldBe(visible, Duration.ofSeconds(5))
                        .scrollIntoView(false)
                        .getText()
                        .replace('\u00A0', ' ')
                        .replace("$", "")
                        .replace(",", "")
                        .replaceAll("[^0-9.\\-]", "")
                        .trim()
        );
        log.info("Unit price: {}", price);
        return price;
    }

    @Step("Get product quantity")
    public int getProductQuantity() {
        loadingSpinner.shouldBe(disappear);
        int qty = Integer.parseInt(
                productQuantity.shouldBe(visible, Duration.ofSeconds(5))
                        .scrollIntoView(false)
                        .getValue()
                        .replaceAll("[^0-9]", "")
                        .trim()
        );
        log.info("Quantity: {}", qty);
        return qty;
    }

    @Step("Get product subtotal")
    public double getSubTotal() {
        loadingSpinner.shouldBe(disappear);
        double sub = Double.parseDouble(
                productSubTotal.shouldBe(visible, Duration.ofSeconds(5))
                        .scrollIntoView(false)
                        .getText()
                        .replace('\u00A0', ' ')
                        .replace("$", "")
                        .replace(",", "")
                        .replaceAll("[^0-9.\\-]", "")
                        .trim()
        );
        log.info("Subtotal: {}", sub);
        return sub;
    }

    @Step("Get expected qty & subtotal after clicking '+' {numberOfClick} time(s)")
    public Product getProductQtyAndSubAfterPlus(int numberOfClick) {
        double price = getProductPrice();
        int beforeQty = getProductQuantity();
        log.info("PLUS start: clicks={}, beforeQty={}, price={}", numberOfClick, beforeQty, price);

        int afterQty;
        if (numberOfClick == 1) {
            clickOnPlusBtn();
            afterQty = beforeQty + 1;
        } else {
            setValueToQtyTextBox(numberOfClick);
            afterQty = numberOfClick;
        }

        loadingSpinner.shouldBe(disappear);
        productQuantity.shouldHave(Condition.value(String.valueOf(afterQty)), Duration.ofSeconds(5));

        double afterTotal = price * afterQty;
        log.info("PLUS done: afterQty={}, expectedSubtotal={}", afterQty, afterTotal);
        return new Product(afterQty, afterTotal);
    }

    @Step("Get expected qty & subtotal after clicking '-' {numberOfClick} time(s)")
    public Product getProductQtyAndSubAfterMinus(int numberOfClick) {
        double price = getProductPrice();
        int beforeQty = getProductQuantity();
        log.info("MINUS start: clicks={}, beforeQty={}, price={}", numberOfClick, beforeQty, price);

        int afterQty;
        if (numberOfClick == 1) {
            clickOnMinusBtn();
            afterQty = Math.max(1, beforeQty - 1);
        } else {
            setValueToQtyTextBox(numberOfClick);
            afterQty = numberOfClick; // assumes absolute set; change if you intend decrement
        }

        loadingSpinner.shouldBe(disappear);
        productQuantity.shouldHave(Condition.value(String.valueOf(afterQty)), Duration.ofSeconds(5));

        double afterTotal = price * afterQty;
        log.info("MINUS done: afterQty={}, expectedSubtotal={}", afterQty, afterTotal);
        return new Product(afterQty, afterTotal);
    }

    @Step("Get all products (name, price, quantity) in cart")
    public List<Product> getAllProductsInCart() {
        log.info("Reading all products in cart…");
        List<Product> items = IntStream.range(0, productsNames.size())
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

                    int qty = Integer.parseInt(
                            productQuantities.get(i)
                                    .shouldBe(visible, Duration.ofSeconds(5))
                                    .scrollIntoView(false)
                                    .getValue()
                                    .trim()
                    );

                    log.info("Item {} -> name='{}', price={}, qty={}", i, name, price, qty);
                    return new Product(name, price, qty);
                })
                .collect(Collectors.toList());
        log.info("Total items read: {}", items.size());
        return items;
    }

    @Step("Click on Clear cart")
    public void clearShoppingCart() {
        log.info("Clearing shopping cart…");
        clearCart.scrollIntoView(false).click();
        switchTo().alert().accept();
        log.info("Clear cart confirmed.");
    }

    @Step("Get empty cart message")
    public String getEmptyCartMsg() {
        String msg = emptyCartMsg.shouldBe(visible).getText();
        log.info("Empty cart message: {}", msg);
        return msg;
    }

    @Step("Is cart empty?")
    public boolean isCartEmpty() {
        boolean displayed = cartContent.isDisplayed();
        log.info("Cart content displayed: {}", displayed);
        return displayed;
    }

    @Step("Click on checkout button")
    public void clickCheckoutBtn() {
        log.info("Attempting to proceed to checkout…");
        if (isCartNavBarDisplayed()) {
            proceedToCheckoutBtn.shouldBe(visible, Duration.ofSeconds(2))
                    .scrollIntoCenter()
                    .click();
            log.info("Clicked 'Proceed to Checkout'.");
        } else {
            log.info("Checkout button not available (cart navbar hidden).");
        }
    }

    @Step("Click on plus button")
    public void clickOnPlusBtn() {
        log.info("Click '+'");
        plusBtn.shouldBe(enabled).click();
        loadingSpinner.shouldBe(appear);
    }

    @Step("Click on minus button")
    public void clickOnMinusBtn() {
        log.info("Click '-'");
        minusBtn.shouldBe(enabled).click();
        loadingSpinner.shouldBe(appear);
    }

    @Step("Set value to Qty textbox")
    public void setValueToQtyTextBox(int num) {
        log.info("Set qty to {} and update cart", num);
        loadingSpinner.shouldBe(disappear);
        quantityInput.shouldBe(enabled).setValue(String.valueOf(num));
        updateCartBtn.shouldBe(enabled).click();
        loadingSpinner.shouldBe(appear);
    }

}
