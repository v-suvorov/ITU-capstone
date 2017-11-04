package steps.dialogSteps;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import pages.dialogs.ConfirmDeletionDialog;
import steps.EditProfileSteps;

public class ConfirmDeletionSteps {
    private WebDriver driver;
    private NgWebDriver ngWebDriver;
    private ConfirmDeletionDialog confirmDeletionDialog;

    public ConfirmDeletionSteps(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        confirmDeletionDialog = new ConfirmDeletionDialog(this.driver, this.ngWebDriver);
    }

    public EditProfileSteps confirmDeletion() {
        confirmDeletionDialog.clickDeleteBtn();
        return new EditProfileSteps(driver, ngWebDriver);
    }
}
