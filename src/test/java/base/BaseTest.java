package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected WebDriver driver;
    protected Properties config;
    protected static Logger log;
    
    public WebDriver getDriver() {
        return driver;
    }
    
    String configfilePath = "src/main/resources/config.properties";
    String xmlfilePath = "src/main/resources/log4j2.xml";

    @BeforeClass
    public void setup() throws IOException {
        // Initialize logger
        log = LogManager.getLogger(BaseTest.class);

        // Load config properties
        config = new Properties();
        try (FileInputStream fis = new FileInputStream(configfilePath)) {
            config.load(fis);
        }

        // Ensure log directory exists
        java.io.File logDir = new java.io.File("logs");
        if (!logDir.exists()) {
            logDir.mkdir();
        }

        // Initialize WebDriverManager and set implicit wait
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));  // 60 seconds implicit wait
        driver.manage().window().maximize();
        driver.get(config.getProperty("url"));

        log.info("Driver initialized and application launched");
    }

    //@AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
            log.info("Driver quit successfully");
        }
    }
}