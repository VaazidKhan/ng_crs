package pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MenuIcon {
	
	WebDriver driver;
	WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(MenuIcon.class);
    
    public MenuIcon(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }
    
    By menuiconField = By.xpath("//i[@class='menu-icon']");
    
    public void menu()
    {
    	log.info("Waiting for menu icon to appear");
    	wait.until(ExpectedConditions.elementToBeClickable(menuiconField)).click();
    	log.info("Menu icon clicked");
    }
    
    
    

	

}
