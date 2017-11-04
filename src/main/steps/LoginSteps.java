package steps;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;

public class LoginSteps {
    private WebDriver driver;
    private NgWebDriver ngWebDriver;
    private LoginPage loginPage;

    public LoginSteps(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        loginPage = new LoginPage(driver, ngWebDriver);
    }

    public HomeSteps login(String userLogin, String userPass) {
        loginPage.setLoginValue(userLogin);
        loginPage.setPasswordValue(userPass);
        loginPage.clickLoginBtn();
        return new HomeSteps(driver, ngWebDriver);
    }

}
