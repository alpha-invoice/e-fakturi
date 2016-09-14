package bg.registryagency.brra.addresstype;

import java.net.URI;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import bg.registryagency.BrraParser;
import bg.registryagency.model.BrraCompany;

public class TestAddressTypeWithIncorrectData {

    private static final String FILE_FOR_2010_LOCATION = "file:///C:/Users/dimitarpahnev/workspace/Invoices/invoices-brra/invoices-remastered/xmlParser/src/main/resources/static/testxml/2010";
    private Map<String, BrraCompany> parsedCompanies;

    @BeforeClass()
    private void setUp() {
        try {
            BrraParser source = new BrraParser(new URI(FILE_FOR_2010_LOCATION));
            parsedCompanies = source.parseAll();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage() + System.lineSeparator() + "Test Failed due to exception in BrraParser.");
        }
    }

    @DataProvider(name = "address output for 20100605")
    public Object[][] addressOutputForIncorrectXML() {
        return new Object[][] {
                { 117683847L, "ул.ДАМЕ ГРУЕВ НОВ, гр. Русе, БЪЛГАРИЯ" },
                { 117694911L, null },
                { 104645992L, "ул.ШЕЙНОВО 28, гр. Горна Оряховица, БЪЛГАРИЯ, 5100" },
                { 104103644L, null },
                { 200233939L, "ж.к. СВЕТА ТРОИЦА, бл. 303Б, гр. София, БЪЛГАРИЯ, 1309" }
        };
    }

    @Test(dataProvider = "address output for 20100605")
    public void testSecondXMLAddresses(Long id, String address) {
        Assert.assertEquals(parsedCompanies.get(id).getAddress(), address);
    }
}
