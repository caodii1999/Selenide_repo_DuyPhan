package helper;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class DriverUtils {

    public static boolean isPageDisplayed(String pageName) {
        try {
            webdriver().shouldHave(urlContaining(pageName));
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public static void disableAds() {
        executeJavaScript(
                "document.querySelectorAll('[src*=wppopupmaker], .pum-overlay, .pum-active').forEach(e => e.remove());");
    }

    public static void hidePopup() {
        executeJavaScript(
                "document.querySelector(\"span.close.pos-absolute.right.top\")?.style.setProperty('display', 'none', 'important');");
    }

    public static void dismissCookieBanner() {
        executeJavaScript("document.getElementById('cn-accept-cookie')?.click();");
    }
}
