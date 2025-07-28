package helper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ElementUtils {

  public static void clickOnElement(String xpath) {
    $x(xpath).shouldBe(visible).click();
  }

  public static void clickOnDynamicElement(String xpath, String dynamicVariable) {
    $x(String.format(xpath, dynamicVariable)).shouldBe(visible).click();
  }

  public static void inputToElement(String xpath, String message) {
    $x(xpath).shouldBe(visible).sendKeys(message);
  }

  public static void hoverOnElement(String xpath) {
    $x(xpath).shouldBe(visible).scrollIntoView(true).hover();
  }

  public static String getElementText(String xpath) {
    return $x(xpath).getText();
  }

  public static void disableSalePopup() {
    $x("//span[@class = 'close pos-absolute right top']").click();
  }
}
