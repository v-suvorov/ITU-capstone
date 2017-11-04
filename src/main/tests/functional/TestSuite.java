package tests.functional;

import com.paulhammant.ngwebdriver.NgWebDriver;
import helpers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.LoginPage;
import steps.LoginSteps;

public class TestSuite {
    private NgWebDriver ngWebDriver;
    private WebDriver driver;
    private LoginSteps loginSteps;

    private final String userLogin = "Use email address for your LinkedIn account";
    private final String userPass = "Use password for your LinkedIn account";

    @Parameters({"browserName"})
    @BeforeSuite
    public void testSetup(String browserName) {
        driver = DriverFactory.initBrowser(browserName);
        ngWebDriver = DriverFactory.initNgWebDriver(driver, browserName);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("blank:blank");
    }

    @Parameters({"envBaseURL"})
    @Test
    public void firstTest(String envBaseURL) {
        driver.get(envBaseURL);
        loginSteps = new LoginSteps(driver, ngWebDriver);
        loginSteps.login(userLogin, userPass);
    }

    @AfterSuite
    public void afterSuite() {
        driver.quit();
    }
}
