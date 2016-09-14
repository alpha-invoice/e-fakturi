package bg.registryagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.registryagency.model.BrraCompany;

public interface BrraCompanyRepository extends JpaRepository<BrraCompany, Long> {

}
