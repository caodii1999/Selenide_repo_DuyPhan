package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class ProductDetailsPage extends BasePage {

    private final String productInfo = "//div[@class = 'product-information-inner']//div[@class = 'fixed-content']";
    private final String productNameLocator =
            productInfo + "//h1[@class = 'product_title entry-title']";
    private final SelenideElement productName = $(By.xpath(productNameLocator));
    private final String addToCartBtnLocator = "//button[contains(text(), 'Add to cart')]";
    private final String productPriceLocator = "//div[@class='row']//p[@class='price']/ins | //div[@class='row']//p[@class='price']/span/bdi";
    private final String productQuantityLocator = "//div[@class = 'quantity']//input[@class = 'input-text qty text']";
    private final SelenideElement addToCartBtn = $(By.xpath(addToCartBtnLocator));
    private final SelenideElement productPrice = $(By.xpath(productPriceLocator));
    private final SelenideElement productQuantity = $(By.xpath(productQuantityLocator));

    @Step("Get product name")
    public String getProductName() {
        return productName
                .shouldBe(visible, Duration.ofSeconds(3))
                .scrollIntoView(false)
                .getText()
                .toLowerCase()
                .trim();
    }

    @Step("Get product unit price")
    public double getProductPrice() {
        return Double.parseDouble(
                productPrice
                        .shouldBe(visible, Duration.ofSeconds(3))
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
        return Integer.parseInt(
                productQuantity
                        .shouldBe(visible, Duration.ofSeconds(3))
                        .scrollIntoView(false)
                        .getValue()
                        .trim()
        );
    }

    public Product getDetailProductInfo() {
        log.info("Retrieving product details from product page");
        String name = getProductName();
        double price = getProductPrice();
        int quantity = getProductQuantity();
        return new Product(name, price, quantity);
    }

    @Step("click on AddToCart button")
    public void clickOnAddToCartBtn() {
        log.info("Clicking 'Add to Cart' button");
        addToCartBtn
                .scrollIntoView(false)
                .shouldBe(visible, enabled)
                .click();
        addToCartBtn.shouldNotHave(cssClass("loading"));
        log.info("Product added to cart");
    }
}
