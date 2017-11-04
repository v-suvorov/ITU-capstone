package pages;

import com.paulhammant.ngwebdriver.NgWebDriver;
import helpers.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.util.List;

public class PageObject {

    protected NgWebDriver ngWebDriver;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor jsExecutor;
    protected static long TIME_TO_WAIT = 15;

    /*
     * Method is created because standard
     * jse.executeScript("arguments[0].scrollIntoView(true);", element);
     * doesn't work for LinkedIn. Element may be not in the DOM yet
     */
    protected void scrollPage(int Ypixels) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("scroll(0, " + Ypixels + ");");
    }

    protected void scrollToElement(WebElement element) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void clickElement(WebElement element) {
        wait = new WebDriverWait(driver, TIME_TO_WAIT);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected String getText(WebElement element) {
        wait = new WebDriverWait(driver, TIME_TO_WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    protected boolean isElementDisplayed(By by) {
        return !driver.findElements(by).isEmpty();
    }

    /*
     * Setting text value to a field
     * No text parameter -> random string is generated with the length not more than 40
     */
    protected void setText(WebElement element) {
        wait = new WebDriverWait(driver, TIME_TO_WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
        String randomString = Utils.randomString(40);
        element.sendKeys(randomString);
        wait.until(ExpectedConditions.textToBePresentInElement(element, randomString));
    }

    /*
     * Setting text value to a field
     * @param: text string
     */
    protected void setText(WebElement element, String string) {
        wait = new WebDriverWait(driver, TIME_TO_WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(string);
//        wait.until(ExpectedConditions.textToBePresentInElement(element, string));
    }

    /*
     * Selecting a year value from LinkedIn calendar control element
     * Clicking the element and clicking a value
     * @param: year to select, e.g. "2017"
     */
    protected void selectYear(WebElement element, String year) {
        wait = new WebDriverWait(driver, TIME_TO_WAIT);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        By yearXPath = By.xpath(".//option[@value='" + year + "']");
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, yearXPath));
        element.findElement(yearXPath).click();
    }

    /*
     * Get a value of checkbox. Returned true, when checked, false - unchecked
     * @param: css locator for checkbox element
     */
    protected boolean getChbxValue(String chbxElementCSS) {
        wait = new WebDriverWait(driver, TIME_TO_WAIT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(chbxElementCSS)));
        String checkedChbxElementCSS = "input[type=checkbox]:checked + " + chbxElementCSS;
        int qty = driver.findElements(By.cssSelector(checkedChbxElementCSS)).size();
        if (qty == 1)
            return true;
        else if (qty == 0)
            return false;
        else
            throw new InvalidArgumentException("Incorrect CSS selector for tested checkbox");
    }

    protected void waitForElementToBeVisible(By by) {
        wait = new WebDriverWait(driver, TIME_TO_WAIT);
        ExpectedCondition<List<WebElement>> visibilityCondition = ExpectedConditions.visibilityOfAllElementsLocatedBy(by);
        handleStaleElement(visibilityCondition);
    }

    private void handleStaleElement(ExpectedCondition<List <WebElement>> condition) {
        int tryQty = 0;
        while(true) {
            try {
                wait.until(condition);
                break;
            } catch (StaleElementReferenceException e) {
                tryQty++;
                if (tryQty > 5) {
                    e.printStackTrace();
                    break;
                }
                System.out.println("StaleElementReferenceException handling: " + tryQty);
            }
        }
    }

    protected void waitForElementToBeVisible(WebElement element) {
        wait = new WebDriverWait(driver, TIME_TO_WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void dragAndDropElement(WebElement element, int moveX, int moveY) {
        Actions actions = new Actions(driver);
        Action dragAndDrop = actions.dragAndDropBy(element, moveX, moveY).build();
        dragAndDrop.perform();
    }

    protected void dragAndDropElement(WebElement elementDrag, WebElement elementDrop) {
        Actions actions = new Actions(driver);
        Action dragAndDrop = actions.dragAndDrop(elementDrag, elementDrop).build();
        dragAndDrop.perform();
    }

    protected void dragAndDropVertical(WebElement element, int startY, int finishY) {
        Actions actions = new Actions(driver);
        int offset = (finishY > startY ? 10 : -10);
        Action dragAndDrop = actions.dragAndDropBy(element, 0, (finishY - startY + offset)).build();
        dragAndDrop.perform();
    }

    protected void waitForJSReadyState() {
        wait = new WebDriverWait(driver, TIME_TO_WAIT);
        ExpectedCondition<Boolean> isReadyState = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                jsExecutor = (JavascriptExecutor) driver;
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };
        wait.until(isReadyState);
    }
}
