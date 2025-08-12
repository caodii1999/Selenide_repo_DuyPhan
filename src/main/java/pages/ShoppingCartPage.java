package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.util.List;
import java.util.stream.Collectors;
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
  private final SelenideElement productPrice = $(By.xpath(productPriceLocator));
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

  @Step("get all products names in cart")
  public List<String> getAllProductNames() {
    return productsNames.stream()
        .map(el -> el.scrollIntoView(false).getText().toLowerCase())
        .collect(Collectors.toList());
  }

  @Step("click on checkout button")
  public void clickCheckoutBtn() {
    proceedToCheckoutBtn.click();
  }
}
