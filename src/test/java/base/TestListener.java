package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;

public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        Object currentClass = result.getInstance();

        // Cast to your BaseTest class which contains the WebDriver instance
        BaseTest baseTest = (BaseTest) currentClass;
        WebDriver driver = baseTest.driver;

        // Take screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotsDir = System.getProperty("user.dir") + "/screenshots/";
            File destDir = new File(screenshotsDir);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            File destination = new File(screenshotsDir + result.getName() + ".png");
            FileHandler.copy(screenshot, destination);
            baseTest.log.info("Screenshot saved at: " + destination.getAbsolutePath());
        } catch (IOException e) {
            baseTest.log.error("Failed to save screenshot: " + e.getMessage());
        }
    }
}
