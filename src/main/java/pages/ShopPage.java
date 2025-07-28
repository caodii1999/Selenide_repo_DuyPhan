package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class ShopPage extends BasePage {

  private final String closePopupBtnLocator = "//button[@aria-label = 'Close']";
  private final SelenideElement getClosePopupBtn = $(By.xpath(closePopupBtnLocator));

  public void clickOnCloseBtn() {
    getClosePopupBtn.click();
  }
}
