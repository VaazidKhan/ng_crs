package pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseTest;

public class CruiseBooking extends BaseTest
{
	WebDriver driver;
    WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(NewBooking.class);
    
 // Constructor to initialize WebDriver and WebDriverWait
    public CruiseBooking(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }
    
    //Locators
    By voyageField = By.xpath("(//i[@class='icon icon-right-arrow m-l-2'])[1]");
    By packageField = By.xpath("(//li[@class='cabin-content ng-star-inserted'])[1]");
    By selectSuiteField = By.xpath("//button[@class='btn btn-primary ng-star-inserted']");
    
    public void voyage()
    {
    	try {
            log.info("Clicking on available cruise.");
            WebElement cruise = wait.until(ExpectedConditions.elementToBeClickable(voyageField));
            cruise.click();
            log.info("Cruise selected successfully.");
        } catch (Exception e) {
            log.error("Error in booking method: " + e.getMessage(), e);
            Assert.fail("Failed to click on new booking button: " + e.getMessage());
        }
    }
    
    public void packages() {
        try {
            log.info("Checking if any packages are existing");
            // Check if the package field is present
            List<WebElement> packageElements = driver.findElements(packageField);
            
            if (!packageElements.isEmpty()) {
                // Package found, select the package
                WebElement pac = packageElements.get(0);
                pac.click();
                log.info("Package selected successfully");
                Thread.sleep(2000); // Optional: consider using explicit waits instead
            }
            
            // Regardless of package presence, click the Select Suite button
            WebElement selectSuiteBtn = wait.until(ExpectedConditions.elementToBeClickable(selectSuiteField));
            selectSuiteBtn.click();
            log.info("Select Suite button clicked successfully");
            
        } catch (Exception e) {
            log.error("An error occurred while selecting package or suite: " + e.getMessage(), e);
            Assert.fail("Failed to select the package or selecting the suite: " + e.getMessage());
        }
    }


}
