package bg.registryagency.brra;

import java.util.Map;

import org.junit.Test;

import bg.registryagency.BrraParser;
import bg.registryagency.model.BrraCompany;

public class TestParser {

    private Map<Long, BrraCompany> results;

    @Test
    public void testWithCorrectData() throws Exception {
        BrraParser brraParser = new BrraParser(
                "file:///C:/Users/borisrosenov/Documents/invoices-remastered/xmlParser/src/main/resources/static/testxml");
        brraParser.parseAll();
        Map<Long, BrraCompany> parsedCompanies = brraParser.getParsedCompanies();
        System.out.println(parsedCompanies);

    }

}
