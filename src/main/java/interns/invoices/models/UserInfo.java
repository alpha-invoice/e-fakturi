package interns.invoices.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class UserInfo {
    @Id
    @JsonProperty("user_id")
    private String id;

    @NotNull
    private String email;

    /**
     * Bulgarian: Ð·Ð°Ð¿Ð¸Ñ�Ð°Ð½Ð¸ ÐºÐ¾Ð¼Ð¿Ð°Ð½Ð¸Ð¸ Ð¾Ñ‚ ÐºÐ¾Ð¸Ñ‚Ð¾
     * Ð¿Ð¾Ñ‚Ñ€ÐµÐ±Ð¸Ñ‚ÐµÐ»Ñ� Ð¸Ð·Ð´Ð°Ð²Ð° Ñ„Ð°ÐºÑ‚ÑƒÑ€Ð¸
     */
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Company> myCompanies;

    @Transient
    private String accessToken;

    public UserInfo() {
        this.myCompanies = new HashSet<>();
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

    public void addCompany(Company company) {
        this.myCompanies.add(company);
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
