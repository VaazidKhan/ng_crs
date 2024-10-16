package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

public class LogHeaderAppender {
    private static Properties config;


    private static final Logger logger = LogManager.getLogger(LogHeaderAppender.class);

    // Method to append log header with system information
    public static void appendLogHeader() {
        config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            config.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("logs/App-" + java.time.LocalDate.now() + ".log", true)) {
            Properties systemProperties = System.getProperties();
            String Name = config.getProperty("username");
            String javaVersion = systemProperties.getProperty("java.version");
            String os = systemProperties.getProperty("os.name");
            String systemName = InetAddress.getLocalHost().getHostName();

            writer.write("-------------------------------------------------\n");
            writer.write("Name: "+Name+"\n");
            writer.write("Log Start: " + java.time.LocalDateTime.now() + "\n");
            writer.write("Java Version: " + javaVersion + "\n");
            writer.write("Operating System: " + os + "\n");
            writer.write("System Name: " + systemName + "\n");
            writer.write("-------------------------------------------------\n\n");
        } catch (IOException e) {
            logger.error("Failed to write log header", e);
        }
    }

    // Method to append log footer with the end time
    public static void appendLogFooter() {
        try (FileWriter writer = new FileWriter("logs/App-" + java.time.LocalDate.now() + ".log", true)) {
            writer.write("-------------------------------------------------\n");
            writer.write("Log End: " + java.time.LocalDateTime.now() + "\n");
            writer.write("-------------------------------------------------\n\n");
        } catch (IOException e) {
            logger.error("Failed to write log footer", e);
        }
    }
}