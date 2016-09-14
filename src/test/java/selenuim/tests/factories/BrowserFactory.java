package selenuim.tests.factories;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import selenuim.tests.browsers.Browser;
import selenuim.tests.browsers.ChromeBrowser;
import selenuim.tests.browsers.FirefoxBrowser;
import selenuim.tests.browsers.InternetExplorerBrowser;
import selenuim.tests.enums.BROWSER_TYPE;
import selenuim.tests.exceptions.BrowserNotFoundException;

public class BrowserFactory implements Cloneable {
    private static Map<BROWSER_TYPE, Browser> browsersMap;

    static {
        initBrowsers();
    }

    private static void initBrowsers() {
        browsersMap = new HashMap<>();
        browsersMap.put(BROWSER_TYPE.FIREFOX, new FirefoxBrowser());
        browsersMap.put(BROWSER_TYPE.CHROME, new ChromeBrowser());
        browsersMap.put(BROWSER_TYPE.IE, new InternetExplorerBrowser());
    }

    public static WebDriver getBrowser(BROWSER_TYPE type) {
        try {
            checkLoadedBrowsers(type);
        } catch (BrowserNotFoundException e) {
            handleBrowserNotFoundExcpetion(e);
        }
        return browsersMap.get(type).initDriver();
    }

    public static void tearDownBrowser(BROWSER_TYPE type) {
        browsersMap.remove(type);
    }

    private static void checkLoadedBrowsers(BROWSER_TYPE type) throws BrowserNotFoundException {
        if (!browsersMap.containsKey(type)) {
            throw new BrowserNotFoundException("No such browser type found in factory");
        }
    }

    private static void handleBrowserNotFoundExcpetion(Exception ex) {
        ex.printStackTrace();
    }
}