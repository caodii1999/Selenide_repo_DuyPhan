package test;

import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import config.EmailConfig;
import config.EnvConfig;
import helper.DriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

@Slf4j
public class TestBase {

  private EnvConfig config;

  @BeforeSuite
  public void globalSetup() {
    config = new EnvConfig();

    Configuration.browser = config.getBrowser();
    Configuration.baseUrl = config.getBaseUrl();
    Configuration.timeout = 10000; // 10 seconds
    Configuration.pageLoadTimeout = 30000; // 30 seconds

    log.info("Test suite initialized with browser: {} and base URL: {}",
        config.getBrowser(), config.getBaseUrl());
  }

  public String initEmail() {
    String email = config.getEmail();
    if (email.isEmpty()) {
      try {
        email = EmailConfig.getEmailAddress();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return email;
  }

  private String initPassword() {
    return config.getPassword();
  }

  @BeforeMethod
  public void setup() {
    try {
      open(config.getBaseUrl());

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