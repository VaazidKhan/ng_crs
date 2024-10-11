package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Explicit wait
    }

    By usernameField = By.xpath("//input[@id='Username']");
    By passwordField = By.xpath("//input[@id='Password']");
    By loginButton = By.xpath("//input[@value='SIGN IN']");

    public void login(String username, String password) throws Exception {
        logger.info("Waiting for username field to be visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        logger.info("Username field is now visible");

        // Wait for username field to be clickable and then send keys
        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).sendKeys(username);
        Thread.sleep(1000);
        logger.info("Username inserted successfully");

        // Wait for password field to be clickable and then send keys
        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).sendKeys(password);
        logger.info("Password inserted successfully");

        // Click on login button
        driver.findElement(loginButton).click();
        logger.info("Signed in successfully");

    }
}
