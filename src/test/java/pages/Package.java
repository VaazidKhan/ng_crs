package pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Package {
	 WebDriver driver;
	    WebDriverWait wait;

	    private static final Logger log = LogManager.getLogger(Package.class);
	    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs

	    // Constructor to initialize WebDriver and WebDriverWait
	    public Package(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
	    }

	    By packageField = By.xpath("//li[@class='cabin-content ng-star-inserted']");

	    
	// Method to select a package and suite with retry for stale element
    public void packages() {
    	int retryCount = 0;
        boolean packageSelected = false;
        
       while (retryCount < 3 && !packageSelected) { // Retry up to 3 times
            try {
                log.info("Checking if any packages are available.");
                
                // Retry locating the packageField if the page gets refreshed or updated
                List<WebElement> packageElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(packageField));

                if (!packageElements.isEmpty()) {
                    WebElement pac = packageElements.get(0);

                    // Re-locate and ensure package is visible and clickable
                    if (pac.isDisplayed() && pac.isEnabled()) {
                        pac.click();
                        log.info("Package selected successfully.");
                        packageSelected = true; // Exit loop once successful
                    } else {
                        log.warn("Package found but it is not clickable.");
                    }
                } else {
                    log.info("No packages available, proceeding to select suite.");
                    packageSelected = true; // Exit loop if no packages are found
                }
            } catch (StaleElementReferenceException staleException) {
                log.warn("Encountered StaleElementReferenceException, retrying... Attempt " + (retryCount + 1));
                retryCount++;
            } catch (Exception e) {
            	errorLogger.error("Error selecting package: " + e.getMessage(), e);
                Assert.fail("Failed to select a package: " + e.getMessage());
            }
        }

        if (!packageSelected) {
            Assert.fail("Failed to select a package after multiple attempts.");
        }
    }
}
