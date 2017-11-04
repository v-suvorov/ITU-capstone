package pages;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends PageObject {

    @FindBy(id = "login-email")
    private WebElement loginEmailFld;

    @FindBy(id = "login-password")
    private WebElement passwordFld;

    @FindBy(id = "login-submit")
    private WebElement signInBtn;

    public LoginPage(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        PageFactory.initElements(this.driver, this);
    }

    public void setLoginValue(String username) {
        loginEmailFld.sendKeys(username);
    }

    public void setPasswordValue(String password) {
        passwordFld.sendKeys(password);
    }

    public void clickLoginBtn() {
        clickElement(signInBtn);
        ngWebDriver.waitForAngularRequestsToFinish();
    }

}
