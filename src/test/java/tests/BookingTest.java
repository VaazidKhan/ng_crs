package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.Agency;
import pages.CentralSetUp;
import pages.CruiseBooking;
import pages.LoginPage;
import pages.MenuIcon;
import pages.NewBooking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookingTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @Test
    public void login() {
        LoginPage loginPage = new LoginPage(driver);

        // Log in to the application
        try {
            log.info("Attempting to log in with provided credentials.");
            loginPage.login(config.getProperty("username"), config.getProperty("password"));
            log.info("Login successful.");
        } catch (Exception e) {
            log.error("Login failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at login: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "login")
    public void menu() {
        MenuIcon menuicon = new MenuIcon(driver);

        // Click on menu icon
        try {
            Thread.sleep(4000);
            menuicon.menu();
            log.info("Clicked on menu icon.");
        } catch (Exception e) {
            log.error("Menu icon interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at Menu Icon: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "menu")
    public void csu() {
        CentralSetUp centralsetup = new CentralSetUp(driver);

        // Click on Central Set Up (CRS RCY)
        try {
            Thread.sleep(4000);
            centralsetup.csu();
            log.info("Clicked on 'CRS RCY' to open a new tab.");
            log.info("Switched to the new tab successfully.");
        } catch (Exception e) {
            log.error("Central Set Up interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at Central Set Up: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "csu")
    public void agent() {
        Agency agency = new Agency(driver);

        try {
            Thread.sleep(3000);
            agency.agency();
            log.info("Clicked on Agency option.");
        } catch (Exception e) {
            log.error("Agency interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at agency button: " + e.getMessage());
        }

        try {
            Thread.sleep(2000);
            agency.agent();
            log.info("Agencies listed.");
        } catch (Exception e) {
            log.error("Agency list interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at agency list: " + e.getMessage());
        }

        try {
            Thread.sleep(2000);
            agency.selectAgent(config.getProperty("agent"));
            log.info("Agent selected.");
        } catch (Exception e) {
            log.error("Agent selection interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at agent selection: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "agent")
    public void newBooking() {
        NewBooking newbooking = new NewBooking(driver);

        try {
            newbooking.booking();
            log.info("Clicked on New Booking.");
          //  log.info("Clicked on Search Button.");
        } catch (Exception e) {
            log.error("New booking button interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at new booking: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "newBooking")
    public void cruises() throws Exception {
        CruiseBooking cruisebooking = new CruiseBooking(driver);

        try {
            cruisebooking.voyage();
            log.info("Random Cruise selected.");
        } catch (Exception e) {
            log.error("Cruise selection interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at CruiseBooking: " + e.getMessage());
        }

        try {
            cruisebooking.packages();
            log.info("Random Package or suite selected.");
        } catch (Exception e) {
            log.error("Package selection interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at package selection: " + e.getMessage());
        }
        Thread.sleep(2000);
        try {
            cruisebooking.selectSuite();
            log.info("Select Suite clicked.");
        } catch (Exception e) {
            log.error("Suite button interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at select suite button in package: " + e.getMessage());
        }
        
        Thread.sleep(4000);
        try {
            cruisebooking.suites();
            log.info("Suite Selected.");
        } catch (Exception e) {
            log.error("Suite selection interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at suite selection in sutes: " + e.getMessage());
        }
        
        Thread.sleep(2000);
        try {
            cruisebooking.selectSuite();
            log.info("Select Suite clicked.");
        } catch (Exception e) {
            log.error("Suite button interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at select suite button in package: " + e.getMessage());
        }
        
        Thread.sleep(2000);
        try {
            cruisebooking.options();
            log.info("Item/Option selected.");
        } catch (Exception e) {
            log.error("Item/Option interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at selecting item in options: " + e.getMessage());
        }
        
        try {
            cruisebooking.proceedToBooking();
            log.info("Proceed to Booking button selected.");
        } catch (Exception e) {
            log.error("Proceed to Booking button interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at Proceed to Booking: " + e.getMessage());
        }
    }
}
