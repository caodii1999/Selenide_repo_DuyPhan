package helper;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class DriverUtils {

    public static boolean isPageDisplayed(String pageName) {
        try {
            // Wait until the URL contains the expected text
            Wait<WebDriver> wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10));
            wait.until(driver -> WebDriverRunner.url().contains(pageName));
            return true;
        } catch (TimeoutException e) {
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

    public static void scrollToTop() {
        executeJavaScript("window.scrollTo(0, 0);");
    }
}
