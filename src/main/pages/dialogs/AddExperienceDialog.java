package pages.dialogs;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.PageObject;

public class AddExperienceDialog extends PageObject {
    private final String titleFldErrorMsgCSS = "#title-error > p";
    private final String companyFldErrorMsgCSS = "#company-error > p";
    private final String yearPeriodErrorMsg1CSS = "#time-period-error > p";
    private final String yearPeriodErrorMsg2CSS = "#time-period-error > p + p";
    private final String endYearFldID = "position-end-year";
    private final String endMonthFldID = "position-end-month";
    private final String presentTxtCSS = "p.pe-form-time-period__present-activity";
    private final String updIndustryChbxCSS = "label[for^='position-update-industry']";
    private final String updHeadlineChbxCSS = "label[for^='position-update-headline']";

    @FindBy(name = "title")
    @CacheLookup
    WebElement titleFld;

    @FindBy(css = titleFldErrorMsgCSS)
    WebElement titleFldErrorMsg;

    @FindBy(name = "companyName")
    @CacheLookup
    WebElement companyFld;

    @FindBy(css = companyFldErrorMsgCSS)
    WebElement companyFldErrorMsg;

    @FindBy(id = "position-start-year")
    @CacheLookup
    WebElement startYearFld;

    @FindBy(css = yearPeriodErrorMsg1CSS)
    WebElement yearPeriodErrorMsg1;

    @FindBy(id = endYearFldID)
    WebElement endYearFld;

    @FindBy(id = endMonthFldID)
    WebElement endMonthFld;

    @FindBy(css = yearPeriodErrorMsg2CSS)
    WebElement yearPeriodErrorMsg2;

    @FindBy(css = "label[for$='-works-here']")
    @CacheLookup
    WebElement iCurrentlyWorkThereChbx;

    @FindBy(css = "div > button.form-submit-action")
    @CacheLookup
    WebElement saveBtn;

    @FindBy(css = updIndustryChbxCSS)
    WebElement updIndustryChbx;

    @FindBy(css = updHeadlineChbxCSS)
    WebElement updHeadlineChbx;

    @FindBy(css = "div > div > button.form-back-action")
    @CacheLookup
    WebElement closeBtn;

    public AddExperienceDialog(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        PageFactory.initElements(this.driver, this);
    }

    public void clickSaveBtn() {
        clickElement(saveBtn);
    }

    public boolean isTitleErrorMsgDisplayed() {
        return isElementDisplayed(By.cssSelector(titleFldErrorMsgCSS));
    }

    public boolean isCompanyErrorMsgDisplayed() {
        return isElementDisplayed(By.cssSelector(companyFldErrorMsgCSS));
    }

    public boolean isPeriodErrorMsg1Displayed() {
        return isElementDisplayed(By.cssSelector(yearPeriodErrorMsg1CSS));
    }

    public boolean isPeriodErrorMsg2Displayed() {
        return isElementDisplayed(By.cssSelector(yearPeriodErrorMsg2CSS));
    }

    public boolean isEndYearFldDisplayed() {
        return isElementDisplayed(By.id(endYearFldID));
    }

    public boolean isEndMonthFldDisplayed() {
        return isElementDisplayed(By.id(endMonthFldID));
    }

    public void clickICurrentlyWorkThereChbx() {
        clickElement(iCurrentlyWorkThereChbx);
    }

    public void setTextTitleFld(String textToInput) {
        setText(titleFld, textToInput);
    }

    public void setTextCompanyFld(String textToInput) {
        setText(companyFld, textToInput + Keys.ENTER);
    }

    public void setStartYear(String yearToSelect) {
        selectYear(startYearFld, yearToSelect);
    }

    public void setEndYear(String yearToInput) {
        selectYear(endYearFld, yearToInput);
    }

    public boolean isPresentTxtDisplayed() {
        return isElementDisplayed(By.cssSelector(presentTxtCSS));
    }

    public boolean isUpdateIndustryChbxDisplayed() {
        return isElementDisplayed(By.cssSelector(updIndustryChbxCSS));
    }

    public boolean isUpdateHeadlineChbxDisplayed() {
        return isElementDisplayed(By.cssSelector(updHeadlineChbxCSS));
    }

    public boolean getIndustryChbxValue() {
        return getChbxValue(updIndustryChbxCSS);
    }

    public boolean getHeadlineChbxValue() {
        return getChbxValue(updHeadlineChbxCSS);
    }

    public void clickCloseBtn() {
        clickElement(closeBtn);
    }

    public void waitForDialogClosed() {

    }
}
