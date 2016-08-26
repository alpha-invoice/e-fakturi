package interns.invoices.controllers;

import interns.invoices.models.Company;
import interns.invoices.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Defines a RestController class where we
 * specify the endpoint URLs for the Company service.
 * It uses an Autowired annotation for our repository
 * which tells spring boot to inject an instance of our
 * {@link CompanyRepository}.
 */
@RestController
public class CompanyRestController {
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Service URL for our
     * {@link CompanyRepository#findAll()} method
     * @return a json representation of all users found in the repository
     * or an empty collection
     */
    @RequestMapping("/companies")
    Collection<Company> getAllCompanies() {
        return this.companyRepository.findAll();
    }

    /**
     * Overloaded service URL which uses a query parameter for finding
     * a company by a given eik number.
     * @param eik a query parameter which is passed from the url
     * @return a json representation of found Company or null
     */
    @RequestMapping(value = "/companies", params = {"eik"})
    Company getCompanyByEik(@RequestParam String eik) {
        return this.companyRepository.findCompanyByEik(eik);
    }

}
