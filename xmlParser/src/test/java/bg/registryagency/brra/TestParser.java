package bg.registryagency.brra;

import java.util.Map;

import org.junit.Test;

import bg.registryagency.ParserApplication;
import bg.registryagency.model.BrraCompany;

public class TestParser {

    private Map<Long, BrraCompany> results;


    @Test
    public void testWithCorrectData() throws Exception {
        ParserApplication parserApplication = new ParserApplication(
                "file:///C:/Users/dimitarpahnev/workspace/xmlParser/src/main/resources/static/testxml");
        parserApplication.start();
        Map<Long, BrraCompany> parsedCompanies = parserApplication.getParsedCompanies();
        System.out.println(parsedCompanies);

    }

}
