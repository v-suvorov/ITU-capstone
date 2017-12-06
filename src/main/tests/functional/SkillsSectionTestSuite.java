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
import steps.dialogSteps.AddSkillSteps;

import java.lang.reflect.Method;

public class SkillsSectionTestSuite {
    private NgWebDriver ngWebDriver;
    private WebDriver driver;
    SoftAssert softAssert;

    private final String userLogin = "Use email address for your LinkedIn account";
    private final String userPass = "Use password for your LinkedIn account";
    private final String newSkillToAdd = "Python";

    private LoginSteps loginSteps;
    private HomeSteps homeSteps;
    private EditProfileSteps editProfileSteps;
    private AddSkillSteps addSkillSteps;

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

    @Test(priority = 3,
            enabled = true,
            description = "Adding new skill - can not be added twice")
    public void addingNewSkillTwiceTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        addSkillSteps = editProfileSteps.clickAddNewSkillBtn();
        addSkillSteps.typeInSkillName(newSkillToAdd);
        editProfileSteps = addSkillSteps.selectSearchResultFromDropdown();
        editProfileSteps = editProfileSteps.expandSkillsSection();
        editProfileSteps.verifySkillIsAdded(newSkillToAdd, true, softAssert);
        addSkillSteps = editProfileSteps.clickAddNewSkillBtn();
        addSkillSteps.typeInSkillName(newSkillToAdd);
        addSkillSteps.verifyErrorMessageForAddingSkill(softAssert);
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