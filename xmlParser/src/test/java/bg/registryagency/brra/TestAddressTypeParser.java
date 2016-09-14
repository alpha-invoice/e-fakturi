package bg.registryagency.brra;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import bg.registryagency.BrraParser;
import bg.registryagency.model.BrraCompany;

@Test
public class TestAddressTypeParser {

    private Map<Long, BrraCompany> parsedCompanies;

    @BeforeClass
    private void setUp(){
        BrraParser source = new BrraParser(
                "file:///C:/Users/dimitarpahnev/workspace/Invoices/invoices-brra/invoices-remastered/xmlParser/src/main/resources/static/testxml/2008");
        try {
            parsedCompanies = source.parseAll();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage() + System.lineSeparator() + "Test Failed due to exception in BrraParser.");
        }
    }

    @DataProvider(name = "address output for 2008")
    public Object[][] provideCorrectAddressStrings() {
        return new Object[][] {
                { 117683847L, "ул.ДАМЕ ГРУЕВ 3, гр. Русе, БЪЛГАРИЯ, 7015" },
                { 117694911L, "ул. ДАМЕ ГРУЕВ 3, гр. Русе, БЪЛГАРИЯ, 7015" },
                { 104645992L, "ул.ШЕЙНОВО 28, гр. Горна Оряховица, БЪЛГАРИЯ, 5100" },
                { 200233939L, "ул. Сезам 17, ж.к. СВЕТА ТРОИЦА, бл. 303Б, гр. София, БЪЛГАРИЯ, 1309" },
                { 104103644L, "КИРИЛ Д.АВРАМОВ 32, гр. Свищов, БЪЛГАРИЯ, 5250" }
        };
    }

    @Test(dataProvider = "address output for 2008")
    public void testAddress(Long id, String address) {

        Assert.assertEquals(parsedCompanies.get(id).getAddress(), address);
    }
}
