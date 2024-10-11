package pages;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CentralSetUp {
    
    WebDriver driver;
    WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(CentralSetUp.class); // Correct logger for the class
    
    public CentralSetUp(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }
    
    // Locator for the CRS RCY menu field
    By csuMenuField = By.xpath("//span[contains(text(), 'CRS RCY')]");
    
    public void csu() {
        try {
            // Wait for the menu field to be clickable and then click
            wait.until(ExpectedConditions.elementToBeClickable(csuMenuField)).click();
            log.info("CRS RCY menu clicked");
            
            // Store the current window handle (main tab)
            String mainWindowHandle = driver.getWindowHandle();
            
            // Get all window handles
            Set<String> allWindowHandles = driver.getWindowHandles();
            
            // Switch to the new tab
            Iterator<String> iterator = allWindowHandles.iterator();
            while (iterator.hasNext()) {
                String handle = iterator.next();
                if (!mainWindowHandle.equals(handle)) {
                    // Switch to the new tab
                    driver.switchTo().window(handle);
                    log.info("Switched to new tab. The title of the new tab is: " + driver.getTitle());
                    
                    // Perform any further actions on the new tab if necessary
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while switching to the new tab: ", e);
        }
    }
}
	