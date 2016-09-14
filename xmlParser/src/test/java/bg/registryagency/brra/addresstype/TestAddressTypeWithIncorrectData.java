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

    private static final String FILE_PATH = "static/testxml/2010/6/20100605.xml";

    private Map<String, BrraCompany> parsedCompanies;

    @BeforeClass()
    private void setUp() {
        try {
            URI filePath = getClass().getClassLoader().getResource(FILE_PATH).toURI();
            BrraParser source = new BrraParser(filePath);
            parsedCompanies = source.parseAll();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage() + System.lineSeparator() + "Test Failed due to exception in BrraParser.");
        }
    }

    @DataProvider(name = "address output for 20100605")
    public Object[][] addressOutputForIncorrectXML() {
        return new Object[][] {
                { "117683847", "ул.ДАМЕ ГРУЕВ НОВ, гр. Русе, БЪЛГАРИЯ" },
                { "117694911", null },
                { "104645992", "ул.ШЕЙНОВО 28, гр. Горна Оряховица, БЪЛГАРИЯ, 5100" },
                { "104103644", null },
                { "200233939", "ж.к. СВЕТА ТРОИЦА, бл. 303Б, гр. София, БЪЛГАРИЯ, 1309" }
        };
    }

    @Test(dataProvider = "address output for 20100605")
    public void testSecondXMLAddresses(String id, String address) {
        Assert.assertEquals(parsedCompanies.get(id).getAddress(), address);
    }
}
