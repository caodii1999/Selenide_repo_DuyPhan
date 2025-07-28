package pages;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class ProductsPage extends BasePage {

  private final String randomItemsLocator = "//div[@class = 'text-center product-details']//h2[@class = 'product-title']";
  private final ElementsCollection randomItems = $$(By.xpath(randomItemsLocator));

  private final String randomItemsAddToCartBtnLocator = "//div[@class = 'text-center product-details']//h2[@class = 'product-title']//following-sibling::a[text() = 'Add to cart']";
  private final ElementsCollection randomItemsAddToCartBtn = $$(
      By.xpath(randomItemsAddToCartBtnLocator));


  private final String successAddToCartPopupLocator = "//div[@class = 'et-notify pos-fixed top right']";
  private final SelenideElement successAddToCartPopup = $(By.xpath(successAddToCartPopupLocator));

  private final String gridViewBtnLocator = "//div[contains(@class, 'switch-grid')]";
  private final String listViewBtnLocator = "//div[contains(@class, 'switch-list')]";

  private final SelenideElement gridViewBtn = $(By.xpath(gridViewBtnLocator));
  private final SelenideElement listViewBtn = $(By.xpath(listViewBtnLocator));

  @Step("select random Item")
  public void selectRandomItem() {
    int size = randomItems.size();
    int randomIndex = new Random().nextInt(size);
    SelenideElement randomElement = randomItems.get(randomIndex);
    randomElement.shouldBe(enabled, Duration.ofSeconds(5)).scrollIntoView(false).click();
  }

  @Step("add multiple items and get")
  public List<String> addMultipleProductsToCartAndGetNames(int numberOfItemsToAdd) {
    List<SelenideElement> buttons = new ArrayList<>(
        randomItemsAddToCartBtn.stream().toList());
    List<SelenideElement> names = new ArrayList<>(randomItems.stream().toList());
    Collections.shuffle(buttons);

    List<String> addedProductNames = new ArrayList<>();
    int count = Math.min(numberOfItemsToAdd, buttons.size());

    for (int i = 0; i < count; i++) {
      if (isSuccessPopupDisappeared()) {
        String productName = names.get(i).scrollIntoView(true).getText();
        addedProductNames.add(productName);
        buttons.get(i).scrollIntoView(false).click();
      }
    }
    return addedProductNames;
  }

//  @Step("get all products names")
//  public List<String> getAllItemsNames() {
//    return productsNames.stream()
//        .map(el -> el.scrollIntoView(false).getText())
//        .collect(Collectors.toList());
//  }

  public boolean isSuccessPopupDisappeared() {
    return !successAddToCartPopup.isDisplayed();
  }

  @Step("click on grid btn")
  public void switchViewToGrid() {
    gridViewBtn.scrollIntoView(false).click();
    gridViewBtn.shouldHave(cssClass("switcher-active"), Duration.ofSeconds(5));
  }

  @Step("click on list btn")
  public void switchViewToList() {
    listViewBtn.scrollIntoView(false).click();
    listViewBtn.shouldHave(cssClass("switcher-active"), Duration.ofSeconds(5));

  }

  @Step("verify grid view")
  public boolean isGridView() {
    return gridViewBtn.shouldBe(visible, Duration.ofSeconds(5)).getAttribute("class")
        .contains(" switcher-active");
  }

  @Step("verify list view")
  public boolean isListView() {
    return listViewBtn.shouldBe(visible, Duration.ofSeconds(5)).getAttribute("class")
        .contains(" switcher-active");
  }
}
