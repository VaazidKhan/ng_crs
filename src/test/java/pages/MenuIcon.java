package pages;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class MenuIcon {

    WebDriver driver;
    Wait<WebDriver> wait;
    private static final Logger log = LogManager.getLogger(MenuIcon.class);

    public MenuIcon(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(30))
                        .pollingEvery(Duration.ofSeconds(5))
                        .ignoring(Exception.class);
    }

    By menuiconField = By.xpath("//i[@class='menu-icon']");

    public void menu() {
        log.info("Waiting for menu icon to appear");
        wait.until(ExpectedConditions.elementToBeClickable(menuiconField)).click();
        log.info("Menu icon clicked");
    }
}
