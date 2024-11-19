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

public class PreviewOptions {

    WebDriver driver;
    Wait<WebDriver> wait;

    private static final Logger log = LogManager.getLogger(PreviewOptions.class); // Updated logger
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs

    // Constructor to initialize WebDriver and FluentWait
    public PreviewOptions(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(30))
                        .pollingEvery(Duration.ofSeconds(5))
                        .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
    }

    // Locators
    By PREVIEW_PIPE = By.xpath("//li[@role='tab' and @aria-controls='k-tabstrip-tabpanel-3' and .//span[text()='Preview/Options']]");
    By TAB = By.xpath("//div[@class='d-flex gap-3']/span");
    By OPTIONS_FIELD = By.xpath("//div[@class='preview-item']");
    By PROCEED_TO_BOOKING_BUTTON = By.xpath("//button[@class='btn btn-primary text-nowrap']");

    // Method to verify if "Preview/Options" tab is selected
    public boolean isPreviewOptionsTabSelected() {
        try {
            log.info("Verifying if the UI is in the 'Preview/Options' tab.");

            WebElement previewTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(PREVIEW_PIPE));
            WebElement previewTabText = wait.until(ExpectedConditions.visibilityOfElementLocated(TAB));

            String isSelected = previewTabElement.getAttribute("aria-selected");
            String pipelineText = previewTabText.getText();
            String selectedText = previewTabElement.findElement(By.tagName("span")).getText();

            if ("true".equals(isSelected) && pipelineText.equals(selectedText)) {
                log.info("Preview/Options tab is correctly selected and highlighted in the pipeline.");
                return true;
            } else {
                errorLogger.error("Preview/Options tab is not selected. aria-selected: " + isSelected + ", Expected Text: " + selectedText);
                return false;
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred while verifying the Preview/Options tab selection.", e);
            return false;
        }
    }

    // Method to select an option/item
    public void options() {
        try {
            log.info("Attempting to locate and select an item/option.");
            List<WebElement> optionsElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(OPTIONS_FIELD));

            if (!optionsElements.isEmpty()) {
                WebElement availableOption = optionsElements.get(0);

                if (availableOption.isDisplayed() && availableOption.isEnabled()) {
                    availableOption.click();
                    log.info("Item/Option selected successfully.");
                } else {
                    log.warn("Item/Option found but it is not clickable.");
                }
            } else {
                log.info("No Item/Option available to select.");
            }
        } catch (Exception e) {
            errorLogger.error("Error selecting item/option: " + e.getMessage(), e);
            Assert.fail("Failed to select an item/option: " + e.getMessage());
        }
    }

    // Method to proceed to booking
    public void proceedToBooking() {
        try {
            log.info("Attempting to click on 'Proceed to Booking' button.");
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(PROCEED_TO_BOOKING_BUTTON));

            if (proceedButton != null && proceedButton.isDisplayed()) {
                proceedButton.click();
                log.info("Clicked on 'Proceed to Booking' button successfully.");
            } else {
                errorLogger.error("Proceed to Booking button is not clickable or visible.");
                Assert.fail("Proceed to Booking button is not clickable or visible.");
            }
        } catch (Exception e) {
            errorLogger.error("Failed to click 'Proceed to Booking' button: " + e.getMessage(), e);
            Assert.fail("Failed to click 'Proceed to Booking' button: " + e.getMessage());
        }
    }
}
