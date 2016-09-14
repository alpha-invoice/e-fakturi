package selenuim.tests.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeBrowser implements Browser {
    private final String PROPERTY_NAME = "webdriver.chrome.driver";
    private final String PATH_TO_BINARY = "src/main/resources/drivers/chromedriver.exe";

    public ChromeBrowser() {
        System.setProperty(PROPERTY_NAME, PATH_TO_BINARY);
    }

    @Override
    public WebDriver initDriver() {
        return new ChromeDriver();
    }
}
