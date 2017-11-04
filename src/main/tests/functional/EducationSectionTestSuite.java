package tests.functional;

import com.paulhammant.ngwebdriver.NgWebDriver;
import helpers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import steps.EditProfileSteps;
import steps.HomeSteps;
import steps.LoginSteps;

import java.util.ArrayList;
import java.util.List;

public class EducationSectionTestSuite {
    private NgWebDriver ngWebDriver;
    private WebDriver driver;
    SoftAssert softAssert;

    private final String userLogin = "Use email address for your LinkedIn account";
    private final String userPass = "Use password for your LinkedIn account";
    private List<String> educationEntries = new ArrayList<String>();

    private LoginSteps loginSteps;
    private HomeSteps homeSteps;
    private EditProfileSteps editProfileSteps;

    @Parameters({"browserName", "envBaseURL"})
    @BeforeSuite
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
            enabled = false,
            description = "Reorder education entries - can be done with drag&drop mouse action")
    public void dragAndDropEducationReorderingTest() {
        editProfileSteps.swapEducationEntries("Computer Software Engineering", "Management Information Systems");
//        educationEntries = editProfileSteps.getEducationEntriesOrder();
//        int lastEdEntryIndex = educationEntries.size() - 1;
//        editProfileSteps.swapEducationEntries(educationEntries.get(0), educationEntries.get(lastEdEntryIndex));
//        editProfileSteps.verifyEducationEntriesOrderIsChanged(educationEntries, softAssert);
//        editProfileSteps.verifyEducationEntriesOrder(educationEntries, 1, 2, softAssert);
        softAssert.assertAll();
    }

    @AfterMethod
    public void afterTestMethod() {
        driver.get("blank:blank");
    }

    @AfterSuite
    public void afterSuite() {
        driver.quit();
    }
}