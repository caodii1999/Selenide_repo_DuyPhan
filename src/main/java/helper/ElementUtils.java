package helper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

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

  public static void setValueToInputFields(String xpath, String inputField, String value) {
    SelenideElement el = $(By.xpath(String.format(xpath, inputField)));
    el.setValue(value);
  }

  public static void selectValueInDropDown(SelenideElement el, ElementsCollection col,
      String name) {
    el.click();
    col.findBy(text(name)).click();
  }
}
