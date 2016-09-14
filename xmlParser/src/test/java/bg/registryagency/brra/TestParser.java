package bg.registryagency.brra;

import java.net.URI;
import java.util.Map;

import org.junit.Test;

import bg.registryagency.BrraParser;
import bg.registryagency.model.BrraCompany;

public class TestParser {

    private Map<Long, BrraCompany> results;

    @Test
    public void testWithCorrectData() throws Exception {
        BrraParser brraParser = new BrraParser(
                new URI("file:///C:/Users/borisrosenov/Documents/invoices-remastered/xmlParser/src/main/resources/static/testxml"));
        Map<Long, BrraCompany> parsedCompanies = brraParser.parseAll();

        System.out.println(parsedCompanies);

    }

}
