package bg.registryagency.brra;

import java.net.URI;
import java.time.LocalDate;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import bg.registryagency.BrraParser;
import bg.registryagency.model.BrraCompany;

public class TestParser {

    private static final String FILE_PATH = "static/testxml";

    private static final LocalDate dateModified = LocalDate.of(2010, 6, 5);

    private Map<String, BrraCompany> parsedCompanies;

    @BeforeClass()
    public void setUp() {
        try {
            URI filePath = getClass().getClassLoader().getResource(FILE_PATH).toURI();
            BrraParser source = new BrraParser(filePath);
            parsedCompanies = source.parseAll();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage() + System.lineSeparator() + "Test Failed due to exception in BrraParser.");
        }
    }

    @Test(dataProvider = "expected brra company info")
    public void testWithCorrectData(String id, String mol) throws Exception {
        BrraCompany company = parsedCompanies.get(id);
        Assert.assertEquals(company.getMol(), mol);

        Assert.assertTrue(company.getDateLastModified().isEqual(dateModified));
    }

    @DataProvider(name = "expected brra company info")
    public Object[][] companyOutput() {
        return new Object[][] {
                { "104645992", "МАРИН АНГЕЛОВ ЧОМАКОВ" },
                { "117694911", "Кольо Иванов  Иванов" },
                { "200233939", "ЕМИЛ ЙОРДАНОВ ПАНДОВ" },
                { "104103644", "МИЛЕНА ЛЮБЕНОВА ЦОЛОВСКА" },
                { "117683847", "ЕМИЛ ТОНЧЕВ ГЕОРГИЕВ" }
        };
    }

}
