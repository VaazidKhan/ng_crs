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

public class PreviewOptions {
	
	
	WebDriver driver;
    WebDriverWait wait;

    private static final Logger log = LogManager.getLogger(Package.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs

    // Constructor to initialize WebDriver and WebDriverWait
    public PreviewOptions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }
    
    By previewPipe = By.xpath("//li[@role='tab' and @aria-controls='k-tabstrip-tabpanel-3' and .//span[text()='Preview/Options']]");
    By tab = By.xpath("//div[@class='d-flex gap-3']/span");
    By optionsField = By.xpath("//div[@class='preview-item']");
    By proccedToBookingField = By.xpath("//button[@class='btn btn-primary text-nowrap']");
    
    
 // Method to verify if "Voyage" tab is selected
    public boolean isPreviewOptionsTabSelected() {
        try {
            log.info("Verifying if the UI is in the 'Suite(s)' tab.");

            // Find the "Voyage" tab element
            WebElement previewTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(previewPipe));
            WebElement previewTabText = wait.until(ExpectedConditions.visibilityOfElementLocated(tab));
            // Check if the 'aria-selected' attribute is set to true
            String isSelected = previewTabElement.getAttribute("aria-selected");
            String tabText = previewTabElement.findElement(By.tagName("span")).getText();
            String text = previewTabText.findElement(tab).getText();

            if ("true".equals(isSelected) && text.equals(tabText)) {
                log.info("Preview/Options tab is correctly selected and highlighted in the pipeline.");
                return true;
            } else {
                errorLogger.error("Preview/Options tab is not selected as expected. aria-selected: " + isSelected + ", Tab Text: " + tabText);
                return false;
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred while verifying the Preview/Options tab selection.", e);
            return false;
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
	 try {
	 WebElement ptb = wait.until(ExpectedConditions.elementToBeClickable(proccedToBookingField));
	 ptb.click();
	 log.info("Clicked on Proceed to booking button");
    	
    }catch(Exception e) {
    	errorLogger.error("Proceed booking button interaction failed at: " + e.getMessage(), e);
    	Assert.fail("Failed to click proceed booking :"+e.getMessage());
    }
    }
	

}
