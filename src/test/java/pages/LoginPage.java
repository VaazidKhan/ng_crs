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

    By usernameField = By.xpath("//input[@id='Username']");
    By passwordField = By.xpath("//input[@id='Password']");
    By loginButton = By.xpath("//input[@value='SIGN IN']");
    By errorMsg = By.xpath("//div[@class='error-message']");
    By dashboardPage = By.xpath("//span[@class='dash-tab-name']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Explicit wait
    }

    public void login(String username, String password) throws Exception {
        logger.info("Waiting for username field to be visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        logger.info("Username field is now visible");

        // Wait for username field to be clickable and then send keys
        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).sendKeys(username);
        logger.info("Username inserted successfully");

        // Wait for password field to be clickable and then send keys
        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).sendKeys(password);
        logger.info("Password inserted successfully");

        // Click on login button
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        logger.info("Login button clicked");

        // Wait for redirection or dashboard page after successful login
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardPage));
            logger.info("Signed in successfully, redirected to dashboard.");
        } catch (Exception e) {
            // If no dashboard is loaded, check if an error message is displayed
            if (isElementPresent(errorMsg)) {
                logger.error("Login failed: Error message displayed");
                throw new Exception("Login failed due to incorrect credentials.");
            } else {
                logger.error("Login failed: Unknown issue, no redirection or error message.");
                throw new Exception("Login failed due to an unknown issue.");
            }
        }
    }

    // Utility method to check if an element is present
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
