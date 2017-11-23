package steps;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import pages.EditProfilePage;
import steps.dialogSteps.AddExperienceSteps;
import steps.dialogSteps.AddSkillSteps;
import steps.dialogSteps.EditExperienceSteps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EditProfileSteps {

    private WebDriver driver;
    private NgWebDriver ngWebDriver;

    private EditProfilePage editProfilePage;

    public EditProfileSteps(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        editProfilePage = new EditProfilePage(this.driver, this.ngWebDriver);
    }

    public AddExperienceSteps clickAddExperienceBtn() {
        editProfilePage.clickAddExperienceBtn();
        return new AddExperienceSteps(driver, ngWebDriver);
    }

    public void verifyExperienceEntryDisplayed(String expectedTitle, String expectedCompnanyName, String expectedStartYear, String expectedEndYear, SoftAssert softAssert) {
        Map<String, String> actualPositionInfo = editProfilePage.getPositionInfo(expectedTitle);
        softAssert.assertEquals(actualPositionInfo.get("title"), expectedTitle,
                "Comparison of actual position title and expected title failed");
        softAssert.assertEquals(actualPositionInfo.get("company"), expectedCompnanyName,
                "Comparison of actual company name and expected company name failed");
        softAssert.assertTrue(actualPositionInfo.get("startDate").contains(expectedStartYear),
                "Comparison of actual start date (year) and expected start date (year) failed");
        softAssert.assertTrue(actualPositionInfo.get("endDate").contains(expectedEndYear),
                "Comparison of actual end date (year) and expected end date (year) failed");
    }

    public EditExperienceSteps clickEditExperienceBtn(String positionTitle) {
        editProfilePage.clickEditExperienceBtn(positionTitle);
        return new EditExperienceSteps(driver, ngWebDriver);
    }

    public void verifyExperienceEntryNotDisplayed(String titleToSearch, SoftAssert softAssert) {
        Map<String, String> foundPositionInfo = editProfilePage.getPositionInfo(titleToSearch);
        softAssert.assertNull(foundPositionInfo, "After deleting a position it still can be found in the list");
    }

    public AddSkillSteps clickAddNewSkillBtn() {
        editProfilePage.clickAddNewSkillBtn();
        return new AddSkillSteps(driver, ngWebDriver);
    }

    public EditProfileSteps expandSkillsSection() {
        editProfilePage.clickSeeMoreSkillsBtn();
        return new EditProfileSteps(driver, ngWebDriver);
    }

    public void verifySkillIsAdded(String skillName, boolean expected, SoftAssert softAssert) {
        softAssert.assertEquals(editProfilePage.isSkillElementDisplayed(skillName), expected, "Adding of skill verification failed");
    }

    public List<String> swapEducationEntries(String dragEducationName, String dropEducationName) {
        editProfilePage.swapEducationEntries(dragEducationName, dropEducationName);
        return editProfilePage.getEducationMajorsOrder();
    }

    public List<String> getEducationEntriesOrder() {
        return editProfilePage.getEducationMajorsOrder();
    }

    public void verifyEducationEntriesSwapped(String major1,
                                              String major2,
                                              List<String> majorsOrderBefore,
                                              List<String> majorsOrderAfter,
                                              SoftAssert softAssert) {
        softAssert.assertNotEquals(majorsOrderBefore, majorsOrderAfter, "Order of education entries has not been changed");
        List<String> majorsOrderExpected = new ArrayList<String>(Arrays.asList("", ""));
        int major1Idx = majorsOrderBefore.indexOf(major1);
        int major2Idx = majorsOrderBefore.indexOf(major2);
        majorsOrderExpected.set(major2Idx, major1);
        majorsOrderExpected.set(major1Idx, major2);
        softAssert.assertEquals(majorsOrderAfter, majorsOrderExpected, "Order of education entries is incorrect");
    }
}
