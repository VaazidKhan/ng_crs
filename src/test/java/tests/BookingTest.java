package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.Agency;
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
    public void  agent() {
    	Agency agency = new Agency(driver);
    	
    	try {
    		Thread.sleep(3000);
    		agency.agency();
    		log.info("Clicked on Agnecy option");
    	}catch(Exception e) {
    		log.error("Agency interaction failed at : "+e.getMessage(),e);
    		Assert.fail("Test failed at agencies button : "+e.getMessage());
    	}
    	
    	try {
    		Thread.sleep(2000);
    		agency.agent();
    		log.info("Agencies listed");
    		
    	}catch(Exception e) {
    		log.error("Agency list interaction failed at : "+e.getMessage(),e);
    		Assert.fail("Test failed at agency list : "+e.getMessage());
    	}
    	try {
    		Thread.sleep(2000);
    		agency.selectAgent(config.getProperty("agent"));
    		log.info("Agent selected ");
    		
    	}catch(Exception e) {
    		log.error("Agent selection interaction failed at : "+e.getMessage(),e);
    		Assert.fail("Test failed at agent selection : "+e.getMessage());
    	
    }
    }
    
}
