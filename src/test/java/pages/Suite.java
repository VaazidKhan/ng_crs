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

public class Suite {
	 WebDriver driver;
	    WebDriverWait wait;

	    private static final Logger log = LogManager.getLogger(Package.class);
	    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs

	    // Constructor to initialize WebDriver and WebDriverWait
	    public Suite(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
	    }
	    
	    By suitesPipe = By.xpath("//li[@role='tab' and @aria-controls='k-tabstrip-tabpanel-2' and .//span[text()='Suite(s)']]");
	    By tab = By.xpath("//div[@class='d-flex gap-3']/span");
	    By selectSuiteField = By.xpath("//button[contains(text(),'Select Suite')]");
	    By suitesField = By.xpath("//li[@class='cabin-content book-cabin-content hrlel ng-star-inserted']");
	    By suiteAvailability = By.xpath("//span[@title='Available']");
	    
	    // Method to verify if "Voyage" tab is selected
	    public boolean isSuiteTabSelected() {
	        try {
	            log.info("Verifying if the UI is in the 'Suite(s)' tab.");

	            // Find the "Voyage" tab element
	            WebElement suitesTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(suitesPipe));
	            WebElement suitesTabText = wait.until(ExpectedConditions.visibilityOfElementLocated(tab));
	            // Check if the 'aria-selected' attribute is set to true
	            String isSelected = suitesTabElement.getAttribute("aria-selected");
	            String tabText = suitesTabElement.findElement(By.tagName("span")).getText();
	            String text = suitesTabText.findElement(tab).getText();

	            if ("true".equals(isSelected) && text.equals(tabText)) {
	                log.info("Suites tab is correctly selected and highlighted in the pipeline.");
	                return true;
	            } else {
	                errorLogger.error("Suites tab is not selected as expected. aria-selected: " + isSelected + ", Tab Text: " + tabText);
	                return false;
	            }
	        } catch (Exception e) {
	            errorLogger.error("An error occurred while verifying the Suites tab selection.", e);
	            return false;
	        }
	    }
	    
	    public void suites() {
	        int retryCount = 0;
	        boolean suiteSelected = false;

	        while (retryCount < 3 && !suiteSelected) { // Retry up to 3 times
	            try {
	                log.info("Checking available suites.");
	                
	                // Locate all suites
	                List<WebElement> suiteElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(suitesField));

	                // Locate only available suites
	                List<WebElement> availableSuites = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(suiteAvailability));

	                if (!suiteElements.isEmpty() && !availableSuites.isEmpty()) {
	                    // Click the first available suite
	                    WebElement firstAvailableSuite = availableSuites.get(0); // Only the first available suite
	                    if (firstAvailableSuite.isDisplayed() && firstAvailableSuite.isEnabled()) {
	                        firstAvailableSuite.click();
	                        log.info("First available suite selected successfully.");
	                        suiteSelected = true; // Exit loop after selecting one suite
	                    } else {
	                        log.warn("Available suite found but not clickable.");
	                    }
	                } else {
	                    log.warn("No available suites found.");
	                    suiteSelected = true; // Exit loop if no available suites
	                }
	            } catch (StaleElementReferenceException staleException) {
	                log.warn("Encountered StaleElementReferenceException, retrying... Attempt " + (retryCount + 1));
	                retryCount++;
	            } catch (Exception e) {
	            	errorLogger.error("Error selecting suite: " + e.getMessage(), e);
	                Assert.fail("Failed to select a suite: " + e.getMessage());
	            }
	        }

	        if (!suiteSelected) {
	            Assert.fail("Failed to select an available suite after multiple attempts.");
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
