package interns.invoices.repositories;

import interns.invoices.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Standard repository interface which is
 * injected via component scan in order to have access
 * to the CRUD methods.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    /**
     * Search method which finds and returns a C
     * Company by a specified EIK number.
     * @param eik the unique EIK number of a company
     * @return the found company in the database or null
     */
    Company findCompanyByEik(String eik);
}
