package test;

import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import config.EnvConfig;
import helper.DriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

@Slf4j
public class TestBase {

  @BeforeSuite
  public void globalSetup() {
    Configuration.browser = EnvConfig.getBrowser();
    Configuration.baseUrl = EnvConfig.getBaseUrl();
    Configuration.timeout = 10000; // 10 seconds
    Configuration.pageLoadTimeout = 30000; // 30 seconds

    log.info("Test suite initialized with browser: {} and base URL: {}",
        EnvConfig.getBrowser(), EnvConfig.getBaseUrl());
  }

  @BeforeMethod
  public void setup() {
    try {
      open(EnvConfig.getBaseUrl());

      WebDriverRunner.getWebDriver().manage().window().maximize();

      DriverUtils.disableAds();
      DriverUtils.hidePopup();
      DriverUtils.dismissCookieBanner();

      log.info("Test setup completed successfully");

    } catch (Exception e) {
      log.error("Failed to setup test: {}", e.getMessage(), e);
      throw new RuntimeException("Test setup failed", e);
    }
  }

  @AfterMethod
  public void tearDown() {
    Selenide.clearBrowserCookies();
    Selenide.closeWebDriver();
  }
}