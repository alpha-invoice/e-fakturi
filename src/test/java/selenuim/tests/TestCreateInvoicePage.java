package selenuim.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenuim.tests.enums.BROWSER_TYPE;
import selenuim.tests.factories.BrowserFactory;
import selenuim.tests.objects.pages.CreateInvoicePage;

import java.util.concurrent.TimeUnit;
import static selenuim.tests.objects.pages.CreateInvoicePage.CREATE_INVOICE_URL;

@Test
public class TestCreateInvoicePage {
    private WebDriver driver;

    /**
     * Before every test we start a web driver. Using factory design pattern to
     * start different browser.
     */
    @BeforeMethod
    public void setUp() {
        driver = BrowserFactory.getBrowser(BROWSER_TYPE.CHROME);
    }

    /**
     * After every test stop's the web driver.
     */
    @AfterMethod
    public void tearDown() {
        driver.close();
    }

    /**
     * Negative test, navigates to create/invoice url and creates invoice with
     * incorrect credentials and verify that the validation messages is displayed.
     */
    @Test(dataProvider = "incorrectCredentials")
    public void testCreateInvoiceWithIncorrectInvoiceNumberAndSenderEik(String invoiceNumber, String senderName, String senderMol, String senderAddress,
            String senderEik, String recipientName, String recipientMol, String recipientAddress,
            String recipientEik, String description, String quantity, String priceWithoutVAT){
        driver.get(CREATE_INVOICE_URL);
        driver.manage().timeouts().implicitlyWait(20L,TimeUnit.SECONDS);
        CreateInvoicePage createInvoicePage = PageFactory.initElements(driver, CreateInvoicePage.class);
        createInvoicePage.createInvoice(invoiceNumber,senderName,senderMol,senderAddress,senderEik,recipientName,
                recipientMol,recipientAddress,recipientEik,description,quantity,priceWithoutVAT);
        createInvoicePage.verifyNotCorrectInvoiceCredentials(driver);
    }

    /**
     * Positive test, navigates to create/invoice url and creates invoice with
     * correct credentials and verify's it
     */
    @Test(dataProvider = "correctCredentials")
    public void testCreateInvoiceWithCorrectCredentials(String invoiceNumber, String senderName, String senderMol, String senderAddress,
            String senderEik, String recipientName, String recipientMol, String recipientAddress,
            String recipientEik, String description, String quantity, String priceWithoutVAT){
        driver.get(CREATE_INVOICE_URL);
        driver.manage().timeouts().implicitlyWait(20L,TimeUnit.SECONDS);
        CreateInvoicePage createInvoicePage = PageFactory.initElements(driver, CreateInvoicePage.class);
        createInvoicePage.createInvoice(invoiceNumber,senderName,senderMol,senderAddress,senderEik,recipientName,
                recipientMol,recipientAddress,recipientEik,description,quantity,priceWithoutVAT);
        createInvoicePage.verifyCorrectInvoiceCredentials(driver);
    }

    @DataProvider
    public Object[][] incorrectCredentials() {
        return new Object[][] {
                {"1234567", "Varna Cars","Петър Петров","гр. Варна","77777","Monro 2000",
                "Кирил Димитров", "гр. Варна", "555555555", "Bmw 760Li", "1", "150000 лв."}
        };
    }

    @DataProvider
    public Object[][] correctCredentials() {
        return new Object[][] {
                {"1234567890", "Comers EOOD","Иван Иванов","гр. София","999999999","Monro 2000",
                "Кирил Димитров", "гр. Варна", "555555555", "Dacia Logan", "5", "50000 лв."}
        };
    }
}
