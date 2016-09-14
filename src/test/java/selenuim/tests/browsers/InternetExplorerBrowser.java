package selenuim.tests.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Before using the Internet Explorer Driver, you should check that you have
 * disabled the Protected Mode. For more information:
 * http://stackoverflow.com/questions/11157902/selenium-webdriver-and-internetexplorer.
 */
public class InternetExplorerBrowser implements Browser {
    private final String PROPERTY_NAME = "webdriver.ie.driver";
    private final String PATH_TO_BINARY = "src/main/resources/drivers/iedriver.exe";

    public InternetExplorerBrowser() {
        System.setProperty(PROPERTY_NAME, PATH_TO_BINARY);
    }

    @Override
    public WebDriver initDriver() {
        return new InternetExplorerDriver();
    }
}
