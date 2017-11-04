package steps.dialogSteps;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import pages.dialogs.EditExperienceDialog;
import steps.EditProfileSteps;

public class EditExperienceSteps {
    private EditExperienceDialog editExperienceDialog;
    private WebDriver driver;
    private NgWebDriver ngWebDriver;

    public EditExperienceSteps(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        editExperienceDialog = new EditExperienceDialog(this.driver, this.ngWebDriver);
    }

    public ConfirmDeletionSteps clickDeleteBtn() {
        editExperienceDialog.clickDeleteBtn();
        return new ConfirmDeletionSteps(driver, ngWebDriver);
    }
}
