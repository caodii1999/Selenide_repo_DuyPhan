package helper;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ElementUtils {

    public static void setValueToInputFields(SelenideElement xpath, String value) {
        SelenideElement el = $(xpath).shouldBe(enabled);
        el.scrollIntoView(false).setValue(value);
    }

    public static void selectValueInDropDown(SelenideElement el, SelenideElement col, String name) {
        el.shouldBe(visible, enabled).scrollIntoView(false).click();
        col.scrollIntoView(false).setValue(name).pressEnter();
    }
}
