package pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseTest;

public class Agency extends BaseTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(Agency.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error");

    // Constructor to initialize WebDriver and WebDriverWait
    public Agency(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Reduced explicit wait time
    }

    // Locators
    private By homeButton = By.xpath("//a[@class='side-btn active']");
    private By agencyButton = By.xpath("//button[normalize-space()='Agency']");
    private By agenciesButton = By.xpath("//li[@class='active']//div[@class='d-flex align-items-center flex-column tab-link gap-2']");
    private By agencyCode = By.xpath("//input[@formcontrolname='cmpCode']");
    private By searchButton = By.xpath("//i[@class='icon icon-search']");
    private By errorMsg = By.xpath("//div[contains(text(),'Error')]");
    private By resultsFound = By.xpath("//span[contains(text(), 'Results Found')]");
    private By newBooking = By.xpath("//button[normalize-space()='New Booking']");
    private By overlay = By.xpath("//div[contains(@class,'ngx-spinner-overlay')]");

    // Utility: Wait for overlay to disappear
    public void waitForOverlayToDisappear() {
        try {
            if (driver.findElements(overlay).size() > 0) {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
                log.info("Overlay disappeared.");
            }
        } catch (Exception e) {
            log.warn("Overlay might still be visible or not found.");
        }
    }

    // Method to click on Home and Agency buttons
    public void agency() {
        try {
            WebElement home = wait.until(ExpectedConditions.visibilityOfElementLocated(homeButton));
            if (!home.getAttribute("class").contains("active")) {
                log.info("Clicking on Home button.");
                home.click();
            } else {
                log.info("Home button is already active.");
            }

            WebElement agency = wait.until(ExpectedConditions.visibilityOfElementLocated(agencyButton));
            if (!agency.getAttribute("class").contains("active")) {
                log.info("Clicking on Agency button.");
                agency.click();
            } else {
                log.info("Agency button is already active.");
            }
        } catch (Exception e) {
            errorLogger.error("Error occurred in agency method: " + e.getMessage());
            Assert.fail("Error in agency method: " + e.getMessage());
        }
    }

    // Method to click Agencies button
    public void agencies() {
        try {
            WebElement agencies = wait.until(ExpectedConditions.visibilityOfElementLocated(agenciesButton));
            if (!agencies.getAttribute("class").contains("active")) {
                log.info("Clicking on Agencies button.");
                agencies.click();
            } else {
                log.info("Agencies button is already active.");
            }
        } catch (Exception e) {
            errorLogger.error("Error occurred in agencies method: " + e.getMessage());
            Assert.fail("Error in agencies method: " + e.getMessage());
        }
    }

    // Method to search for an agent
    public void searchAgent(String agentCode) {
        try {
            waitForOverlayToDisappear();
            WebElement agencycode = wait.until(ExpectedConditions.elementToBeClickable(agencyCode));
            log.info("Clearing and entering agent code.");
            agencycode.clear();
            agencycode.sendKeys(agentCode);
            log.info("Entered agency code: " + agentCode);
        } catch (Exception e) {
            errorLogger.error("Error occurred in searchAgent method: " + e.getMessage());
            Assert.fail("Error in searchAgent: " + e.getMessage());
        }
    }

    // Method to perform a search
    public void search() {
        try {
            WebElement searchbtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            log.info("Clicking on Search button.");
            searchbtn.click();
            log.info("Search button clicked.");
        } catch (Exception e) {
            errorLogger.error("Error occurred in search method: " + e.getMessage());
            Assert.fail("Error in search method: " + e.getMessage());
        }
    }

    // Method for New Booking
    public void newBooking() {
        try {
            WebElement newbooking = wait.until(ExpectedConditions.visibilityOfElementLocated(newBooking));
            log.info("Clicking on New Booking button.");
            newbooking.click();
        } catch (Exception e) {
            errorLogger.error("Error occurred in newBooking method: " + e.getMessage());
            Assert.fail("Error in newBooking method: " + e.getMessage());
        }
    }

    // Utility: Click using JavaScript
    private void jsClick(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
            log.info("Clicked element using JavaScript.");
        } catch (Exception e) {
            errorLogger.error("JavaScript click failed: " + e.getMessage());
        }
    }
}
