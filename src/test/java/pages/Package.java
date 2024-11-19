package pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

public class Package {

    WebDriver driver;
    Wait<WebDriver> wait;

    private static final Logger log = LogManager.getLogger(Package.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs

    // Constructor to initialize WebDriver and FluentWait
    public Package(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(30))
                        .pollingEvery(Duration.ofSeconds(5))
                        .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
    }

    // Locators
    By packagePipe = By.xpath("//li[@role='tab' and @aria-controls='k-tabstrip-tabpanel-1' and .//span[text()='Package']]");
    By tab = By.xpath("//div[@class='d-flex gap-3']/span");
    By packageField = By.xpath("//li[@class='cabin-content ng-star-inserted']");
    By selectSuiteField = By.xpath("//button[contains(text(),'Select Suite')]");

    // Method to verify if "Package" tab is selected
    public boolean isPackageTabSelected() {
        try {
            log.info("Verifying if the UI is in the 'Package' tab.");

            WebElement packageTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(packagePipe));
            WebElement packageTabTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(tab));

            String isSelected = packageTabElement.getAttribute("aria-selected");
            String tabText = packageTabTextElement.getText();

            if ("true".equals(isSelected) && tabText.equals("Package")) {
                log.info("Package tab is correctly selected and highlighted in the pipeline.");
                return true;
            } else {
                log.warn("Package tab is not selected as expected. aria-selected: " + isSelected + ", Tab Text: " + tabText);
                return false;
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred while verifying the Package tab selection.", e);
            return false;
        }
    }

    // Method to select a package with retry logic
    public void packages() {
        int retryCount = 0;
        boolean packageSelected = false;

        while (retryCount < 3 && !packageSelected) {
            try {
                log.info("Attempting to select a package. Attempt: " + (retryCount + 1));

                List<WebElement> packageElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(packageField));

                if (!packageElements.isEmpty()) {
                    WebElement packageElement = packageElements.get(0);

                    if (packageElement.isDisplayed() && packageElement.isEnabled()) {
                        packageElement.click();
                        log.info("Package selected successfully.");
                        packageSelected = true;
                    } else {
                        log.warn("Package is found but not clickable.");
                    }
                } else {
                    log.warn("No packages available, skipping package selection.");
                    break;
                }
            } catch (StaleElementReferenceException e) {
                log.warn("Encountered StaleElementReferenceException. Retrying...");
                retryCount++;
            } catch (Exception e) {
                errorLogger.error("An error occurred while selecting a package: " + e.getMessage(), e);
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
            log.info("Waiting for the 'Select Suite' button to be clickable.");

            WebElement selectSuiteButton = wait.until(ExpectedConditions.elementToBeClickable(selectSuiteField));
            if (selectSuiteButton.isDisplayed() && selectSuiteButton.isEnabled()) {
                selectSuiteButton.click();
                log.info("Select Suite button clicked successfully.");
            } else {
                log.warn("Select Suite button is not clickable.");
                Assert.fail("Select Suite button is not in a clickable state.");
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred while selecting a suite: " + e.getMessage(), e);
            Assert.fail("Failed to click on 'Select Suite' button: " + e.getMessage());
        }
    }
}
