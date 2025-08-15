package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Product;
import org.openqa.selenium.By;

public class ShoppingCartPage extends BasePage {

  private final String productNameLocator = "//a[@class = 'product-title']";
  private final String proceedToCheckoutBtnLocator = "//a[@class = 'checkout-button button alt wc-forward']";
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
    return new Product(
        productName.getText().toLowerCase().trim(),
        productPrice.getText().replace("$", "").trim(),
        productQuantity.getValue()
    );
  }

  @Step("Get all products (name, price, quantity) in cart")
  public List<Product> getAllProductsInCart() {
    return IntStream.range(0, productsNames.size())
        .mapToObj(i -> {
          String name = productsNames.get(i)
              .shouldBe(visible, Duration.ofSeconds(3))
              .scrollIntoView(false)
              .getText()
              .toLowerCase()
              .trim();

          String price = productPrices.get(i)
              .shouldBe(visible, Duration.ofSeconds(3))
              .scrollIntoView(false)
              .getText()
              .replace("$", "")
              .trim();

          String quantity = "1";

          return new Product(name, price, quantity);
        })
        .collect(Collectors.toList());
  }


  @Step("click on checkout button")
  public void clickCheckoutBtn() {
    proceedToCheckoutBtn.click();
  }
}
