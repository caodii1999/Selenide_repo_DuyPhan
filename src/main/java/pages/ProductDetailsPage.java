package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.Product;
import org.openqa.selenium.By;

public class ProductDetailsPage extends BasePage {

  private final String productInfo = "//div[@class = 'product-information-inner']//div[@class = 'fixed-content']";
  private final String productNameLocator =
      productInfo + "//h1[@class = 'product_title entry-title']";
  private final SelenideElement productName = $(By.xpath(productNameLocator));
  private final String addToCartBtnLocator = "//button[@type = 'submit' and contains(text(), 'Add to cart')]";
  private final String productPriceLocator = "//div[@class='row']//p[@class='price']/ins | //div[@class='row']//p[@class='price']/span/bdi";
  private final SelenideElement addToCartBtn = $(By.xpath(addToCartBtnLocator));
  private final SelenideElement productPrice = $(By.xpath(productPriceLocator));

  public Product getProductName() {
    String name = productName.scrollIntoView(false).getText().toLowerCase().trim();
    return new Product(name);
  }

  public String getProductPrice() {
    return productPrice.scrollIntoView(false).getText().trim();
  }

  @Step("click on AddToCart button")
  public void ClickOnAddToCartBtn() {
    addToCartBtn.click();
  }
}
