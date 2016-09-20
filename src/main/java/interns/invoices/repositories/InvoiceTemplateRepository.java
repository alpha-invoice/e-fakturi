package interns.invoices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import interns.invoices.models.InvoiceRestTemplate;

/**
 * Standard repository interface which is injected via component scan in order
 * to have access to the CRUD methods.
 */
@RepositoryRestResource(exported = false)
public interface InvoiceTemplateRepository extends JpaRepository<InvoiceRestTemplate, Long> {

}
