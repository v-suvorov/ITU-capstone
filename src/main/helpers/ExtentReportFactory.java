package helpers;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;

import java.io.File;

public class ExtentReportFactory {
    private static ExtentReports extent;
    private static ExtentTest logger;

    public static void initExtentReports() {
        String reportPath = System.getProperty("user.dir") + File.separator
                + "target" + File.separator
                + "TestRunReport.html";
        String configPath = System.getProperty("user.dir") + File.separator
                + "extent-config.xml";
        extent = new ExtentReports(reportPath, true);
        extent
                .addSystemInfo("Host Name", "local")
                .addSystemInfo("Environment", "Automation Testing Capstone Project")
                .addSystemInfo("User Name", "V. Suvorov");
        extent.loadConfig(new File(configPath));
    }

    public static void startTest(String testDescription){
        logger = extent.startTest(testDescription);
    }

    public static void generateExtentReport(ITestResult result) {
        if(result.getStatus() == ITestResult.SUCCESS) {
            logger.log(LogStatus.PASS, "Test Case " + result.getName() + " is PASSED");
        } else if(result.getStatus() == ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, "Test Case " + result.getName() + " is FAILED");
            logger.log(LogStatus.FAIL, "Test Case " + result.getName() + " fail stacktrace: " + result.getThrowable());
        } else if(result.getStatus() == ITestResult.SKIP) {
            logger.log(LogStatus.SKIP, "Test Case " + result.getName() + " is SKIPPED");
        }
        extent.endTest(logger);
    }

    public static void flush() {
        extent.flush();
    }

    public static void close() {
        extent.close();
    }
}
