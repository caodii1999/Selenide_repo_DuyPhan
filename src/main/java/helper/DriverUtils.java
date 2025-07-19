package helper;

import static com.codeborne.selenide.Selenide.executeJavaScript;

import com.codeborne.selenide.WebDriverRunner;

public class DriverUtils {

	public static boolean verifyDisplayingPage(String title) {
		boolean status = false;

		String currentUrl = WebDriverRunner.url();
		System.out.println(currentUrl);
		if (currentUrl.contains(title))
			return status = true;
		else
			return status;
	}

	public static void disableAds() {
		executeJavaScript(
				"document.querySelectorAll('[src*=wppopupmaker], .pum-overlay, .pum-active').forEach(e => e.remove());");

	}
}
