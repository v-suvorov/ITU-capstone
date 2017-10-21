package helpers;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by vsuvorov on 10/21/17.
 */
public class DriverFactory {
    public static WebDriver initBrowser(String browserName) {
        String osName = System.getProperty("os.name").toLowerCase();
        String pathToDrivers = "." + File.separator + "src" + File.separator + "resources" + File.separator + "drivers" + File.separator;
        WebDriver driver = null;
        switch (browserName) {
            case "firefox":
                //driver = new FirefoxDriver();
                break;
            case "ie":
                //System.setProperty("webdriver.ie.driver", "C:\\Users\\abc\\Desktop\\Server\\IEDriverServer.exe");
                //driver = new InternetExplorerDriver();
                break;
            case "chrome":
                if (osName.contains("mac")) {
                    System.setProperty("webdriver.chrome.driver", pathToDrivers + "chromedriver");
                } else if (osName.contains("windows")) {
                    System.setProperty("webdriver.chrome.driver", pathToDrivers + "chromedriver.exe");
                }
                driver = new ChromeDriver();
                break;
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }

    public static NgWebDriver initNgWebDriver(WebDriver driver, String browserName) {
        switch (browserName) {
            case "firefox":

                break;
            case "ie":

                break;
            case "chrome":
                return new NgWebDriver((ChromeDriver) driver);
        }
        return null;
    }
}
