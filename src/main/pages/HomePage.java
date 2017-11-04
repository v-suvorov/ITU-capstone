package pages;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends PageObject {

    @FindBy(css = ".lazy-image.feed-identity-module__member-photo")
    private WebElement userProfileImage;

    public HomePage(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        PageFactory.initElements(this.driver, this);
    }

    public void clickUserProfileImage() {
        clickElement(userProfileImage);
        ngWebDriver.waitForAngularRequestsToFinish();
    }

}
