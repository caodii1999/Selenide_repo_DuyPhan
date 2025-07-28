package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.refresh;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;

public class ShoppingCartPage extends BasePage {

  private final String productNameLocator = "//a[@class = 'product-title']";
  private final String proceedToCheckoutBtnLocator = "//a[@class = 'checkout-button button alt wc-forward']";

  private final String productsNamesLocator = "//table[@class = 'shop_table shop_table_responsive cart woocommerce-cart-form__contents']//tbody//tr//td//div//a[@class = 'product-title']";
  private final ElementsCollection productsNames = $$(By.xpath(productsNamesLocator));

  private final SelenideElement productTitle = $(By.xpath(productNameLocator));
  private final SelenideElement proceedToCheckoutBtn = $(By.xpath(proceedToCheckoutBtnLocator));


  public String getProductName() {
    refresh();
    return productTitle.getText().trim();
  }

  @Step("get all products names in cart")
  public List<String> getAllProductsNames() {
    refresh();
    return productsNames.stream()
        .map(el -> el.scrollIntoView(false).getText())
        .collect(Collectors.toList());
  }

  @Step("click on checkout button")
  public void clickCheckoutBtn() {
    proceedToCheckoutBtn.click();
  }
}
