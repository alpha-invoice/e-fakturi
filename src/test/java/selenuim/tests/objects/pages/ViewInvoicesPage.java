package selenuim.tests.objects.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Class for the page for viewing all of the previous invoices per user,
 * implementation for Page Object Design Pattern. Contains the page URL (which
 * is currently localhost), the web elements needed to check if all of the
 * invoices are displayed.
 */
public class ViewInvoicesPage {
    public static final String URL = "http://localhost:4200/invoices";

    @FindBy(id = "invoice 0")
    private WebElement invoiceNumber;

    private WebElement sender;

    private WebElement recipient;

    private WebElement items;

    /**
     * Method that clicks on the first of the invoices.
     */
    public void clickInvoice() {
        this.invoiceNumber.click();
    }

    /**
     * This method verifies that the information for the sender, recipient and
     * all of the items are displayed.
     */
    public void verifyShownInvoice() {
        assertElementIsDisplayed(sender);
        assertElementIsDisplayed(recipient);
        assertElementIsDisplayed(items);
    }

    /**
     * Helper method used to assert that the given element is displayed.
     *
     * @param element
     *            It can be the WebElement for the sender, recipient or item.
     */
    private void assertElementIsDisplayed(WebElement element) {
        Assert.assertTrue(
                element.isDisplayed());
    }
}