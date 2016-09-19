package interns.invoices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import interns.invoices.models.BrraCompany;
import interns.invoices.repositories.BrraCompanyRepository;

@RestController
@RequestMapping()
public class BrraCompanyRestController {

    @Autowired
    private BrraCompanyRepository brraCompanyRepository;

    @RequestMapping(path = "api/companies/brra", method = RequestMethod.GET, params = { "eik" })
    public ResponseEntity<BrraCompany> getBrraCompanyByEik(@RequestParam("eik") String eik) {
        if (!isValidInput(eik)) {
            return ResponseEntity.badRequest().body(null);
        }
        BrraCompany brra = brraCompanyRepository.findByEik(eik);
        if (brra == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(brra);
    }

    /**
     * @param eik
     * @return true if the eik input is valid. Else if is missing or incorrect
     *         format
     */
    private boolean isValidInput(String eik) {
        if (eik != null && eik.length() == 9 && isNumber(eik)) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the input is a number.
     * 
     * @param input
     *            the input data
     * @return true if the input is number.False otherwise.
     */
    private boolean isNumber(String input) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
