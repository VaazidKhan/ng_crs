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

import base.BaseTest;

public class CruiseBooking extends BaseTest {
    WebDriver driver;
    WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(CruiseBooking.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs


    // Constructor to initialize WebDriver and WebDriverWait
    public CruiseBooking(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }

    // Locators
    By searchButton = By.xpath("//i[@class='icon icon-search']");
    By voyageField = By.xpath("(//i[@class='icon icon-right-arrow m-l-2'])[1]");
    By packageField = By.xpath("//li[@class='cabin-content ng-star-inserted']");
    By selectSuiteField = By.xpath("//button[contains(text(),'Select Suite')]");
    //suites
    By suitesField = By.xpath("//li[@class='cabin-content book-cabin-content hrlel ng-star-inserted']");
    By suiteAvailability = By.xpath("//span[@title='Available']"); //list
    //preview/options
    By optionsField = By.xpath("//div[@class='preview-item']");
    By proccedToBookingField = By.xpath("//button[@class='btn btn-primary text-nowrap']");

    // Method to select a voyage
    public void voyage() {
        try {
        	log.info("Checking if there are any available cruises");
            WebElement search = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            search.click();
            log.info("Clicking on available cruise.");
            WebElement cruise = wait.until(ExpectedConditions.elementToBeClickable(voyageField));
            cruise.click();
            log.info("Cruise selected successfully.");
        } catch (Exception e) {
            errorLogger.error("Error in booking method: " + e.getMessage(), e);
            Assert.fail("Failed to click on new booking button: " + e.getMessage());
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
    
    public void options() {
    	
        boolean itemSelected = false;
        try {
    	//locate all items
        List<WebElement> optionsElement = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsField));
        if (!optionsElement.isEmpty()) {
            // Click the first available suite
            WebElement availableOptions = optionsElement.get(0);
            
            if (availableOptions.isDisplayed() && availableOptions.isEnabled()) {
                availableOptions.click();
                log.info("Item/Option selected successfully.");
                itemSelected = true; // Exit loop once successful
            } else {
                log.warn("Item/Option found but it is not clickable.");
            }
        } else {
            log.info("No Item/Option available, proceeding to proceed booking.");
            itemSelected = true; // Exit loop if no packages are found
        }
    	
    }catch (Exception e) {
    	errorLogger.error("Error selecting item/option: " + e.getMessage(), e);
        Assert.fail("Failed to select a item/option: " + e.getMessage());
    }
        
    }
    
 public void proceedToBooking() throws Exception {
	 Thread.sleep(2000);
	 WebElement ptb = wait.until(ExpectedConditions.elementToBeClickable(proccedToBookingField));
	 ptb.click();
	 log.info("Clicked on Proceed to booking button");
    	
    }
}
