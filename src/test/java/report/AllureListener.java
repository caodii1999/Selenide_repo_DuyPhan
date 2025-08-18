package report;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

@Slf4j
public class AllureListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Running test: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test Passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("Test Failed: {}", result.getMethod().getMethodName());
        byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
        Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(screenshot));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("Test Skipped: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }
}
