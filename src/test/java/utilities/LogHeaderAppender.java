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

    static {
        config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            config.load(fis);
        } catch (IOException e) {
            logger.error("Failed to load config properties", e);
        }
    }

    // Method to append log header with system information
    public static void appendLogHeader(String logFilePath) {
        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            String name = config.getProperty("username", "supportuser");
            String javaVersion = System.getProperty("java.version");
            String os = System.getProperty("os.name");
            String systemName = InetAddress.getLocalHost().getHostName();

            writer.write("-------------------------------------------------\n");
            writer.write("Name: " + name + "\n");
            writer.write("Log Start: " + java.time.LocalDateTime.now() + "\n");
            writer.write("Java Version: " + javaVersion + "\n");
            writer.write("Operating System: " + os + "\n");
            writer.write("System Name: " + systemName + "\n");
            writer.write("-------------------------------------------------\n\n");
        } catch (IOException e) {
            logger.error("Failed to write log header", e);
        }
    }

    // Method to append log footer with end time
    public static void appendLogFooter(String logFilePath) {
        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            writer.write("-------------------------------------------------\n");
            writer.write("Log End: " + java.time.LocalDateTime.now() + "\n");
            writer.write("-------------------------------------------------\n\n");
        } catch (IOException e) {
            logger.error("Failed to write log footer", e);
        }
    }
}
