package pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Voyage {

    WebDriver driver;
    WebDriverWait wait;

    private static final Logger log = LogManager.getLogger(Voyage.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error"); // For ERROR logs

    // Constructor to initialize WebDriver and WebDriverWait
    public Voyage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait
    }

    // Locators for pipeline "Voyage" tab
    By voyagePipe = By.xpath("//li[@role='tab' and @aria-controls='k-tabstrip-tabpanel-0' and .//span[text()='Voyage']]");
    By tab = By.xpath("//h3[@class='d-flex align-items-center ng-star-inserted']/span");
    By cruiseSearch = By.xpath("//i[@class='icon icon-search']");
    By voyages = By.xpath("//ul[@class='list-unstyled panel-content-list m-b-0']/li");

    // Method to verify if "Voyage" tab is selected
    public boolean isVoyageTabSelected() {
        try {
            log.info("Verifying if the UI is in the 'Voyage' tab.");

            // Find the "Voyage" tab element
            WebElement voyageTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(voyagePipe));
            
            // Check if the 'aria-selected' attribute is set to true
            String isSelected = voyageTabElement.getAttribute("aria-selected");
            String tabText = voyageTabElement.findElement(By.tagName("span")).getText();
            String text = driver.findElement(tab).getText();

            if ("true".equals(isSelected) && "Voyage".equals(tabText)) {
                log.info("Voyage tab is correctly selected and highlighted in the pipeline.");
                return true;
            } else {
                errorLogger.error("Voyage tab is not selected as expected. aria-selected: " + isSelected + ", Tab Text: " + tabText);
                return false;
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred while verifying the Voyage tab selection.", e);
            return false;
        }
    }

    // Method to perform actions related to Voyage tab
    public void voyageTab() {
        try {
            if (isVoyageTabSelected()) {
                log.info("Voyage tab is currently active.");
                WebElement searchCruise = wait.until(ExpectedConditions.elementToBeClickable(cruiseSearch));
                searchCruise.click();
                log.info("Click on search button in voyage");
            } else {
                errorLogger.error("Voyage tab is not active. Please verify the selection.");
                Assert.fail("Voyage tab is not active or unable to click on search button");
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred in voyageTab method.", e);
            Assert.fail("An error occurred while attempting to interact with the Voyage tab: " + e.getMessage());
        }
    }

    // Method to select the first cruise with green mark and availability > 2
    public void availableCruise() {
        try {
            List<WebElement> cruiseList = driver.findElements(voyages);

            for (WebElement cruise : cruiseList) {
                log.info("Checking if the cruise has a green mark (availability icon) and availability count greater than 2");

                // Find the availability icon (green indicator) within the cruise item
                WebElement availabilityIcon = cruise.findElement(By.cssSelector("i[title='Availability']"));
                String iconClass = availabilityIcon.getAttribute("class");

                // Check if the icon class includes 'available' (indicating green)
                if (iconClass.contains("available")) {

                    // Find the availability count and convert it to an integer
                    WebElement availabilityCountElement = cruise.findElement(By.cssSelector("span.fs-12.fw-600"));
                    String availabilityText = availabilityCountElement.getText().split("/")[0]; // Extract the count before "/"
                    int availabilityCount = Integer.parseInt(availabilityText);

                    if (availabilityCount > 2) {
                        // Click on the ">" mark to select the cruise if availability is more than 2
                        WebElement selectVoyage = cruise.findElement(By.xpath(".//i[@class='icon icon-right-arrow m-l-2']"));
                        selectVoyage.click();
                        log.info("Selected a cruise with availability greater than 2.");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred while selecting an available cruise.", e);
            Assert.fail("Failed to select an available cruise: " + e.getMessage());
        }
    }
}
