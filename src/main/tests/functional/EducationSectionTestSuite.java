package tests.functional;

import com.paulhammant.ngwebdriver.NgWebDriver;
import helpers.DriverFactory;
import helpers.ExtentReportFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import steps.EditProfileSteps;
import steps.HomeSteps;
import steps.LoginSteps;

import java.lang.reflect.Method;
import java.util.List;

public class EducationSectionTestSuite {
    private NgWebDriver ngWebDriver;
    private WebDriver driver;
    SoftAssert softAssert;

    private final String userLogin = "Use email address for your LinkedIn account";
    private final String userPass = "Use password for your LinkedIn account";
    private final String educationMajor1 = "Computer Software Engineering";
    private final String educationMajor2 = "Management Information Systems";

    private LoginSteps loginSteps;
    private HomeSteps homeSteps;
    private EditProfileSteps editProfileSteps;

    @BeforeSuite
    public void setupReport() {
        ExtentReportFactory.initExtentReports();
    }

    @Parameters({"browserName", "envBaseURL"})
    @BeforeClass
    public void testSetup(String browserName, String envBaseURL) {
        driver = DriverFactory.initBrowser(browserName);
        ngWebDriver = DriverFactory.initNgWebDriver(driver, browserName);
        driver.get(envBaseURL);
        loginSteps = new LoginSteps(driver, ngWebDriver);
        homeSteps = loginSteps.login(userLogin, userPass);
    }

    @Parameters({"envBaseURL"})
    @BeforeMethod
    public void beforeMethod(String envBaseURL) {
        softAssert = new SoftAssert();
        driver.get(envBaseURL);
        editProfileSteps = homeSteps.clickUserProfileImage();
    }

    @Test(priority = 4,
            enabled = true,
            description = "Reorder education entries - can be done with drag&drop mouse action")
    public void dragAndDropEducationReorderingTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        List<String> majorsOrderBefore = editProfileSteps.getEducationEntriesOrder();
        List<String> majorsOrderAfter = editProfileSteps.swapEducationEntries(educationMajor1, educationMajor2);
        editProfileSteps.verifyEducationEntriesSwapped(educationMajor1,
                educationMajor2,
                majorsOrderBefore,
                majorsOrderAfter,
                softAssert);
        softAssert.assertAll();
    }

    @AfterMethod
    public void generateReport(ITestResult result) {
        ExtentReportFactory.generateExtentReport(result);
        ExtentReportFactory.flush();
    }

    @AfterClass
    public void afterClass() {
        driver.close();
    }

    @AfterSuite
    public void closeReport() {
        ExtentReportFactory.close();
    }
}