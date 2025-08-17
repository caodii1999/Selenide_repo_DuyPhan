package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class ProductDetailsPage extends BasePage {

    private final String productInfo = "//div[@class = 'product-information-inner']//div[@class = 'fixed-content']";
    private final String productNameLocator =
            productInfo + "//h1[@class = 'product_title entry-title']";
    private final SelenideElement productName = $(By.xpath(productNameLocator));
    private final String addToCartBtnLocator = "//button[@type = 'submit' and contains(text(), 'Add to cart')]";
    private final String productPriceLocator = "//div[@class='row']//p[@class='price']/ins | //div[@class='row']//p[@class='price']/span/bdi";
    private final String productQuantityLocator = "//div[@class = 'quantity']//input[@class = 'input-text qty text']";
    private final SelenideElement addToCartBtn = $(By.xpath(addToCartBtnLocator));
    private final SelenideElement productPrice = $(By.xpath(productPriceLocator));
    private final SelenideElement productQuantity = $(By.xpath(productQuantityLocator));


    public Product getDetailProductInfo() {
        log.info("Retrieving product details from product page");

        String name = productName
                .shouldBe(visible, Duration.ofSeconds(3))
                .scrollIntoView(false)
                .getText()
                .toLowerCase()
                .trim();

        String price = productPrice
                .shouldBe(visible, Duration.ofSeconds(3))
                .scrollIntoView(false)
                .getText()
                .replace("$", "")
                .trim();

        String quantity = productQuantity
                .shouldBe(visible, Duration.ofSeconds(3))
                .scrollIntoView(false)
                .getValue();

        return new Product(name, price, quantity);
    }

    @Step("click on AddToCart button")
    public void clickOnAddToCartBtn() {
        log.info("Clicking 'Add to Cart' button");
        if (!isAddedPopupAppear()) {
            addToCartBtn
                    .scrollIntoView(false)
                    .shouldBe(visible, enabled)
                    .click();
            log.info("Product added to cart");
        }
    }
}
