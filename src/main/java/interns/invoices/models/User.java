package interns.invoices.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Defines a user which holds the
 * invoices created by the user.
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String email;
    /** Bulgarian: издадени фактури */
    @OneToMany
    private Set<Invoice> invoices;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
}
