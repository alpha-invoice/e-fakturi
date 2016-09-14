package selenuim.tests.browsers;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxBrowser implements Browser {
    private final String PATH_TO_BINARY = "src/main/resources/drivers/FirefoxPortable/FirefoxPortable.exe";

    private FirefoxBinary firefoxBinary;

    public FirefoxBrowser() {
        File firefoxFile = new File(PATH_TO_BINARY);
        firefoxBinary = new FirefoxBinary(firefoxFile);
    }

    @Override
    public WebDriver initDriver() {
        return new FirefoxDriver(this.firefoxBinary, new FirefoxProfile());
    }
}
