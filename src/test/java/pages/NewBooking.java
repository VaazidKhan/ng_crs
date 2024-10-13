package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseTest;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewBooking extends BaseTest {
	
	  WebDriver driver;
	    WebDriverWait wait;
	    private static final Logger log = LogManager.getLogger(NewBooking.class);
	    
	 // Constructor to initialize WebDriver and WebDriverWait
	    public NewBooking(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
	    }
	    
	  //Locators
	    By newbuttonField = By.xpath("//button[@class='btn btn-primary pe-auto']");
	    By searchbuttonField = By.xpath("//button[@class='btn btn-primary icon-only']");
    
	    public void booking() throws Exception {
	        // Clicking the 'New Booking' button
	        try {
	            log.info("Clicking on New Booking.");
	            WebElement newButton = wait.until(ExpectedConditions.elementToBeClickable(newbuttonField));
	            newButton.click();
	            log.info("New booking button clicked successfully.");
	        } catch (Exception e) {
	            log.error("Error in booking method: " + e.getMessage(), e);
	            Assert.fail("Failed to click on new booking button: " + e.getMessage());
	        }
	        
	        Thread.sleep(5000);
	        // Clicking the 'Search' button
	        try {
	            log.info("Waiting for Search button to become clickable.");
	            
	            // Add more checks before clicking the search button
	            WebElement searchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(searchbuttonField)); // Ensure the element is visible
	            wait.until(ExpectedConditions.elementToBeClickable(searchbuttonField)); // Ensure it is clickable
	            searchButton.click();
	            
	            log.info("Search button clicked successfully.");
	        } catch (Exception e) {
	            log.error("Error in booking method at search button: " + e.getMessage(), e);
	            
	            // Debugging additional details
	            log.error("Additional Debug Info: Page Title: " + driver.getTitle());
	            log.error("Additional Debug Info: Current URL: " + driver.getCurrentUrl());
	            
	            Assert.fail("Failed to click on search button: " + e.getMessage());
	        }
	    }


}
