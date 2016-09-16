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
            JSONObject json = new JSONObject(request);
            eik = json.get(EIK_KEY).toString();
        } catch (JSONException e) {
            return ResponseEntity.badRequest().body(null);
        }
        if (eik == null || eik.length() != 9 || !isValidFormat(eik)) {
            return ResponseEntity.badRequest().body(null);
        }
        BrraCompany brra = brraCompanyRepository.findByEik(eik);
        if (brra != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(brra);
    }

    private boolean isValidFormat(String input) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
