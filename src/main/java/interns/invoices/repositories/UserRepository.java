package interns.invoices.repositories;

import interns.invoices.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Standard repository interface which is
 * injected via component scan in order to have access
 * to the CRUD methods.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
