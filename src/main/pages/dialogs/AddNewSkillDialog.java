package pages.dialogs;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.PageObject;

public class AddNewSkillDialog extends PageObject {

    private final String errorMessageXpathLocator = "//p[text()='Looks like you already have this skill on your profile.']";
    private final String addedSkillCSSLocator = "div.pv-add__message > p.pv-add__error";

    @FindBy(css = "div.type-ahead-input > input")
    private WebElement addSkillInputFld;

    @FindBy(css = "div.type-ahead-result-info > h3")
    private WebElement resultDropdownValue;

    @FindBy(xpath = errorMessageXpathLocator)
    private WebElement existingSkillErrorMsg;

    @FindBy(css = addedSkillCSSLocator)
    private WebElement addedSkillElement;

    public AddNewSkillDialog(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        PageFactory.initElements(this.driver, this);
    }

    public void setAddSkillInputFldValue(String value) {
        setText(addSkillInputFld, value);
    }

    public void clickResultDropdownValue() {
        clickElement(resultDropdownValue);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(By.xpath(errorMessageXpathLocator));
    }
}
