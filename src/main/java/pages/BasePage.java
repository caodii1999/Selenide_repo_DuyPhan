package pages;

import com.codeborne.selenide.SelenideElement;
import enums.NavItems;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class BasePage {

    private static final String productAddedPopupLocator = "//div[@class = 'et-notify pos-fixed top right']";
    private static final SelenideElement productAddedPopup = $(By.xpath(productAddedPopupLocator));
    protected final String dynamicNavItemsLocator = "//ul[@id = 'menu-main-menu-1']//li[a[text() = '%s']]";
    protected final String allDepartmentLocator = "//div[@class = 'secondary-menu-wrapper']";
    protected final String dynamicDepartmentLocator = "//div[@class = 'secondary-menu-wrapper']//div[ul[@id = 'menu-all-departments-1']]//li[a[contains(text(), '%s')]]";
    protected final String myAccountBtnLocator = "//span[@class = 'et-element-label inline-block mob-hide']";
    protected final String myCartBtnLocator = "//div[@class = 'et_element et_b_header-cart  flex align-items-center cart-type1  et-quantity-right et-content-right et-content-dropdown et-content-toTop et_element-top-level']";
    protected final SelenideElement allDepartment = $(By.xpath(allDepartmentLocator));
    protected final SelenideElement myAccountBtn = $(By.xpath(myAccountBtnLocator));
    protected final SelenideElement myCartBtn = $(By.xpath(myCartBtnLocator));

    public static boolean isAddedPopupAppear() {
        return productAddedPopup.isDisplayed();
    }

    public void clickOnNavItem(String item) {
        log.info("Clicking on navigation item: {}", item);
        $(By.xpath(String.format(dynamicNavItemsLocator, item)))
                .shouldBe(visible, Duration.ofSeconds(5))
                .scrollIntoCenter()
                .shouldBe(enabled, Duration.ofSeconds(5))
                .click();
    }

    @Step("Navigate to Shop page")
    public void navigateToShopPage() {
        log.info("Navigating to Shop page");
        clickOnNavItem(NavItems.SHOP.getItemName());
    }

    @Step("Select Department")
    public void selectDepartment(String department) {
        log.info("Selecting department: {}", department);
        allDepartment.shouldBe(visible, Duration.ofSeconds(5)).hover();
        $(By.xpath(String.format(dynamicDepartmentLocator, department)))
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(enabled, Duration.ofSeconds(5))
                .click();
    }

    @Step("Click on My Account button")
    public void clickOnMyAccountButton() {
        log.info("Clicking on My Account button");
        myAccountBtn.shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(enabled, Duration.ofSeconds(5))
                .click();
    }

    @Step("Click on Cart")
    public void clickOnMyCartButton() {
        log.info("Clicking on Cart button");
        if (!isAddedPopupAppear()) {
            myCartBtn.scrollIntoView(false)
                    .shouldBe(visible, Duration.ofSeconds(10))
                    .hover()
                    .shouldBe(enabled, Duration.ofSeconds(10))
                    .click();
            log.info("Already clicked on My Cart button");
        }
    }
}
