package pages;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProfilePage extends PageObject {

    private By positionCardsCSSLocator = By.cssSelector("li.pv-profile-section__card-item.pv-position-entity");
    private By positionTitleCSSLocator = By.cssSelector("div.pv-entity__summary-info > h3");
    private By positionCompanyCSSLocator = By.cssSelector("span.pv-entity__secondary-title");
    private By positionPeriodCSSLocator = By.cssSelector("h4.pv-entity__date-range > span + span");
    private By skillsCSSLocator = By.cssSelector("span.pv-skill-entity__skill-name");
    private By educatoinCardsCSSLocator = By.cssSelector("div.pv-education-entity");
    private By eduMajorCSSLocator = By.cssSelector("p.pv-entity__fos > span + span");
    private By eduSchoolNameCSSLocator = By.cssSelector("div.pv-entity__degree-info > h3");
    private By eduPeriodCSSLocator = By.cssSelector("p.pv-entity__dates > span + span > time");
    private String positionEditBtnXpathStart = "//li[";
    private String positionEditBtnXpathEnd = "]/div/a[@data-control-name='edit_position']";
    private String educationHandleBtnXpathStart = "//li[";
    private String educationHandleBtnXpathEnd = "]//button[@class='pv-entity__handle pv-entity__reorder-handle pv-profile-section__hoverable-action mt2']";

    @FindBy(css = ".add-position")
    private WebElement addPositionBtn;

    @FindBy(css = "a[data-control-name='edit_skills_add']")
    private WebElement addNewSkillBtn;

//    @FindBy(css = "li.pv-profile-section__card-item.pv-position-entity")
//    private List<WebElement> positionCards;

    @FindBy(css = "button[aria-expanded=false][data-control-name='skill_details']")
    private WebElement seeMoreSkillsBtn;


    public EditProfilePage(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        PageFactory.initElements(this.driver, this);
    }

    public void clickAddExperienceBtn() {
        scrollPage(1000);
        clickElement(addPositionBtn);
    }

    private List<WebElement> getPositionCardElements() {
        scrollPage(1000);
        waitForElementToBeVisible(positionCardsCSSLocator);
        return driver.findElements(positionCardsCSSLocator);
    }

    public Map<String, String> getPositionInfo(String expectedTitle) {
        List<WebElement> positionCards = getPositionCardElements();
        Map<String, String> positionInfo = new HashMap<String, String>();
        waitForElementToBeVisible(positionTitleCSSLocator);
        for (int i = 0; i < positionCards.size(); i++) {
            WebElement positionCard = positionCards.get(i).findElement(positionTitleCSSLocator);
            String positionTitleFournd = positionCard.getText();
            if (positionTitleFournd.equals(expectedTitle)) {
                String positionCompanyFound = positionCards.get(i).findElement(positionCompanyCSSLocator).getText();
                String postitionPeriodFound = positionCards.get(i).findElement(positionPeriodCSSLocator).getText();
                String positionStartDate = postitionPeriodFound.split(" – ")[0];
                String positionEndDate = postitionPeriodFound.split(" – ")[1];
                positionInfo.put("title", positionTitleFournd);
                positionInfo.put("company", positionCompanyFound);
                positionInfo.put("startDate", positionStartDate);
                positionInfo.put("endDate", positionEndDate);
                return positionInfo;
            }
        }
        return null;
    }

    public void clickEditExperienceBtn(String positionTitle) {
        List<WebElement> positionCards = getPositionCardElements();
        for (int i = 0; i < positionCards.size(); i++) {
            WebElement positionTitleElement = positionCards.get(i).findElement(positionTitleCSSLocator);
            waitForElementToBeVisible(positionTitleElement);
            String positionTitleFound = positionTitleElement.getText();
            if (positionTitleFound.equals(positionTitle)) {
                By positionEditBtnXpathLocator = By.xpath(positionEditBtnXpathStart + (i + 1) + positionEditBtnXpathEnd);
                WebElement positionEditBtn = driver.findElement(positionEditBtnXpathLocator);
                positionEditBtn.click();
                break;
            }
        }
    }

    public void clickAddNewSkillBtn() {
        scrollPage(3000);
        clickElement(addNewSkillBtn);
    }

    public void clickSeeMoreSkillsBtn() {
        scrollPage(3000);
        clickElement(seeMoreSkillsBtn);
    }

    public boolean isSkillElementDisplayed(String skillName) {
        waitForElementToBeVisible(skillsCSSLocator);
        List<WebElement> skillElementsFound = driver.findElements(skillsCSSLocator);
        for (int i = skillElementsFound.size() - 1; i >= 0; i--) {
            String skillNameFound = skillElementsFound.get(i).getText();
            if (skillNameFound.equals(skillName)) return true;
        }
        return false;
    }

    public void swapEducationEntries(String dragEducationName, String dropEducationName) {
        By dragBtnXpathLocator = getEducationHandleBtnLocator(dragEducationName);
        By dropBtnXpathLocator = getEducationHandleBtnLocator(dropEducationName);
        WebElement dragBtn = driver.findElement(dragBtnXpathLocator);
        WebElement dropBtn = driver.findElement(dropBtnXpathLocator);
        int dragBtnY = dragBtn.getLocation().getY();
        int dropBtnY = dropBtn.getLocation().getY();
        scrollPage(2500);
        dragAndDropVertical(dragBtn, dragBtnY, dropBtnY);
    }

    private List<WebElement> getEducationEntries() {
        scrollPage(2500);
        waitForElementToBeVisible(educatoinCardsCSSLocator);
        return driver.findElements(educatoinCardsCSSLocator);
    }

    private Map<String, String> getEducationInfo(String majorName) {
        List<WebElement> educationEntries = getEducationEntries();
        Map<String, String> educationInfo = new HashMap<String, String>();
        waitForElementToBeVisible(eduMajorCSSLocator);
        for (int i = 0; i < educationEntries.size(); i++) {
            WebElement majorNameElement = educationEntries.get(i).findElement(eduMajorCSSLocator);
            String majorNameFound = majorNameElement.getText();
            if (majorNameFound.equals(majorName)) {
                String schoolNameFound = educationEntries.get(i).findElement(eduSchoolNameCSSLocator).getText();
                List<WebElement> startAndEndDatesFound = educationEntries.get(i).findElements(eduPeriodCSSLocator);
                String eduStartDate = startAndEndDatesFound.get(0).getText();
                String eduEndDate = startAndEndDatesFound.get(1).getText();
                educationInfo.put("school", schoolNameFound);
                educationInfo.put("majorName", majorNameFound);
                educationInfo.put("startDate", eduStartDate);
                educationInfo.put("endDate", eduEndDate);
                return educationInfo;
            }
        }
        return null;
    }

    private By getEducationHandleBtnLocator(String majorName) {
        List<WebElement> educationEntries = getEducationEntries();
        By eduHandleBtnXpathLocator = null;
        for (int i = 0; i < educationEntries.size(); i++) {
            WebElement eduMajorElement = educationEntries.get(i).findElement(eduMajorCSSLocator);
            waitForElementToBeVisible(eduMajorElement);
            String eduMajorFound = eduMajorElement.getText();
            if (eduMajorFound.equals(majorName)) {
                eduHandleBtnXpathLocator = By.xpath(educationHandleBtnXpathStart + (i + 1) + educationHandleBtnXpathEnd);
                return eduHandleBtnXpathLocator;
            }
        }
        return null;
    }

    public List<String> getEducationMajorsOrder() {
        List<WebElement> educationEntries = getEducationEntries();
        List<String> majorsFound = new ArrayList<String>();
        for (int i = 0; i < educationEntries.size(); i++) {
            WebElement eduMajorElement = educationEntries.get(i).findElement(eduMajorCSSLocator);
            waitForElementToBeVisible(eduMajorElement);
            String eduMajorFound = eduMajorElement.getText();
            majorsFound.add(eduMajorFound);
        }
        return majorsFound;
    }
}
