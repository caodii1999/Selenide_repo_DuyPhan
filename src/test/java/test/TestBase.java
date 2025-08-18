package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import config.EnvConfig;
import helper.DriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class TestBase {

    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
//        EnvConfig config = new EnvConfig();
//
//        if (config.getRemoteUrl() != null && !config.getRemoteUrl().isEmpty()) {
//            Configuration.remote = config.getRemoteUrl();
//        } else {
//            Configuration.remote = null;
//        }
//
//        log.info("Selenium Grid Hub URL: {}", Configuration.remote);

        Configuration.timeout = 15000;
        Configuration.pollingInterval = 250;
        Configuration.pageLoadTimeout = 90000;
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional String browser) {
        EnvConfig config = new EnvConfig();

        Configuration.browser = config.getBrowser();

        if (browser != null && !browser.isBlank()) {
            Configuration.browser = browser;
            Configuration.remoteConnectionTimeout = 60_000;
            Configuration.remoteReadTimeout = 180_000;
        }

        if (Configuration.browser == null || Configuration.browser.isBlank()) {
            Configuration.browser = "chrome";
        }

        open(config.getBaseUrl());

        log.info("Opening browser: {}", Configuration.browser);
        log.info("Remote URL: {}", Configuration.remote);
        WebDriverRunner.getWebDriver()
                .manage().timeouts()
                .scriptTimeout(java.time.Duration.ofSeconds(30));
        DriverUtils.disableAds();
        DriverUtils.hidePopup();
        DriverUtils.dismissCookieBanner();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Selenide.clearBrowserCookies();
        Selenide.closeWebDriver();
    }
}
