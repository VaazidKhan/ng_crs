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
	    By packagePipe = By.xpath("//li[@role='tab' and @aria-controls='k-tabstrip-tabpanel-1' and .//span[text()='Package']]");
	    By tab = By.xpath("//div[@class='d-flex gap-3']/span");
	    By packageField = By.xpath("//li[@class='cabin-content ng-star-inserted']");
	    By selectSuiteField = By.xpath("//button[contains(text(),'Select Suite')]");
	    
	    
	 // Method to verify if "Package" tab is selected
	    public boolean isPackageTabSelected() {
	        try {
	            log.info("Verifying if the UI is in the 'Package' tab.");

	            // Find the "Voyage" tab element
	            WebElement packageTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(packagePipe));
	            WebElement packageTabText = wait.until(ExpectedConditions.visibilityOfElementLocated(tab));

	            // Check if the 'aria-selected' attribute is set to true
	            String isSelected = packageTabElement.getAttribute("aria-selected");
	            String tabText = packageTabElement.findElement(By.tagName("span")).getText();
	            String text = packageTabText.findElement(tab).getText();

	            if ("true".equals(isSelected) && text.equals(tabText)) {
	                log.info("Package tab is correctly selected and highlighted in the pipeline.");
	                return true;
	            } else {
	                errorLogger.error("Package tab is not selected as expected. aria-selected: " + isSelected + ", Tab Text: " + tabText);
	                return false;
	            }
	        } catch (Exception e) {
	            errorLogger.error("An error occurred while verifying the Package tab selection.", e);
	            return false;
	        }
	    }
	    
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
    

	 // Method to select a suite
   public void selectSuite() {
       try {
           log.info("Waiting for the Select Suite button to be clickable.");
           WebElement selectSuiteBtn = wait.until(ExpectedConditions.elementToBeClickable(selectSuiteField));
               selectSuiteBtn.click();
               log.info("Select Suite button clicked successfully.");
           
       } catch (Exception e) {
       	errorLogger.error("An error occurred while suite selecting : " + e.getMessage(), e);
           Assert.fail("Failed to click on select suite button: " + e.getMessage());
       }
   }
}
