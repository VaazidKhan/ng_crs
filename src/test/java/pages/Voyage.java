package pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

public class Voyage {

    WebDriver driver;
    Wait<WebDriver> wait;

    private static final Logger log = LogManager.getLogger(Voyage.class);
    private static final Logger errorLogger = LogManager.getLogger("com.demo.ng_crs.error");

    // Constructor to initialize WebDriver and FluentWait
    public Voyage(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(30))
                        .pollingEvery(Duration.ofSeconds(5))
                        .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
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

            WebElement voyageTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(voyagePipe));
            WebElement voyageTabTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(tab));

            String isSelected = voyageTabElement.getAttribute("aria-selected");
            String tabText = voyageTabTextElement.getText();

            if ("true".equals(isSelected) && tabText.equals("Voyage")) {
                log.info("Voyage tab is correctly selected and highlighted in the pipeline.");
                return true;
            } else {
                log.warn("Voyage tab is not selected. aria-selected: " + isSelected + ", Tab Text: " + tabText);
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
                log.info("Clicked on search button in Voyage tab.");
            } else {
                log.warn("Voyage tab is not active. Unable to proceed with search.");
                Assert.fail("Voyage tab is not active or unable to click on search button.");
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred in the voyageTab method.", e);
            Assert.fail("An error occurred while attempting to interact with the Voyage tab: " + e.getMessage());
        }
    }

    // Method to select the first cruise with a green mark and availability > 2
    public void availableCruise() {
        try {
            List<WebElement> cruiseList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(voyages));
            boolean cruiseSelected = false;

            for (WebElement cruise : cruiseList) {
                log.info("Checking cruise for availability and green mark.");

                WebElement availabilityIcon = cruise.findElement(By.cssSelector("i[title='Availability']"));
                String iconClass = availabilityIcon.getAttribute("class");

                if (iconClass.contains("available")) {
                    WebElement availabilityCountElement = cruise.findElement(By.cssSelector("span.fs-12.fw-600"));
                    String availabilityText = availabilityCountElement.getText().split("/")[0];
                    int availabilityCount = Integer.parseInt(availabilityText.trim());

                    if (availabilityCount > 2) {
                        WebElement selectVoyage = cruise.findElement(By.xpath(".//i[@class='icon icon-right-arrow m-l-2']"));
                        selectVoyage.click();
                        log.info("Selected a cruise with availability greater than 2.");
                        cruiseSelected = true;
                        break;
                    }
                }
            }

            if (!cruiseSelected) {
                log.warn("No cruise with a green mark and availability > 2 was found.");
            }
        } catch (Exception e) {
            errorLogger.error("An error occurred while selecting an available cruise.", e);
            Assert.fail("Failed to select an available cruise: " + e.getMessage());
        }
    }
}
