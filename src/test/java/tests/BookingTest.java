package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CentralSetUp;
import pages.LoginPage;
import pages.MenuIcon;

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
   
    @Test(dependsOnMethods = "login")
    public void menu() {
    	MenuIcon menuicon = new MenuIcon(driver);
    	
    	//click on menu icon
    	try {
    		Thread.sleep(4000);
    		menuicon.menu();
    		log.info("Clicked on menu icon");
    	}catch (Exception e) {
    		log.error("Menu icon interaction failed at :"+e.getMessage());
    		Assert.fail("Test failed at Menu Icon: "+e.getMessage());
    	}
    }
    
    @Test(dependsOnMethods = "menu")
    public void csu() {
    	CentralSetUp centralsetup = new CentralSetUp(driver);
    	
    	//click on csu
    	try {
    		Thread.sleep(4000);
    		centralsetup.csu();
    		log.info("Clicked on central set up");
    	}catch (Exception e) {
    		log.error("Central Set Up interaction failed at :"+e.getMessage());
    		Assert.fail("Test failed at Central Setu Up: "+e.getMessage());
    	}	
    }
    
}
