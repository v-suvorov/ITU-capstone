package steps.dialogSteps;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import pages.dialogs.AddNewSkillDialog;
import steps.EditProfileSteps;

public class AddSkillSteps {
    private AddNewSkillDialog addNewSkillDialog;
    private WebDriver driver;
    private NgWebDriver ngWebDriver;

    public AddSkillSteps(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        addNewSkillDialog = new AddNewSkillDialog(this.driver, this.ngWebDriver);
    }

    public void typeInSkillName(String skillNameToAdd) {
        addNewSkillDialog.setAddSkillInputFldValue(skillNameToAdd);
    }

    public EditProfileSteps selectSearchResultFromDropdown() {
        addNewSkillDialog.clickResultDropdownValue();
        return new EditProfileSteps(driver, ngWebDriver);
    }

    public void verifyErrorMessageForAddingSkill(SoftAssert softAssert) {
        softAssert.assertTrue(addNewSkillDialog.isErrorMessageDisplayed(), "Error message verification failed");
    }
}
