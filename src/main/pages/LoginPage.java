package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "loginId")
    private WebElement usernameFld;

    @FindBy(id = "loginPassword")
    private WebElement passwordFld;

    @FindBy(id = "submitLogin")
    private WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setLoginValue(WebDriver driver, String username) {
        usernameFld.sendKeys(username);
    }

    public void setPasswordValue(WebDriver driver, String password) {
        passwordFld.sendKeys(password);
    }

    public void clickLoginBtn(WebDriver driver) {
        loginBtn.click();
    }

}
