package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import enums.Pages;
import enums.Sort;
import helper.DriverUtils;
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

    private final String addToCartBtnLocator = "//div[@class = 'text-center product-details']//h2[@class = 'product-title']//following-sibling::a[text() = 'Add to cart']";
    private final ElementsCollection addToCartBtn = $$(By.xpath(addToCartBtnLocator));

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

    private final String loadSpinnerLocator = "//div[contains(@class, 'et-loader product-ajax')]";
    private final SelenideElement loadSpinner = $(By.xpath(loadSpinnerLocator));

    private final String productAddedPopupLocator = "//div[@class = 'et-notify pos-fixed top right']";
    private final SelenideElement productAddedPopup = $(By.xpath(productAddedPopupLocator));

    @Step("select random Item")
    public void selectRandomItem() {
        log.info("Selecting a random item from the shop page");
        DriverUtils.isPageDisplayed(Pages.SHOP.getPageName());

        multipleItems.shouldHave(CollectionCondition.sizeGreaterThan(1), Duration.ofSeconds(5));
        int size = multipleItems.size();
        if (size == 0) {
            log.warn("No items found to select");
            return;
        }

        int randomIndex = new Random().nextInt(size);
        multipleItems.get(randomIndex)
                .shouldBe(enabled, clickable)
                .scrollIntoView(false)
                .click();
        if (isAddedPopupAppear()) {
            log.info("waiting for pop up hidden");
        }
        log.info("added item successfully");
    }

    @Step("Add multiple items and get info")
    public List<Product> addMultipleProductsToCartAndGetInfo(int numberOfItemsToAdd) {
        log.info("Adding {} products to cart and retrieving their info", numberOfItemsToAdd);

        addToCartBtn.shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(5));
        multipleItems.shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(5));

        int totalItems = Math.min(numberOfItemsToAdd, Math.min(addToCartBtn.size(), multipleItems.size()));

        return IntStream.range(0, totalItems)
                .mapToObj(i -> {
                    String name = multipleItems.get(i)
                            .shouldBe(visible, Duration.ofSeconds(5))
                            .scrollIntoView(true)
                            .getText()
                            .toLowerCase();
                    String price = productPrices.get(i)
                            .shouldBe(visible, Duration.ofSeconds(5))
                            .getText().replace("$", "").trim();
                    String quantity = "1";

                    addToCartBtn.get(i)
                            .shouldBe(visible, Duration.ofSeconds(5))
                            .scrollIntoView(false)
                            .click();

                    successAddToCartPopup.shouldBe(visible, Duration.ofSeconds(5))
                            .should(disappear, Duration.ofSeconds(10));

                    log.info("Added to cart: {} | Price: {} | Quantity: {}", name, price, quantity);
                    return new Product(name, price, quantity);
                })
                .collect(Collectors.toList());
    }

    public boolean isSuccessPopupDisappeared() {
        log.info("Checking if success popup has disappeared");
        return successAddToCartPopup.should(disappear, Duration.ofSeconds(10)).exists();
    }

    @Step("click on grid btn")
    public void switchViewToGrid() {
        log.info("Switching view to grid layout");
        if (!gridViewBtn.has(cssClass("switcher-active"))) {
            gridViewBtn.shouldBe(visible, Duration.ofSeconds(5)).scrollIntoView(false).click();
            gridViewBtn.shouldHave(cssClass("switcher-active"), Duration.ofSeconds(5));
        }
    }

    @Step("click on list btn")
    public void switchViewToList() {
        log.info("Switching view to list layout");
        if (!listViewBtn.has(cssClass("switcher-active"))) {
            listViewBtn.shouldBe(visible, Duration.ofSeconds(5)).scrollIntoView(false).click();
            listViewBtn.shouldHave(cssClass("switcher-active"), Duration.ofSeconds(5));
        }
    }

    @Step("verify grid view")
    public boolean isGridView() {
        log.info("Verifying if grid view is active");
        return gridViewBtn.shouldBe(visible, Duration.ofSeconds(5))
                .getAttribute("class").contains("switcher-active");
    }

    @Step("verify list view")
    public boolean isListView() {
        log.info("Verifying if list view is active");
        return listViewBtn.shouldBe(visible, Duration.ofSeconds(5))
                .getAttribute("class").contains("switcher-active");
    }

    public void selectSortOption(String option) {
        log.info("Selecting sort option: {}", option);
        sortDropDown.shouldBe(visible, Duration.ofSeconds(5)).selectOptionContainingText(option);
        loadSpinner.should(disappear, Duration.ofSeconds(10));
    }

    @Step("sort low to high")
    public void selectLowToHighSortOption() {
        log.info("Sorting items from low to high");
        selectSortOption(Sort.LOW_TO_HIGH.getSortBy());
    }

    @Step("sort high to low")
    public void selectHighToLowSortOption() {
        log.info("Sorting items from high to low");
        selectSortOption(Sort.HIGH_TO_LOW.getSortBy());
    }

    public Boolean isLoadingSpinnerAppeared() {
        log.info("Checking if loading spinner is visible");
        return loadSpinner.shouldBe(visible, Duration.ofSeconds(5)).getAttribute("class").contains("loading");
    }

    public List<Double> getProductPrices() {
        log.info("Retrieving product prices from the page");
        loadSpinner.should(disappear, Duration.ofSeconds(10));

        return productPrices
                .shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(5))
                .stream()
                .map(el -> el.shouldBe(visible, Duration.ofSeconds(5)))
                .map(SelenideElement::getText)
                .map(text -> text.replaceAll("[^\\d.]", ""))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    @Step("is items sort low to high by price")
    public boolean isSortedLowToHigh(List<Double> prices) {
        log.info("Checking if items are sorted from low to high");
        loadSpinner.should(disappear, Duration.ofSeconds(10));
        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        return prices.equals(sorted);
    }

    @Step("is items sort high to low by price")
    public boolean isSortedHighToLow(List<Double> prices) {
        log.info("Checking if items are sorted from high to low");
        loadSpinner.should(disappear, Duration.ofSeconds(10));
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        return prices.equals(sorted);
    }
}
