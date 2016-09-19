package interns.invoices;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import interns.invoices.controllers.BrraCompanyRestController;
import interns.invoices.models.BrraCompany;
import interns.invoices.repositories.BrraCompanyRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PaysafeInternsInvoicesApplication.class)
@WebAppConfiguration
public class BrraCompanyTest {

    private static String validExsistingEik = "830153305";
    private static String validNonexsistingEik = "999999444";

    @Autowired
    BrraCompanyRepository brraCompanyRepository;

    @Autowired
    BrraCompanyRestController mockedController;

    @Before
    public void setupMockMvc() {
    }

    @Test
    public void testWithValidExistingEik() {
        ResponseEntity<BrraCompany> brraCompanyByEik = mockedController.getBrraCompanyByEik(validExsistingEik);
        Assert.assertEquals(brraCompanyByEik.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testWithValidNonexsistingEik() {
        ResponseEntity<BrraCompany> brraCompanyByEik = mockedController.getBrraCompanyByEik(validNonexsistingEik);
        Assert.assertEquals(brraCompanyByEik.getStatusCode(), HttpStatus.NO_CONTENT);
        Assert.assertEquals(brraCompanyByEik.getBody(), brraCompanyRepository.findByEik(validNonexsistingEik));
    }

    @Test
    public void testWithInvalidEik() {
        List<String> listOfInvalidData = Arrays.asList(
                validNonexsistingEik + "213",
                validExsistingEik.substring(0, 7),
                null, "");

        for (String invalidEik : listOfInvalidData) {
            ResponseEntity<BrraCompany> brraCompanyByEik = mockedController
                    .getBrraCompanyByEik(invalidEik);
            Assert.assertEquals(brraCompanyByEik.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

}
