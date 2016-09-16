package interns.invoices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import interns.invoices.models.BrraCompany;
import interns.invoices.repositories.BrraCompanyRepository;

@RestController
@RequestMapping(path = "api/companies/brra")
public class BrraCompanyRestController {

    @Autowired
    private BrraCompanyRepository brraCompanyRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BrraCompany> getBrraCompanyByEik(@Param("eik") String eik) {
        return ResponseEntity.ok(brraCompanyRepository.findBrraCompanyByEik(eik));
    }

}
