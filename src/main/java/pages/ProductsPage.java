package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import enums.Sort;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Slf4j
public class ProductsPage extends BasePage {

    private final String multipleItemsLocator = "//div[@class = 'text-center product-details']//h2[@class = 'product-title']";
    private final ElementsCollection multipleItems = $$(By.xpath(multipleItemsLocator));

    private final String AddToCartBtnLocator = "//div[@class = 'text-center product-details']//h2[@class = 'product-title']//following-sibling::a[text() = 'Add to cart']";
    private final ElementsCollection AddToCartBtn = $$(By.xpath(AddToCartBtnLocator));

    private final String successAddToCartPopupLocator = "//div[@class = 'et-notify pos-fixed top right']";
    private final SelenideElement successAddToCartPopup = $(By.xpath(successAddToCartPopupLocator));

    private final String gridViewBtnLocator = "//div[contains(@class, 'switch-grid')]";
    private final String listViewBtnLocator = "//div[contains(@class, 'switch-list')]";

    private final SelenideElement gridViewBtn = $(By.xpath(gridViewBtnLocator));
    private final SelenideElement listViewBtn = $(By.xpath(listViewBtnLocator));

    private final String sortDropDownLocator = "//select[@class = 'orderby']";
    private final SelenideElement sortDropDown = $(By.xpath(sortDropDownLocator));

    private final String productPricesLocator = "//div[@class='text-center product-details']//span[@class='woocommerce-Price-amount amount']/bdi[not(ancestor::del)]";
    private final ElementsCollection productPrices = $$(By.xpath(productPricesLocator));

    private final String loadSpinnerLocator = "//div[@class = 'et-loader product-ajax']";
    private final SelenideElement loadSpinner = $(By.xpath(loadSpinnerLocator));

    @Step("select random Item")
    public void selectRandomItem() {
        int size = multipleItems.size();
        int randomIndex = new Random().nextInt(size);
        SelenideElement randomElement = multipleItems.get(randomIndex);
        randomElement.shouldBe(enabled, Duration.ofSeconds(5)).scrollIntoView(false).click();
    }

    @Step("Add multiple items and get info")
    public List<Product> addMultipleProductsToCartAndGetInfo(int numberOfItemsToAdd) {
        int totalItems = Math.min(numberOfItemsToAdd, Math.min(AddToCartBtn.size(), multipleItems.size()));

        return IntStream.range(0, totalItems).filter(i -> isSuccessPopupDisappeared()).mapToObj(i -> {
            String name = multipleItems.get(i).scrollIntoView(true).getText().toLowerCase();

            String price = productPrices.get(i).getText().replace("$", "").trim();

            String quantity = "1";

            AddToCartBtn.get(i).scrollIntoView(false).click();

            return new Product(name, price, quantity);
        }).collect(Collectors.toList());
    }
    
    public boolean isSuccessPopupDisappeared() {
        return !successAddToCartPopup.isDisplayed();
    }

    @Step("click on grid btn")
    public void switchViewToGrid() {
        if (!gridViewBtn.has(cssClass("switcher-active"))) {
            gridViewBtn.scrollIntoView(false).click();
            gridViewBtn.shouldHave(cssClass("switcher-active"), Duration.ofSeconds(5));
        }
    }

    @Step("click on list btn")
    public void switchViewToList() {
        if (!listViewBtn.has(cssClass("switcher-active"))) {
            listViewBtn.scrollIntoView(false).click();
            listViewBtn.shouldHave(cssClass("switcher-active"), Duration.ofSeconds(5));
        }
    }

    @Step("verify grid view")
    public boolean isGridView() {
        return gridViewBtn.shouldBe(visible, Duration.ofSeconds(5)).getAttribute("class").contains(" switcher-active");
    }

    @Step("verify list view")
    public boolean isListView() {
        return listViewBtn.shouldBe(visible, Duration.ofSeconds(5)).getAttribute("class").contains(" switcher-active");
    }

    public void selectSortOption(String option) {
        sortDropDown.selectOptionContainingText(option);
    }

    @Step("sort low to high")
    public void selectLowToHighSortOption() {
        selectSortOption(Sort.LOW_TO_HIGH.getSortBy());
    }

    @Step("sort high to low")
    public void selectHighToLowSortOption() {
        selectSortOption(Sort.HIGH_TO_LOW.getSortBy());
    }

    public Boolean isLoadingSpinnerAppeared() {
        return loadSpinner.getAttribute("class").contains(" loading");
    }

    public List<Double> getProductPrices() {
        if (isLoadingSpinnerAppeared()) {
            throw new IllegalStateException("Loading spinner did not disappear within timeout.");
        }
        return productPrices.stream().map(SelenideElement::getText).map(text -> text.replaceAll("[^\\d.]", "")).map(Double::parseDouble).collect(Collectors.toList());
    }

    @Step("is items sort low to high by price")
    public boolean isSortedLowToHigh(List<Double> prices) {
        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        return prices.equals(sorted);
    }

    @Step("is items sort high to low by price")
    public boolean isSortedHighToLow(List<Double> prices) {
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        return prices.equals(sorted);
    }
}
