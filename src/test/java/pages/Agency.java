package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseTest;

public class Agency extends BaseTest {
    WebDriver driver;
    WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(Agency.class);

    // Store the list of agents as a class-level variable
    List<String> allAgents = new ArrayList<>();

    // Constructor to initialize WebDriver and WebDriverWait
    public Agency(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }

    // Locators for various elements
    By agenciesField = By.xpath("//i[@class='icon icon-agencies']");
    By agentsList = By.xpath("//label[@class='fs-14 text-truncate']");
    By totalAgents = By.xpath("//a[@class='fs-12 ng-star-inserted']");
    By moveRightButton = By.xpath("//a[@class='move-btn move-right']");

    int totalAgentCount;

    // Method to click on 'Agencies' field
    public void agency() {
        try {
            log.info("Clicking on Agencies field.");
            WebElement agenciesElement = wait.until(ExpectedConditions.elementToBeClickable(agenciesField));
            agenciesElement.click();
            log.info("Agencies field clicked successfully.");
        } catch (Exception e) {
            log.error("Error in agency method: " + e.getMessage(), e);
            Assert.fail("Failed to click on Agencies field: " + e.getMessage());
        }
    }

    // Method to get all agents and store them in the class-level variable 'allAgents'
 // Method to retrieve agents in batches
    private boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public void agent() {
        try {
            log.info("Extracting total agents and retrieving agent names.");

            // Get the total number of agents from the text
            WebElement totalAgentsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(totalAgents));
            String totalAgentsText = totalAgentsElement.getText();

            // Extract the number of total agents using regex
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(totalAgentsText);
            if (matcher.find()) {
                totalAgentCount = Integer.parseInt(matcher.group());
            } else {
                log.error("Could not extract total agent count from the text.");
                Assert.fail("Failed to extract total agent count.");
            }

            log.info("Total agents found: " + totalAgentCount);

            // Retrieve agents in batches
            while (allAgents.size() < totalAgentCount) {
                List<WebElement> displayedAgents = driver.findElements(agentsList);

                for (WebElement agentElement : displayedAgents) {
                    String agentName = agentElement.getText().trim();
                    if (!allAgents.contains(agentName)) {
                        allAgents.add(agentName);
                        log.info("Retrieved agent: " + agentName);
                    }
                }

                // If more agents need to be loaded, check if 'Move Right' button is visible
                if (allAgents.size() < totalAgentCount) {
                    if (isElementPresent(moveRightButton)) {
                        try {
                            WebElement moveRight = wait.until(ExpectedConditions.elementToBeClickable(moveRightButton));
                            moveRight.click();
                            log.info("Clicked the 'Move Right' button to load more agents.");
                        } catch (Exception e) {
                            log.error("Failed to click 'Move Right' button: " + e.getMessage());
                            break; // Exit the loop if the button is not clickable
                        }
                    } else {
                        log.info("'Move Right' button is not available.");
                        break; // Exit loop if 'Move Right' button is not present
                    }
                }
            }

            log.info("All agents retrieved successfully. Total agents: " + allAgents.size());

        } catch (Exception e) {
            log.error("Error in agent method: " + e.getMessage(), e);
            Assert.fail("Failed to retrieve agents: " + e.getMessage());
        }
    }


    // Method to select an agent from the list of already retrieved agents
    public void selectAgent(String agent) {
        try {
            log.info("Attempting to select agent: " + agent);

            // Check if the agent list has been populated and the agent exists
            if (allAgents.isEmpty()) {
                log.error("Agent list is empty. Ensure 'agent()' method is called before 'selectAgent()'.");
                Assert.fail("Agent list is empty. Call 'agent()' before trying to select an agent.");
            }

            if (allAgents.contains(agent)) {
                // Locate the WebElement corresponding to the agent and click it
                List<WebElement> agentElements = driver.findElements(agentsList);
                for (WebElement agentElement : agentElements) {
                    String agentName = agentElement.getText();
                    if (agentName.equalsIgnoreCase(agent)) {
                        agentElement.click();
                        log.info("Selected agent: " + agentName);
                        return; // Exit after selecting the agent
                    }
                }
            } else {
                log.error("Agent '" + agent + "' not found in the list.");
                Assert.fail("Agent '" + agent + "' not found.");
            }

        } catch (Exception e) {
            log.error("Error in selectAgent method: " + e.getMessage(), e);
            Assert.fail("Failed to select the agent: " + e.getMessage());
        }
    }
}
