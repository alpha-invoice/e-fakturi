package interns.invoices.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import interns.invoices.models.Company;
import interns.invoices.models.UserInfo;

/**
 * Standard repository interface which is
 * injected via component scan in order to have access
 * to the CRUD methods.
 */
@RepositoryRestResource(exported = false)
public interface CompanyRepository extends JpaRepository<Company, Long> {
    /**
     * Search method which finds and returns a C
     * Company by a specified EIK number.
     * @param eik the unique EIK number of a company
     * @return the found company in the database or null
     */
    Company findCompanyByEik(String eik);
}
