package steps;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import pages.HomePage;

public class HomeSteps {
    private WebDriver driver;
    private NgWebDriver ngWebDriver;
    private HomePage homePage;

    public HomeSteps(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        homePage = new HomePage(driver, ngWebDriver);
    }

    public EditProfileSteps clickUserProfileImage() {
        homePage.clickUserProfileImage();
        return new EditProfileSteps(driver, ngWebDriver);
    }

}
