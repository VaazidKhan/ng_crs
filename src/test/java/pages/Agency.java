package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.BaseTest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Agency extends BaseTest {
    WebDriver driver;
    WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(Agency.class);

    // Constructor to initialize WebDriver and WebDriverWait
    public Agency(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }

    // Locators for various elements
    By homeField = By.xpath("//a[@class='side-btn active']"); // Verify it's clicked by default
    By customerField = By.xpath("//button[contains(text(),'Customer')]"); // Verify it's clicked by default
    By agenciesField = By.xpath("//i[@class='icon icon-agencies']");
    By agentsList = By.xpath("//div[@class='search-result d-flex justify-content-between align-items-center gap-2']");
    By totalAgents = By.xpath("//a[@class='fs-12 ng-star-inserted']"); // Extracting the total number of agents to compare
    By moveRightButton = By.xpath("//a[@class='move-btn move-right']");
    By moveLeftButton = By.xpath("//a[@class='move-btn move-left']");

    // Method to click on 'Agencies' field
    public void agency() {
        try {
            log.info("Clicking on Agencies field.");
            wait.until(ExpectedConditions.elementToBeClickable(agenciesField)).click();
            log.info("Agencies field clicked successfully.");
        } catch (Exception e) {
            log.error("Error in agency method: " + e.getMessage());
        }
    }

    // Method to select agencies and log all unique agency names
    public void selectAgency() {
        Set<String> allAgencyNames = new HashSet<>(); // Use Set to store unique agency names
        boolean isLastPage = false;

        while (!isLastPage) {
            try {
                // Get the list of currently visible agents
                List<WebElement> searchResults = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(agentsList));

                // Loop through the list of elements and store their text
                for (WebElement result : searchResults) {
                    String agencyText = result.getText().trim(); // Trim to remove extra spaces
                    if (!agencyText.isEmpty() && allAgencyNames.add(agencyText)) { // Only add non-empty and unique names
                        log.info("Agency Found: " + agencyText);
                    }
                }
                
                // Extract the number of agents from WebElement
                WebElement totalAgentsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(totalAgents));
                String totalAgentsText = totalAgentsElement.getText();
                
                // Use a regex pattern to extract the numeric value
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(totalAgentsText);
                if (matcher.find()) {
                    String numberOfAgents = matcher.group();  // Extract the numeric part
                    log.info("Total number of agents found: " + numberOfAgents);
                } else {
                    log.warn("No numeric value found in the total agents element.");
                }

                // Check if the "move right" button is clickable
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(moveRightButton));
                if (nextButton.isDisplayed()) {
                    nextButton.click();
                    log.info("Clicked the 'Move Right' button to view more agencies.");
                } else {
                    log.warn("No more pages available, reached the last set of agencies.");
                    isLastPage = true;
                }
                
            } catch (Exception e) {
                log.error("Error navigating pages or fetching agency data: " + e.getMessage());
                isLastPage = true; // If any error occurs, exit the loop
            }
        }

        // Print out all the agency names after collecting them
        log.info("Total Agencies Found: " + allAgencyNames.size());
        for (String agency : allAgencyNames) {
            log.info("Agency: " + agency);
        }
    }
}
