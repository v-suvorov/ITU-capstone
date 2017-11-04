package tests.functional;

import com.paulhammant.ngwebdriver.NgWebDriver;
import helpers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.LoginPage;

public class TestSuite {
    private NgWebDriver ngWebDriver;
    private WebDriver driver;
    private LoginPage loginPage;

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
        driver.get(envBaseURL + "/login");
        loginPage = new LoginPage(driver);
        loginPage.setLoginValue(driver, "login_value");
        loginPage.setPasswordValue(driver, "password_value");
        loginPage.clickLoginBtn(driver);
        ngWebDriver.waitForAngularRequestsToFinish();
    }

    @AfterSuite
    public void afterSuite() {
        driver.quit();
    }
}
