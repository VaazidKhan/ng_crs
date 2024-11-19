package pages;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class CentralSetUp {

    WebDriver driver;
    Wait<WebDriver> wait;
    private static final Logger log = LogManager.getLogger(CentralSetUp.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error");

    public CentralSetUp(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(30))
                        .pollingEvery(Duration.ofSeconds(5))
                        .ignoring(Exception.class);
    }

    By csuMenuField = By.xpath("//span[contains(text(), 'CRS RCY')]");

    public void csu() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(csuMenuField)).click();
            log.info("CRS RCY menu clicked");

            String mainWindowHandle = driver.getWindowHandle();
            Set<String> allWindowHandles = driver.getWindowHandles();

            Iterator<String> iterator = allWindowHandles.iterator();
            while (iterator.hasNext()) {
                String handle = iterator.next();
                if (!mainWindowHandle.equals(handle)) {
                    driver.switchTo().window(handle);
                    log.info("Switched to new tab. The title of the new tab is: " + driver.getTitle());
                }
            }
        } catch (Exception e) {
            errorLogger.error("Error occurred while switching to the new tab: ", e);
        }
    }
}
