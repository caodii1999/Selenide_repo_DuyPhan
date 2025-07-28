package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class BasePage {

  protected final String dynamicNavItemsLocator = "//ul[@id = 'menu-main-menu-1']//li[a[text() = '%s']]";
  protected final String allDepartmentLocator = "//div[@class = 'secondary-menu-wrapper']";
  protected final String dynamicDepartmentLocator = "//div[@class = 'secondary-menu-wrapper']//div[ul[@id = 'menu-all-departments-1']]//li[a[contains(text(), '%s')]]";
  protected final String myAccountBtnLocator = "//span[contains(text(), 'Log in / Sign up')]";
  protected final String myCartBtnLocator = "//a[@href = 'https://demo.testarchitect.com/cart/']";

  protected final SelenideElement allDepartment = $(By.xpath(allDepartmentLocator));
  protected final SelenideElement myAccountBtn = $(By.xpath(myAccountBtnLocator));
  protected final SelenideElement myCartBtn = $(By.xpath(myCartBtnLocator));

  @Step("click on navigate item")
  public void clickOnNavItem(String item) {
    $(By.xpath(String.format(dynamicNavItemsLocator, item))).scrollIntoCenter().click();
  }

  @Step("Select Department")
  public void selectDepartment(String department) {
    allDepartment.hover();
    $(By.xpath(String.format(dynamicDepartmentLocator, department))).click();
  }

  @Step("Click on My Account button")
  public void clickOnMyAccountButton() {
    myAccountBtn.click();
  }

  @Step("Click on Cart")
  public void clickOnMyCartButton() {
    myCartBtn.scrollIntoView(false).click();
  }
}
