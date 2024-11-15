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
    private static final Logger eLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs


    @Test
    public void login() {
        LoginPage loginPage = new LoginPage(driver);

        try {
            log.info("Attempting to log in with provided credentials.");
            loginPage.login(config.getProperty("username"), config.getProperty("password"));
            log.info("Login successful.");
        } catch (Exception e) {
            eLogger.error("Login failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at login: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "login")
    public void menu() {
        MenuIcon menuicon = new MenuIcon(driver);

        try {
            Thread.sleep(4000); // Better replaced with explicit waits
            menuicon.menu();
            log.info("Clicked on menu icon.");
        } catch (Exception e) {
        	eLogger.error("Menu icon interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at Menu Icon: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "menu")
    public void csu() {
        CentralSetUp centralsetup = new CentralSetUp(driver);

        // Click on Central Set Up (CRS RCY)
        try {
            Thread.sleep(4000); // Better replaced with explicit waits
            centralsetup.csu();
            log.info("Clicked on 'CRS RCY' to open a new tab.");
            log.info("Switched to the new tab successfully.");
        } catch (Exception e) {
        	eLogger.error("Central Set Up interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at Central Set Up: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "csu")
    public void agent() {
        Agency agency = new Agency(driver);
        
        try {
        	agency.waitForOverlayToDisappear();
        	log.info("Waiting for the overlay to end");
        }catch(Exception e) {
        	eLogger.error("Overlay spinner is problematic at:"+e.getMessage(),e);
        	Assert.fail("Test failed at overlaymethod: "+e.getMessage());
        }
        
        try {
            agency.agency();
            log.info("Clicked on Agency option.");
        } catch (Exception e) {
        	eLogger.error("Agency interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at agency button: " + e.getMessage());
        }
        
        try {
            agency.agencies();
            log.info("Clicked on agencies button.");
        } catch (Exception e) {
        	eLogger.error("Agencies interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at agencies button: " + e.getMessage());
        }
        
        try {
            log.info("Attempting to send agency code.");
            agency.searchAgent(config.getProperty("agencyCode"));
            log.info("Clicked on agency code field.");
        } catch (Exception e) {
        	eLogger.error("Agency code field interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at agency code field: " + e.getMessage());
        }
        
        try {
            agency.newBooking();
            log.info("Clicked on new booking button.");
        } catch (Exception e) {
        	eLogger.error("New Booking interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at new booking button: " + e.getMessage());
        }


    }

    @Test(dependsOnMethods = "agent")
    public void newBooking() {
        NewBooking newbooking = new NewBooking(driver);

        try {
            newbooking.booking();
            log.info("Clicked on New Booking.");
        } catch (Exception e) {
        	eLogger.error("New booking button interaction failed at: " + e.getMessage(), e);
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
        	eLogger.error("Cruise selection interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at CruiseBooking: " + e.getMessage());
        }

        try {
            cruisebooking.packages();
            log.info("Random Package or suite selected.");
        } catch (Exception e) {
        	eLogger.error("Package selection interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at package selection: " + e.getMessage());
        }

        Thread.sleep(2000); // Better replaced with explicit waits
        try {
            cruisebooking.selectSuite();
            log.info("Select Suite clicked.");
        } catch (Exception e) {
        	eLogger.error("Suite button interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at select suite button in package: " + e.getMessage());
        }

        Thread.sleep(4000); // Better replaced with explicit waits
        try {
            cruisebooking.suites();
            log.info("Suite Selected.");
        } catch (Exception e) {
        	eLogger.error("Suite selection interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at suite selection in suites: " + e.getMessage());
        }

        Thread.sleep(2000); // Better replaced with explicit waits
        try {
            cruisebooking.selectSuite();
            log.info("Select Suite clicked.");
        } catch (Exception e) {
        	eLogger.error("Suite button interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at select suite button in package: " + e.getMessage());
        }

        Thread.sleep(2000); // Better replaced with explicit waits
        try {
            cruisebooking.options();
            log.info("Item/Option selected.");
        } catch (Exception e) {
        	eLogger.error("Item/Option interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at selecting item in options: " + e.getMessage());
        }

        try {
            cruisebooking.proceedToBooking();
            log.info("Proceed to Booking button selected.");
        } catch (Exception e) {
        	eLogger.error("Proceed to Booking button interaction failed at: " + e.getMessage(), e);
            Assert.fail("Test failed at Proceed to Booking: " + e.getMessage());
        }
    }
}
