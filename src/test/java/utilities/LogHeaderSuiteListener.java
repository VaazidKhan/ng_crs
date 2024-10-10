package utilities;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class LogHeaderSuiteListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        // Append log header without WebDriver
        LogHeaderAppender.appendLogHeader();
    }

    @Override
    public void onFinish(ISuite suite) {
        // Append log footer
        LogHeaderAppender.appendLogFooter();
    }
}
