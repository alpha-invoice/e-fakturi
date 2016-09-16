package interns.invoices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import interns.invoices.models.BrraCompany;

public interface BrraCompanyRepository extends JpaRepository<BrraCompany, Long> {

    BrraCompany findBrraCompanyByEik(String eik);
}
