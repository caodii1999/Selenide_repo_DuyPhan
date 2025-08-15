package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import config.EnvConfig;
import helper.DriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class TestBase {

    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
        EnvConfig config = new EnvConfig();

        if (config.getRemoteUrl() != null && !config.getRemoteUrl().isEmpty()) {
            Configuration.remote = config.getRemoteUrl();
        } else {
            Configuration.remote = null;
        }

        Configuration.browser = config.getBrowser();
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 30000;
        Configuration.reportsFolder = "target/reports";

        log.info("Selenium Grid Hub URL: {}", Configuration.remote);
        log.info("Running tests on browser: {}", Configuration.browser);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional String browser) {

        if (browser != null && !browser.isEmpty()) {
            Configuration.browser = browser;
        }

        open(new EnvConfig().getBaseUrl());

        log.info("Opening browser: {}", Configuration.browser);
        log.info("Remote URL: {}", Configuration.remote);

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
