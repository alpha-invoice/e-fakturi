package selenuim.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import selenuim.tests.enums.BROWSER_TYPE;
import selenuim.tests.factories.BrowserFactory;
import selenuim.tests.objects.pages.ViewInvoicesPage;

/**
 * TestNG class with one positive test checking the previewing of all of the
 * previous invoices.
 */
@Test
public class TestViewInvoices {
    private WebDriver driver;

    /**
     * Before every test we start a web driver. Using factory design pattern to
     * start different browser.
     */
    @BeforeMethod
    public void setUp() {
        driver = BrowserFactory.getBrowser(BROWSER_TYPE.IE);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    /**
     * After every test, we stop the web driver.
     */
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    /**
     * Positive test that checks that when the first invoice is clicked, It is
     * shown all if Its information. After redirecting to the URL, the driver
     * waits 20 second due to the fact that the Front-end takes some time to
     * load.
     */
    public void testPreviewingPreviousInvoice() {
        driver.get(ViewInvoicesPage.URL);
        driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);

        ViewInvoicesPage viewInvoicesPage = PageFactory.initElements(driver, ViewInvoicesPage.class);
        viewInvoicesPage.clickInvoice();

        driver.manage().timeouts().implicitlyWait(2L, TimeUnit.SECONDS);

        viewInvoicesPage.verifyShownInvoice();
    }
}
