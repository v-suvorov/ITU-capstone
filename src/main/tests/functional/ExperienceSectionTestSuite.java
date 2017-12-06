package tests.functional;

import com.paulhammant.ngwebdriver.NgWebDriver;
import helpers.DriverFactory;
import helpers.ExtentReportFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.testng.internal.MethodInstance;
import steps.EditProfileSteps;
import steps.HomeSteps;
import steps.LoginSteps;
import steps.dialogSteps.AddExperienceSteps;
import steps.dialogSteps.ConfirmDeletionSteps;
import steps.dialogSteps.EditExperienceSteps;

import java.lang.reflect.Method;

public class ExperienceSectionTestSuite {
    private NgWebDriver ngWebDriver;
    private WebDriver driver;
    SoftAssert softAssert;

    private final String userLogin = "Use email address for your LinkedIn account";
    private final String userPass = "Use password for your LinkedIn account";
    private final String testPositionTitle = "Senior Ranger";
    private final String testCompnanyName = "Night Watch";
    private final String testStartYear = "2011";
    private final String testEndYear = "2016";

    private LoginSteps loginSteps;
    private HomeSteps homeSteps;
    private EditProfileSteps editProfileSteps;
    private AddExperienceSteps addExperienceSteps;
    private EditExperienceSteps editExperienceSteps;
    private ConfirmDeletionSteps confirmDeletionSteps;

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
    public void navigateToStartingPage(String envBaseURL) {
        softAssert = new SoftAssert();
        driver.get(envBaseURL);
        editProfileSteps = homeSteps.clickUserProfileImage();
        addExperienceSteps = editProfileSteps.clickAddExperienceBtn();
    }

    @Test(priority = 1,
            enabled = true,
            description = "Adding new experience entry - only required fields")
    public void addingNewEntryTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        addExperienceSteps.populateRequiredFields(testPositionTitle, testCompnanyName, testStartYear, testEndYear);
        editProfileSteps = addExperienceSteps.clickSaveBtn();
        editProfileSteps.verifyExperienceEntryDisplayed(testPositionTitle, testCompnanyName, testStartYear, testEndYear, softAssert);
        softAssert.assertAll();
    }

    @Test(priority = 2,
            enabled = true,
            description = "4 fields are required to add a new experience entry")
    public void required4FieldsTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        addExperienceSteps.clickSaveBtn();
        addExperienceSteps.verifyErrorMessagesDisplayed(true, true, true, true, softAssert);
        softAssert.assertAll();
    }

    @Test(priority = 2,
            enabled = true,
            description = "3 fields are required to add a new experience entry if it's a current position")
    public void required3FieldsTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        addExperienceSteps.clickSaveBtn();
        addExperienceSteps.clickICurrentlyWorkThereChbx();
        addExperienceSteps.verifyErrorMessagesDisplayed(true, true, true, false, softAssert);
        softAssert.assertAll();
    }

    @Test(priority = 3,
            enabled = true,
            description = "When 'I currently work here' checkbox is checked, 2 fields get substituted by 'Present' text")
    public void presentTextSubstitutesEndPeriodFieldsTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        addExperienceSteps.clickICurrentlyWorkThereChbx();
        addExperienceSteps.verifyEndMonthFldDisplayed(false, softAssert);
        addExperienceSteps.verifyEndYearFldDisplayed(false, softAssert);
        addExperienceSteps.verifyPresentTxtDisplayed(true, softAssert);
        softAssert.assertAll();
    }

    @Test(priority = 3,
            enabled = true,
            description = "When 'I currently work here' checkbox is checked, 2 additional checkboxes appear")
    public void additionalChbxsAreDisplayedTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        addExperienceSteps.clickICurrentlyWorkThereChbx();
        addExperienceSteps.verifyUpdateChbxsDisplayed(true, true, softAssert);
        softAssert.assertAll();
    }

    @Test(priority = 3,
            dependsOnMethods = {"additionalChbxsAreDisplayedTest"},
            enabled = true,
            description = "2 additional checkboxes default values")
    public void additionalChbxsDefaultValuesTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        addExperienceSteps.clickICurrentlyWorkThereChbx();
        addExperienceSteps.verifyUpdateChbxsValues(false, true, softAssert);
        softAssert.assertAll();
    }

    @Test(priority = 1,
            dependsOnMethods = {"addingNewEntryTest"},
            enabled = true,
            description = "Deleting existing experience entry")
    public void deleteExistingEntryTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        editProfileSteps = addExperienceSteps.closeAddExpDialog();
        editExperienceSteps = editProfileSteps.clickEditExperienceBtn(testPositionTitle);
        confirmDeletionSteps = editExperienceSteps.clickDeleteBtn();
        editProfileSteps = confirmDeletionSteps.confirmDeletion();
        editProfileSteps.verifyExperienceEntryNotDisplayed(testPositionTitle, softAssert);
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