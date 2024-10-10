package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class BookingTest extends BaseTest {

    @Test
    public void loginAndBook() {
        // Create Page Objects
        LoginPage loginPage = new LoginPage(driver);

        // Log in to the application
        try {
            log.info("Attempting to log in with provided credentials.");
            loginPage.login(config.getProperty("username"), config.getProperty("password"));
            log.info("Login successful.");
            // ExtentReportManager handles report, no need to call test.pass()

            // You can add booking steps here
            // log.info("Performing booking.");
            // bookingPage.makeBooking();
            // test.pass("Booking completed.");
        } catch (Exception e) {
            log.error("Login failed at: " + e.getMessage());
            Assert.fail("Test failed at login: " + e.getMessage());
        }
    }
}
