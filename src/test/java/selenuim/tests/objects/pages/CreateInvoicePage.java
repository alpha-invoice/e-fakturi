package selenuim.tests.objects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Class for the page for creating invoices. Contains the page URL (which
 * is currently localhost), the web elements gets by id each detail for
 * the invoice.
 */
public class CreateInvoicePage {
    public static final String CREATE_INVOICE_URL = "http://localhost:4200/create/invoice";

    private WebElement invoiceN;

    private WebElement senderName;

    private WebElement senderMol;

    private WebElement senderAddress;

    private WebElement senderEik;

    private WebElement recipientName;

    private WebElement recipientMol;

    private WebElement recipientAddress;

    private WebElement recipientEik;

    private WebElement description;

    private WebElement quantity;

    private WebElement priceWithoutVAT;

    public void createInvoice(String invoiceNumber, String senderName, String senderMol, String senderAddress,
            String senderEik, String recipientName, String recipientMol, String recipientAddress,
            String recipientEik, String description, String quantity, String priceWithoutVAT) {
        this.invoiceN.sendKeys(invoiceNumber);
        this.senderName.sendKeys(senderName);
        this.senderMol.sendKeys(senderMol);
        this.senderAddress.sendKeys(senderAddress);
        this.senderEik.sendKeys(senderEik);
        this.recipientName.sendKeys(recipientName);
        this.recipientMol.sendKeys(recipientMol);
        this.recipientAddress.sendKeys(recipientAddress);
        this.recipientEik.sendKeys(recipientEik);
        this.description.sendKeys(description);
        this.quantity.sendKeys(quantity);
        this.priceWithoutVAT.sendKeys(priceWithoutVAT);
    }

    /**
     * Verify the creating of invoice with 2 wrong credentials,
     * expects 2 messages to be displayed.
     */
    public void verifyNotCorrectInvoiceCredentials(WebDriver driver){
        String incorrectInvoiceNumberMessage = "Номерът на фактурата трябва да се с дължина 10 цифри.";
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/app-root/invoice-form/div[1]")).getText().equals(incorrectInvoiceNumberMessage));
        String incorrectSenderEikMessage = "ЕИК номерът не отговаря на изискванията, трябва да съдържа 9 цифри.";
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/app-root/invoice-form/div[2]")).getText().equals(incorrectSenderEikMessage));
    }

    /**
     * Verify the creating of invoice with correct credentials,
     * expects a save button to be displayed.
     */
    public void verifyCorrectInvoiceCredentials(WebDriver driver){
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"saveButton\"]/div/div/button")).getText().contains("Запази"));
    }
}
