package interns.invoices.repositories;

import interns.invoices.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Standard repository interface which is
 * injected via component scan in order to have access
 * to the CRUD methods.
 */
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
}
