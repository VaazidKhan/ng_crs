package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pages.*;
import pages.Package;

public class BookingTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    private static final Logger eLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs

    // Helper method for error handling
    private void handleError(String action, Exception e, SoftAssert softAssert) {
        eLogger.error("{} failed: {}", action, e.getMessage(), e);
        softAssert.fail(action + " failed: " + e.getMessage());
    }
    SoftAssert softAssert = new SoftAssert();

    // Login Test
    @Test
    public void login() {
        LoginPage loginPage = new LoginPage(driver);

        try {
            log.info("Attempting to log in with provided credentials.");
            loginPage.login(config.getProperty("username"), config.getProperty("password"));
            log.info("Login successful.");
        } catch (Exception e) {
            handleError("Login", e, softAssert);
        }
    }

    // Menu Test
    @Test(dependsOnMethods = "login")
    public void menu() {
        MenuIcon menuIcon = new MenuIcon(driver);

        try {
            log.info("Clicking on the menu icon.");
            menuIcon.menu();
            log.info("Menu icon clicked successfully.");
        } catch (Exception e) {
            handleError("Menu Icon", e, softAssert);

        }
    }

    // Central Setup Test
    @Test(dependsOnMethods = "menu")
    public void csu() {
        CentralSetUp centralSetup = new CentralSetUp(driver);

        try {
            log.info("Interacting with Central Set Up.");
            centralSetup.csu();
            log.info("Central Set Up interaction completed successfully.");
        } catch (Exception e) {
            handleError("CSU", e, softAssert);

        }
    }

    // Agent Test
    @Test(dependsOnMethods = "csu")
    public void agent() {
        Agency agency = new Agency(driver);


        try {
            log.info("Waiting for the overlay to disappear.");
            agency.waitForOverlayToDisappear();
            log.info("Overlay disappeared successfully.");
        } catch (Exception e) {
            handleError("Overlay wait", e, softAssert);
        }

        try {
            log.info("Navigating to the Agency section.");
            agency.agency();
            log.info("Agency section navigation successful.");
        } catch (Exception e) {
            handleError("Agency section navigation", e, softAssert);
        }
        
        try {
            log.info("Navigating to the Agencies section.");
            agency.agencies();
            log.info("Agencies section navigation successful.");
        } catch (Exception e) {
            handleError("Agencies section navigation", e, softAssert);
        }
        try {
            log.info("Passing Agency code.");
            agency.searchAgent(config.getProperty("agencyCode"));
            log.info("Agency code passed successfully.");
        } catch (Exception e) {
            handleError("Agency code", e, softAssert);
        }
        
        try {
            log.info("Passing Agency code.");
            agency.search();
            log.info("Search button clicked successfully.");
        } catch (Exception e) {
            handleError("Search button", e, softAssert);
        }

        try {
            log.info("Clicking on the New Booking button.");
            agency.newBooking();
            log.info("New Booking button clicked successfully.");
        } catch (Exception e) {
            handleError("New Booking button click", e, softAssert);
        }
    }
    // Voyage Tab Test
    @Test(dependsOnMethods = "agent")
    public void voyageTab() {
        Voyage voyage = new Voyage(driver);

        try {
            log.info("Verifying if Voyage tab is selected.");
            Assert.assertTrue(voyage.isVoyageTabSelected(), "Voyage tab not selected.");
            log.info("Voyage tab is selected.");
        } catch (Exception e) {
            handleError("Voyage tab", e, softAssert);
        }

        try {
            log.info("Searching for available cruises.");
            voyage.voyageTab();
            log.info("Search for cruises initiated.");
        } catch (Exception e) {
            handleError("Cruise Seacrh", e, softAssert);
        }

        try {
            log.info("Selecting a random available cruise.");
            voyage.availableCruise();
            log.info("Cruise selected.");
        } catch (Exception e) {
            handleError("Available cruises", e, softAssert);
        }
    }

    // Package Tab Test
    @Test(dependsOnMethods = "voyageTab")
    public void packageTab() {
        Package packageVoy = new Package(driver);

        try {
            log.info("Verifying if the Package tab is selected.");
            Assert.assertTrue(packageVoy.isPackageTabSelected(), "Package tab not selected.");
            log.info("Package tab is selected.");
        } catch (Exception e) {
            handleError("Packgae Tab", e, softAssert);

        }

        try {
            log.info("Selecting a package.");
            packageVoy.packages();
            log.info("Package selected successfully.");
        } catch (Exception e) {
            handleError("Available packages", e, softAssert);
        }

        try {
            log.info("Clicking on Select Suite button.");
            packageVoy.selectSuite();
            log.info("Select Suite button clicked.");
        } catch (Exception e) {
            handleError("Select Suite button", e, softAssert);
        }
    }

    // Suite Tab Test
    @Test(dependsOnMethods = "packageTab")
    public void suiteTab() {
        Suite suite = new Suite(driver);

        try {
            log.info("Verifying if the Suite tab is selected.");
            Assert.assertTrue(suite.isSuiteTabSelected(), "Suite tab not selected.");
            log.info("Suite tab is selected.");
        } catch (Exception e) {
            handleError("Suite Tab", e, softAssert);
        }

        try {
            log.info("Selecting a suite.");
            suite.suites();
            log.info("Suite selected successfully.");
        } catch (Exception e) {
            handleError("Avaiable suites ", e, softAssert);
        }

        try {
            log.info("Clicking on Select Suite button.");
            suite.selectSuite();
            log.info("Select Suite button clicked.");
        } catch (Exception e) {
            handleError("Select suite button", e, softAssert);
        }
    }

    // Preview Tab Test
    @Test(dependsOnMethods = "suiteTab")
    public void previewTab() {
        PreviewOptions options = new PreviewOptions(driver);

        try {
            log.info("Verifying if the Preview/Options tab is selected.");
            Assert.assertTrue(options.isPreviewOptionsTabSelected(), "Preview/Options tab not selected.");
            log.info("Preview/Options tab is selected.");
        } catch (Exception e) {
            handleError("Preview Tab", e, softAssert);
        }

        try {
            log.info("Interacting with options.");
            options.options();
            log.info("Options selected successfully.");
        } catch (Exception e) {
            handleError("Avaiable options", e, softAssert);
        }

        try {
            log.info("Clicking on Proceed to Booking button.");
            options.proceedToBooking();
            log.info("Proceed to Booking button clicked.");
        } catch (Exception e) {
            handleError("Proceed to booking button", e, softAssert);
        }
    }
}
