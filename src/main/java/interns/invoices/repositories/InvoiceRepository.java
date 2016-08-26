package interns.invoices.repositories;

import interns.invoices.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Standard repository interface which is
 * injected via component scan in order to have access
 * to the CRUD methods.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
