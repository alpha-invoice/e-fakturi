package interns.invoices.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Defines a user which holds the companies registered by him.
 * {@link JsonIdentityInfo} annotation is used every time Jackson serializes
 * your object. It will add an ID to it, so that it won't entirely "scan" the
 * object again every time. We use it to prevent infinite recursion while having
 * chained relations between objects User -> Company -> Invoice -> Company
 *
 * Additionally each user may have a custom defined template, which will be used
 * to generate the invoices that the specific user will issue.
 */
@Entity(name = "users")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@userId")
public class User extends BaseEntity {
    private String email;
    /** Bulgarian: записани компании от които потребителя издава фактури */
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Company> myCompanies;

    @Lob
    private byte[] userInvoiceTemplate;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Company> getMyCompanies() {
        return myCompanies;
    }

    public void setMyCompanies(Set<Company> myCompanies) {
        this.myCompanies = myCompanies;
    }

    public byte[] getUserInvoiceTemplate() {
        return userInvoiceTemplate;
    }

    public void setUserInvoiceTemplate(byte[] userInvoiceTemplate) {
        this.userInvoiceTemplate = userInvoiceTemplate;
    }


    @Override
    public String toString() {

        return "User{" +
                "email='" + email + '\'' +
                ", myCompanies=" + myCompanies +
                '}';
    }
}
