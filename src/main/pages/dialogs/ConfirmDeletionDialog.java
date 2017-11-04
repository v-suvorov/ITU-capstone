package pages.dialogs;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.PageObject;

public class ConfirmDeletionDialog extends PageObject {

    @FindBy(css = "button.confirmation-modal__secondary-action-btn")
    @CacheLookup
    private WebElement deleteBtn;

    public ConfirmDeletionDialog(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        PageFactory.initElements(this.driver, this);
    }

    public void clickDeleteBtn() {
        clickElement(deleteBtn);
    }
}
