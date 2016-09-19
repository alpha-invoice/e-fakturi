package interns.invoices.models;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Defines a user which holds the companies registered by him.
 * {@link JsonIdentityInfo} annotation is used every time Jackson serializes
 * your object. It will add an ID to it, so that it won't entirely "scan" the
 * object again every time. We use it to prevent infinite recursion while having
 * chained relations between objects UserInfo -> Company -> Invoice -> Company
 *
 * Additionally each user may have a custom defined template, which will be used
 * to generate the invoices that the specific user will issue.
 */
@Entity(name = "users")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@userId")
public class UserInfo {
    @Id
    @JsonProperty("user_id")
    private String id;

    @NotNull
    private String email;

    /** Bulgarian: записани компании от които потребителя издава фактури */
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Company> myCompanies;

    @Lob
    private byte[] userInvoiceTemplate;

    @Transient
    private String accessToken;

    public UserInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {

        return "UserInfo{" +
                "email='" + email + '\'' +
                ", myCompanies=" + myCompanies +
                '}';
    }
}
