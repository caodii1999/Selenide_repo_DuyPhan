package helper;

import static com.codeborne.selenide.Selenide.Wait;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import java.time.Duration;

public class DriverUtils {

  public static boolean isPageDisplayed(String pageName) {
    Selenide.Wait().withTimeout(Duration.ofSeconds(10))
        .until(driver -> WebDriverRunner.url().contains(pageName));

    return WebDriverRunner.url().contains(pageName);
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

  public static void waitForPageLoad() {
    Wait().until(webDriver -> executeJavaScript("return document.readyState").equals("complete"));
  }

  public static void hideOverlapItem() {
    executeJavaScript("document.querySelector('a.item-link').style.display = 'none';");
  }

}
