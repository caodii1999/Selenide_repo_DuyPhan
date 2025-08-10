package pages;

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

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

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


    @Step("Get product info")
    public Product getSingleProductInfo() {
        log.info("Retrieving single product info from cart");
        String name = productName.shouldBe(visible, Duration.ofSeconds(5))
                .scrollIntoView(false)
                .getText()
                .toLowerCase()
                .trim();

        String price = productPrice.shouldBe(visible, Duration.ofSeconds(5))
                .scrollIntoView(false)
                .getText()
                .replace("$", "")
                .trim();

        String quantity = productQuantity.shouldBe(visible, Duration.ofSeconds(5))
                .scrollIntoView(false)
                .getValue();

        return new Product(name, price, quantity);
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

                    String price = productPrices.get(i)
                            .shouldBe(visible, Duration.ofSeconds(5))
                            .scrollIntoView(false)
                            .getText()
                            .replace("$", "")
                            .trim();

                    // in cart, usually quantity field is always present
                    String quantity = productQuantities.get(i)
                            .shouldBe(visible, Duration.ofSeconds(5))
                            .scrollIntoView(false)
                            .getValue();

                    log.info("Product found - Name: {}, Price: {}, Quantity: {}", name, price, quantity);
                    return new Product(name, price, quantity);
                })
                .collect(Collectors.toList());
    }

  @Step("click on checkout button")
  public void clickCheckoutBtn() {
    proceedToCheckoutBtn.click();
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
}
