package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import helper.Constants;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Slf4j
public class ProductDetailsPage extends BasePage {

    private final String productInfo = "//div[@class = 'product-information-inner']//div[@class = 'fixed-content']";
    private final String productNameLocator =
            productInfo + "//h1[@class = 'product_title entry-title']";
    private final SelenideElement productName = $(By.xpath(productNameLocator));
    private final String addToCartBtnLocator = "//button[contains(text(), 'Add to cart')]";
    private final String productPriceLocator = "//div[@class='row']//p[@class='price']/ins | //div[@class='row']//p[@class='price']/span/bdi";
    private final String productQuantityLocator = "//div[@class = 'quantity']//input[@class = 'input-text qty text']";
    private final SelenideElement addToCartBtn = $(By.xpath(addToCartBtnLocator));
    private final SelenideElement productPrice = $(By.xpath(productPriceLocator));
    private final SelenideElement productQuantity = $(By.xpath(productQuantityLocator));
    private final String reviewBtnLocator = "//a[@id = 'tab_reviews']";
    private final SelenideElement reviewBtn = $(By.xpath(reviewBtnLocator));
    private final String commentInputFieldLocator = "//p[@class = 'comment-form-comment']//textarea";
    private final SelenideElement commentInputField = $(By.xpath(commentInputFieldLocator));
    private final String reviewStarLocator = "//p[@class = 'stars']//a";
    private final ElementsCollection reviewStars = $$(By.xpath(reviewStarLocator));
    private final String submitBtnLocator = "//input[@id = 'submit']";
    private final SelenideElement submitBtn = $(By.xpath(submitBtnLocator));
    private final String newReviewLocator = "//li[contains(@class, 'review byuser')]";
    private final SelenideElement newReview = $(By.xpath(newReviewLocator));
    private final String newReviewStarLocator = "//li[contains(@class, 'review byuser')]//div//div//div[@class = 'star-rating']";
    private final SelenideElement newReviewStar = $(By.xpath(newReviewStarLocator));
    private final String newReviewCommentLocator = "//li[contains(@class, 'review byuser')]//div//div//div[@class = 'description']//p";
    private final SelenideElement newReviewComment = $(By.xpath(newReviewCommentLocator));
    private final String descriptionBtnLocator = "//a[@id = 'tab_description']";
    private final SelenideElement descriptionBtn = $(By.xpath(descriptionBtnLocator));
    private final String commentListLocator = "//ol[@class = 'commentlist']//li";
    private final ElementsCollection commentList = $$(By.xpath(commentListLocator));
    private final String numberOfReviewLocator = "//a[@class = 'woocommerce-review-link']//span[@class = 'count']";
    private final SelenideElement numberOfReview = $(By.xpath(numberOfReviewLocator));

    @Step("Get product name")
    public String getProductName() {
        String name = productName
                .shouldBe(visible, Duration.ofSeconds(3))
                .scrollIntoView(false)
                .getText()
                .toLowerCase()
                .trim();
        log.info("Product name: {}", name);
        return name;
    }

    @Step("Get product unit price")
    public double getProductPrice() {
        double price = Double.parseDouble(
                productPrice
                        .shouldBe(visible, Duration.ofSeconds(3))
                        .scrollIntoView(false)
                        .getText()
                        .replace('\u00A0', ' ')
                        .replace("$", "")
                        .replace(",", "")
                        .replaceAll("[^0-9.\\-]", "")
                        .trim()
        );
        log.info("Unit price: {}", price);
        return price;
    }

    @Step("Get product quantity")
    public int getProductQuantity() {
        int qty = Integer.parseInt(
                productQuantity
                        .shouldBe(visible, Duration.ofSeconds(3))
                        .scrollIntoView(false)
                        .getValue()
                        .trim()
        );
        log.info("Quantity: {}", qty);
        return qty;
    }

    public Product getDetailProductInfo() {
        log.info("Retrieving product details from product page");
        String name = getProductName();
        double price = getProductPrice();
        int quantity = getProductQuantity();
        return new Product(name, price, quantity);
    }

    @Step("click on AddToCart button")
    public void clickOnAddToCartBtn() {
        log.info("Clicking 'Add to Cart' button");
        addToCartBtn
                .scrollIntoView(false)
                .shouldBe(visible, enabled)
                .click();
        addToCartBtn.shouldNotHave(cssClass("loading"));
        log.info("Product added to cart");
    }

    @Step("Click on review tab")
    public void clickOnReviewTab() {
        log.info("Opening 'Reviews' tab…");
        reviewBtn.shouldBe(visible).scrollIntoView(false).click();
        commentInputField.shouldBe(appear);
        log.info("'Reviews' tab opened.");
    }

    @Step("Click on submit button")
    public void clickOnSubmitBtn() {
        log.info("Submitting review…");
        submitBtn.scrollIntoView(false).shouldBe(enabled).click();
        log.info("Review submitted.");
    }

    @Step("Select star")
    public int selectRandomStar() {
        reviewStars.shouldHave(CollectionCondition.sizeGreaterThan(1), Duration.ofSeconds(5));
        int rating = new Random().nextInt(1, 6);
        log.info("Selecting star rating: {}", rating);
        reviewStars.get(rating - 1)
                .scrollIntoView(false)
                .shouldBe(enabled, clickable)
                .click();
        log.info("Star {} selected.", rating);
        return rating;
    }

    @Step("Write comment")
    public String writeComment() {
        String message = Constants.COMMENT;
        log.info("Typing comment ({} chars)…", message.length());
        commentInputField.scrollIntoView(false)
                .shouldBe(enabled)
                .setValue(message);
        commentInputField.shouldHave(value(message));
        log.info("Comment typed.");
        return message;
    }

    @Step("Get new review star")
    public int getNewReviewStar() {
        newReview.shouldBe(visible).scrollIntoView(false);
        String label = newReviewStar.should(appear).getAttribute("aria-label");
        log.info("New review aria-label: {}", label);
        int star = Integer.parseInt(label.split(" ")[1]);
        log.info("Parsed new review star: {}", star);
        return star;
    }

    @Step("Get new comment")
    public String getNewReviewComment() {
        String text = newReviewComment.scrollIntoView(false)
                .shouldBe(visible)
                .getText();
        log.info("New review comment: {}", text);
        return text;
    }

    public int getNumberOfReviewsFromList() {
        newReview.shouldBe(visible).scrollIntoView(false);
        int count = commentList.size();
        log.info("Comment list count: {}", count);
        return count;
    }

    public int getActualNumberOfReview() {
        newReview.shouldBe(visible).scrollIntoView(false);
        int count = Integer.parseInt(numberOfReview.scrollIntoView(false).getText());
        log.info("Displayed review count: {}", count);
        return count;
    }
}
