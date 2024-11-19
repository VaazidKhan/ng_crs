package pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PaxDetails {
	WebDriver driver;
    WebDriverWait wait;

    private static final Logger log = LogManager.getLogger(Suite.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error");

    // Constructor
    public PaxDetails(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }
    
    // Locators
    By paxDetailsPipe = By.xpath("//li[@role='tab' and @aria-controls='k-tabstrip-tabpanel-4' and .//span[text()='Pax Details']]");
    By tab = By.xpath("//div[@class='d-flex gap-3']/span");
    By spinner = By.xpath("//div[contains(@class, 'ngx-spinner-overlay')]");
    By newProfileButton = By.xpath("(//button[contains(text(),'New Profile')])[1]");

    
    
    
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
    public boolean isPaxDetailsTabSelected() {
        try {
        	
        	waitForSpinnerToDisappear();
        	
            log.info("Verifying if the UI is in the 'Pax details' tab.");
            WebElement paxTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(paxDetailsPipe));
            WebElement paxTabText = wait.until(ExpectedConditions.visibilityOfElementLocated(tab));

            String isSelected = paxTabElement.getAttribute("aria-selected");
            String tabText = paxTabElement.findElement(By.tagName("span")).getText();
            String text = paxTabText.getText();

            if ("true".equals(isSelected) && text.equals(tabText)) {
                log.info("Pax Details tab is correctly selected and highlighted in the pipeline.");
                return true;
            } else {
                errorLogger.error("Pax Details tab is not selected. aria-selected: " + isSelected + ", Tab Text: " + tabText);
                return false;
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred while verifying the Pax Details tab selection.", e);
            return false;
        }
    }
    
    
    // Method to check and create pax
    public void newProfile() {
    	try {
        log.info("Clicking on New Profile button.");
        WebElement newProfileBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(newProfileButton));
        newProfileBtn.click();
        log.info("New Profile button clicked.");
    }catch(Exception e) {
    	errorLogger.error("Error selecting new profile button: " + e.getMessage(), e);
        Assert.fail("Failed to click on new profile button: " + e.getMessage());
    }
 }
}
