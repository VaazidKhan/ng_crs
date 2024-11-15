package pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseTest;

public class Agency extends BaseTest {
    WebDriver driver;
    WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(Agency.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs

    // Constructor to initialize WebDriver and WebDriverWait
    public Agency(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }

    // Locators for various elements
    By homeButton = By.xpath("(//li[@title='Home'])");
    By agencyButton = By.xpath("//button[normalize-space()='Agency']");
    By agenciesButton = By.xpath("//li[@class='active']//div[@class='d-flex align-items-center flex-column tab-link gap-2']");
    By agencyCode = By.xpath("//input[@formcontrolname='cmpCode']");
    By searchButton = By.xpath("//i[@class='icon icon-search']");
    By errorMsg = By.xpath("//div[contains(text(),'Error')]");
    By resultsFound = By.xpath("//span[contains(text(), 'Results Found')]");
    By newBooking = By.xpath("//button[normalize-space()='New Booking']");
    
    By alert = By.xpath("//h3[contains(text(),'Warning')]");
    By yesButton = By.xpath("//button[contains(text(),'Yes')]");
    
    By overlay = By.xpath("//div[contains(@class,'ngx-spinner-overlay')]");

    public void waitForOverlayToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
            log.info("Overlay disappeared.");
        } catch (Exception e) {
            log.warn("Overlay might still be visible or not found.");
        }
    }
    //Method to click on Home field and Agency
    public void agency() {
        try {
            // Wait for the home button to be visible
            WebElement home = wait.until(ExpectedConditions.visibilityOfElementLocated(homeButton));
            // Check if the home button is already clicked
            if (home.isSelected() || home.getAttribute("class").contains("active")) {
                log.info("Home button is already clicked.");
                
                // Click on the agency button
                WebElement agency = wait.until(ExpectedConditions.visibilityOfElementLocated(agencyButton));
             // Check if the agency button is already clicked
                if (agency.isSelected() || agency.getAttribute("class").contains("active")) {
                    log.info("Agency button is already clicked.");
                }
            }         
            else {
                log.info("Home button is not clicked. Clicking on Home button.");
                // Click the home button
                home.click();
                // Click on the agency button
                WebElement agency = wait.until(ExpectedConditions.visibilityOfElementLocated(agencyButton));
                agency.click();
                log.info("Clicked on Agency button.");
            }
        } catch (Exception e) {
            errorLogger.error("Error occurred in the agency method: " + e.getMessage());
        }
    }
    
 //method to click agencies button
    public void agencies() {
    	try {
            // Wait for the agencies button to be visible
            WebElement agencies = wait.until(ExpectedConditions.visibilityOfElementLocated(agenciesButton));
            //clicking on agencies
            agencies.click();
    	}catch(Exception e) {
            errorLogger.error("Error occurred in the agencies method: " + e.getMessage());
    	}
    }
    
  //method to search the agent
    public void searchAgent(String agentCode) {
    	try {
    		// Wait for the agency code button to be visible
            WebElement agencycode = wait.until(ExpectedConditions.elementToBeClickable(agencyCode));
            log.info("Waiting for agency code field to be clickable");
            agencycode.clear();
            Thread.sleep(2000);
            agencycode.sendKeys(agentCode);
            WebElement searchbtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            log.info("Waiting for search button to be clickable");
            searchbtn.click();
    	}catch (Exception e) {
            errorLogger.error("Error occurred in the search agent method: " + e.getMessage());
    	}
    }
    
   //method for new booking
    public void newBooking() {
    	
    	try {
            // Wait for the new booking button to be visible
            WebElement newbooking = wait.until(ExpectedConditions.visibilityOfElementLocated(newBooking));
            //clicking on new booking button
            newbooking.click();
    	}catch(Exception e) {
            errorLogger.error("Error occurred in the new booking method: " + e.getMessage());
    	}
    }
}