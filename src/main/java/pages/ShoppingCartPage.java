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

  private final SelenideElement productTitle = $(By.xpath(productNameLocator));
  private final SelenideElement proceedToCheckoutBtn = $(By.xpath(proceedToCheckoutBtnLocator));


  public Product getProductName() {
    String productName = productTitle.getText().toLowerCase().trim();
    return new Product(productName);
  }

  @Step("get all products names in cart")
  public List<Product> getAllProductsNames() {
    return productsNames.stream()
        .map(el -> {
          String name = el.scrollIntoView(false).getText().toLowerCase();
          return new Product(name);
        })
        .collect(Collectors.toList());
  }

  @Step("click on checkout button")
  public void clickCheckoutBtn() {
    proceedToCheckoutBtn.click();
  }
}
