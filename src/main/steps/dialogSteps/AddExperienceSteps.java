package steps.dialogSteps;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import pages.dialogs.AddExperienceDialog;
import steps.EditProfileSteps;

public class AddExperienceSteps {
    private WebDriver driver;
    private NgWebDriver ngWebDriver;

    private AddExperienceDialog addExpDialog;
    public AddExperienceSteps(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        addExpDialog = new AddExperienceDialog(this.driver, this.ngWebDriver);
    }

    public EditProfileSteps clickSaveBtn() {
        addExpDialog.clickSaveBtn();
        addExpDialog.waitForDialogClosed();
        return new EditProfileSteps(driver, ngWebDriver);
    }

//    public String getTitleFldErrorMsg() {
//        return addExpDialog.getTitleFldErrorMsg();
//    }
//
//    public String getCompanyFldErrorMsg() {
//        return addExpDialog.getCompanyFldErrorMsg();
//    }
//
//    public String getPeriodErrorMsg1() {
//        return addExpDialog.getPeriodErrorMsg1();
//    }
//
//    public String getPeriodErrorMsg2() {
//        return addExpDialog.getPeriodErrorMsg2();
//    }

    public void clickICurrentlyWorkThereChbx() {
        addExpDialog.clickICurrentlyWorkThereChbx();
    }

    public void populateRequiredFields(String positionTitle, String compnanyName, String startYear, String endYear) {
        addExpDialog.setTextTitleFld(positionTitle);
        addExpDialog.setTextCompanyFld(compnanyName);
        addExpDialog.setStartYear(startYear);
        addExpDialog.setEndYear(endYear);
    }

    public void verifyErrorMessagesDisplayed(boolean isTitleMsg, boolean isCompanyMsg, boolean isPeriod1Msg, boolean isPeriod2Msg, SoftAssert softAssert) {
        verifyTitleErrMsgDisplayed(isTitleMsg, softAssert);
        verifyCompanyErrMsgDisplayed(isCompanyMsg, softAssert);
        verifyPeriodErrMsg1Displayed(isPeriod1Msg, softAssert);
        verifyPeriodErrMsg2Displayed(isPeriod2Msg, softAssert);
    }

    private void verifyPeriodErrMsg1Displayed(boolean expected, SoftAssert softAssert) {
        softAssert.assertEquals(addExpDialog.isPeriodErrorMsg1Displayed(), expected, "First error message for work period displays incorrectly");
    }

    private void verifyPeriodErrMsg2Displayed(boolean expected, SoftAssert softAssert) {
        softAssert.assertEquals(addExpDialog.isPeriodErrorMsg2Displayed(), expected, "Second error message for work period displays incorrectly");
    }

    private void verifyTitleErrMsgDisplayed(boolean expected, SoftAssert softAssert) {
        softAssert.assertEquals(addExpDialog.isTitleErrorMsgDisplayed(), expected, "Position title error message displays incorrectly");
    }

    private void verifyCompanyErrMsgDisplayed(boolean expected, SoftAssert softAssert) {
        softAssert.assertEquals(addExpDialog.isCompanyErrorMsgDisplayed(), expected, "Company name error message displays incorrectly");
    }

    public void verifyEndMonthFldDisplayed(boolean expected, SoftAssert softAssert) {
        softAssert.assertEquals(addExpDialog.isEndMonthFldDisplayed(), expected, "'To' - 'Month' dropdown displays incorrectly");
    }

    public void verifyEndYearFldDisplayed(boolean expected, SoftAssert softAssert) {
        softAssert.assertEquals(addExpDialog.isEndYearFldDisplayed(), expected, "'To' - 'Year' dropdown displays incorrectly");
    }

    public void verifyPresentTxtDisplayed(boolean expected, SoftAssert softAssert) {
        softAssert.assertEquals(addExpDialog.isPresentTxtDisplayed(), expected, "'Present' text displays incorrectly");
    }

    public void verifyUpdateChbxsDisplayed(boolean updIndustryExpected, boolean updHeadlineExpected, SoftAssert softAssert) {
        softAssert.assertEquals(addExpDialog.isUpdateIndustryChbxDisplayed(), updIndustryExpected, "'Update my industry' checkbox displays incorrectly");
        softAssert.assertEquals(addExpDialog.isUpdateHeadlineChbxDisplayed(), updHeadlineExpected, "'Update my headline' checkbox displays incorrectly");
    }

    public void verifyUpdateChbxsValues(boolean updIndustryValueExpected, boolean updHeadlineValueExpected, SoftAssert softAssert) {
        softAssert.assertEquals(addExpDialog.getIndustryChbxValue(), updIndustryValueExpected, "'Update my industry' checkbox value is incorrect");
        softAssert.assertEquals(addExpDialog.getHeadlineChbxValue(), updHeadlineValueExpected, "'Update my headline' checkbox value is incorrect");
    }

    public EditProfileSteps closeAddExpDialog() {
        addExpDialog.clickCloseBtn();
        return new EditProfileSteps(driver, ngWebDriver);
    }
}
