package pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CentralSetUp {
	
	WebDriver driver;
	WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(MenuIcon.class);
    
    public CentralSetUp(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }
    
	By csuMenuField = By.xpath("//span[contains(text(), 'CRS RCY')]");
	public void csu() {
		wait.until(ExpectedConditions.elementToBeClickable(csuMenuField)).click();
    	log.info("Menu icon clicked");
		
	}

}
