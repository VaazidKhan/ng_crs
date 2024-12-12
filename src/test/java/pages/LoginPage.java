package pages;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

public class LoginPage {

    WebDriver driver;
    Wait<WebDriver> wait;
    private static final Logger infoLogger = LogManager.getLogger(LoginPage.class); // For INFO logs
    private static final Logger errorLogger = LogManager.getLogger(LoginPage.class); // For ERROR logs

    By usernameField = By.xpath("//input[@id='Username']");
    By passwordField = By.xpath("//input[@id='Password']");
    By loginButton = By.xpath("//input[@value='SIGN IN']");
    By errorMsg = By.xpath("//div[@class='error-message']");
    By dashboardPage = By.xpath("//span[@class='dash-tab-name']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(60))
                        .pollingEvery(Duration.ofSeconds(5))
                        .ignoring(Exception.class);
    }

    public void login(String username, String password) throws Exception {
        infoLogger.info("Waiting for username field to be visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        infoLogger.info("Username field is now visible");

        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).sendKeys(username);
        infoLogger.info("Username inserted successfully");

        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).sendKeys(password);
        infoLogger.info("Password inserted successfully");

        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        infoLogger.info("Login button clicked");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardPage));
            infoLogger.info("Signed in successfully, redirected to dashboard.");
        } catch (Exception e) {
            if (isElementPresent(errorMsg)) {
                errorLogger.error("Login failed: Error message displayed");
                Assert.fail("Login failed: Incorrect credentials.");
            } else {
                errorLogger.error("Login failed: Unknown issue, no redirection or error message.");
                Assert.fail("Login failed: Unknown issue.");
            }
        }
    }


    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

