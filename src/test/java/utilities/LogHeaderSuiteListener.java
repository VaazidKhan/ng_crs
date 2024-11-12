package utilities;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class LogHeaderSuiteListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        // Append log headers for both INFO and ERROR logs to ensure they are logged at suite start
        LogHeaderAppender.appendLogHeader("logs/info.log");
        LogHeaderAppender.appendLogHeader("logs/error.log");
    }

    @Override
    public void onFinish(ISuite suite) {
        // Append log footers for both INFO and ERROR logs at suite end
        LogHeaderAppender.appendLogFooter("logs/info.log");
        LogHeaderAppender.appendLogFooter("logs/error.log");
    }
}
