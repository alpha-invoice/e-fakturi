package interns.invoices.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import interns.invoices.models.BrraCompany;
import interns.invoices.repositories.BrraCompanyRepository;

@RestController
@RequestMapping(path = "api/companies/brra")
public class BrraCompanyRestController {

    private static final String EIK_KEY = "eik";

    @Autowired
    private BrraCompanyRepository brraCompanyRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BrraCompany> getBrraCompanyByEik(@RequestBody String request) {
        String eik;
        try {
            eik = extractEikFromReqest(request);
        } catch (JSONException e) {
            return ResponseEntity.badRequest().body(null);
        }
        if (!isValidInput(eik)) {
            return ResponseEntity.badRequest().body(null);
        }
        BrraCompany brra = brraCompanyRepository.findByEik(eik);
        if (brra != null) {
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
        if (eik != null && eik.length() == 9 || isNumber(eik)) {
            return true;
        }
        return false;
    }

    /**
     * Parse request json and extract the value of eik
     * 
     * @param request
     *            json string.
     * @return the value of eik from request.
     * @throws JSONException
     *             if the request format is not correct and can not parse eik
     *             from it.
     */
    private String extractEikFromReqest(String request) throws JSONException {
        JSONObject json = new JSONObject(request);
        return json.get(EIK_KEY).toString();

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
