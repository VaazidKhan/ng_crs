package utilities;

import base.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static Properties config;
    private static final Logger log = Logger.getLogger(ExtentReportManager.class);

    String repName;

    @Override
    public void onStart(ITestContext testContext) {
        // Load config properties
        config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            config.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp
        repName = "Test-Report-" + timeStamp + ".html";

        // Ensure reports directory exists
        String reportsDir = System.getProperty("user.dir") + "/reports/";
        File reportDirFile = new File(reportsDir);
        if (!reportDirFile.exists()) {
            reportDirFile.mkdirs();
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportsDir + repName); // specify location of the report
        sparkReporter.config().setDocumentTitle("CRS_NG_Demo_Project"); // title of report
        sparkReporter.config().setReportName("CRS_NG"); // name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "CRS-NG");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", config.getProperty("username"));
        extent.setSystemInfo("Environment", "QA");
    }

    @Override
    public void onFinish(ITestContext testContext) {
        // Log end time
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        log.info("Test finished at: " + endTime);

        extent.flush(); // Writes the test report once the execution is completed
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());

        // Capture screenshot and attach to report
        Object currentClass = result.getInstance();
        BaseTest baseTest = (BaseTest) currentClass;
        WebDriver driver = baseTest.getDriver();

        String screenshotPath = captureScreenshot(driver, result.getName());
        if (screenshotPath != null) {
            try {
                test.fail("Screenshot of failure:",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                test.fail("Failed to attach screenshot");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }

    // Utility method to capture screenshots
    public String captureScreenshot(WebDriver driver, String testName) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Ensure screenshots directory exists
        String screenshotsDir = System.getProperty("user.dir") + "/screenshots/";
        File destDir = new File(screenshotsDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        String destination = screenshotsDir + testName + "_" + dateName + ".png";
        File finalDestination = new File(destination);
        try {
            FileHandler.copy(source, finalDestination); // Save screenshot
            return destination; // Return the path for report attachment
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
