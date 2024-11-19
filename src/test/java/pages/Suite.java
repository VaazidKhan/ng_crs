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

    private static final Logger log = LogManager.getLogger(Suite.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error");

    // Constructor
    public Suite(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }

    // Locators
    By suitesPipe = By.xpath("//li[@role='tab' and @aria-controls='k-tabstrip-tabpanel-2' and .//span[text()='Suite(s)']]");
    By tab = By.xpath("//div[@class='d-flex gap-3']/span");
    By spinner = By.xpath("//div[contains(@class, 'ngx-spinner-overlay')]");
    By selectSuiteField = By.xpath("//button[contains(text(),'Select Suite')]");
    By suitesField = By.xpath("//li[@class='cabin-content book-cabin-content hrlel ng-star-inserted']");
    By suiteAvailability = By.xpath("//span[@title='Available']");

    
    // Wait for spinner to disappear
    private void waitForSpinnerToDisappear() {
        try {
            log.info("Waiting for spinner to disappear.");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(spinner));
            log.info("Spinner disappeared.");
        } catch (Exception e) {
            log.warn("Spinner was not visible or timed out: " + e.getMessage());
        }
    }
    // Method to verify if "Suite" tab is selected
    public boolean isSuiteTabSelected() {
        try {
        	
        	waitForSpinnerToDisappear();
        	
            log.info("Verifying if the UI is in the 'Suite(s)' tab.");
            WebElement suitesTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(suitesPipe));
            WebElement suitesTabText = wait.until(ExpectedConditions.visibilityOfElementLocated(tab));

            String isSelected = suitesTabElement.getAttribute("aria-selected");
            String tabText = suitesTabElement.findElement(By.tagName("span")).getText();
            String text = suitesTabText.getText();

            if ("true".equals(isSelected) && text.equals(tabText)) {
                log.info("Suites tab is correctly selected and highlighted in the pipeline.");
                return true;
            } else {
                errorLogger.error("Suites tab is not selected. aria-selected: " + isSelected + ", Tab Text: " + tabText);
                return false;
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred while verifying the Suites tab selection.", e);
            return false;
        }
    }


    // Method to check and select available suites
    public void suites() {
        int retryCount = 0;
        boolean suiteSelected = false;

        while (retryCount < 3 && !suiteSelected) { // Retry up to 3 times
            try {
                log.info("Checking available suites.");
                waitForSpinnerToDisappear();

                // Locate available suites
                List<WebElement> availableSuites = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(suiteAvailability));

                if (!availableSuites.isEmpty()) {
                    for (WebElement suite : availableSuites) {
                        if (suite.isDisplayed() && suite.isEnabled()) {
                            suite.click();
                            log.info("Available suite selected successfully.");
                            suiteSelected = true;
                            break;
                        }
                    }
                } else {
                    log.warn("No available suites found.");
                }
            } catch (StaleElementReferenceException e) {
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

    // Method to click on the "Select Suite" button
    public void selectSuite() {
        try {
            log.info("Waiting for the Select Suite button to be clickable.");
            waitForSpinnerToDisappear();
            WebElement selectSuiteBtn = wait.until(ExpectedConditions.elementToBeClickable(selectSuiteField));
            selectSuiteBtn.click();
            log.info("Select Suite button clicked successfully.");
        } catch (Exception e) {
            errorLogger.error("An error occurred while clicking the 'Select Suite' button: " + e.getMessage(), e);
            Assert.fail("Failed to click on 'Select Suite' button: " + e.getMessage());
        }
    }
}
