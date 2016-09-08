package interns.invoices.models;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

/**
 * Used for defining recipient and sender of an {@link Invoice}.
 * {@link JsonIdentityInfo} annotation is used every time
 * Jackson serializes your object. It will add an ID to it,
 * so that it won't entirely "scan" the object again every time.
 * We use it to prevent infinite recursion while having chained
 * relations between objects User -> Company -> Invoice -> Company
 */
@Entity(name = "companies")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@companyId")
public class Company extends BaseEntity {
    /** Bulgarian: име на фирма */
    private String name;
    /** Bulgarian: МОЛ */
    private String mol;
    /** Bulgarian: ЕИК */
    @Column(unique = true)
    @Length(min = 9, max = 9)
    private String eik;
    /** Bulgarian: регистриран по ДДС */
    private boolean isVATRegistered;
    /** Bulgarian: адрес на фирмата */
    private String address;
    /** Bulgarian: издадени фактури от фирма */
    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Invoice> issuedInvoices;
    /** Bulgarian: потребителя, записал фирмата*/
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Company() {
    }

    /**
     * Returns VAT number for Bulgarian companies only for now.
     *
     * @return VAT number if VAT registered,
     *         empty string otherwise
     */
    public String getVATNumber() {
        if(isVATRegistered) {
            return "BG" + this.eik;
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMol() {
        return mol;
    }

    public void setMol(String mol) {
        this.mol = mol;
    }

    public String getEik() {
        return eik;
    }

    public void setEik(String eik) {
        this.eik = eik;
    }

    public boolean isVATRegistered() {
        return isVATRegistered;
    }

    public void setVATRegistered(boolean VATRegistered) {
        isVATRegistered = VATRegistered;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Invoice> getIssuedInvoices() {
        return issuedInvoices;
    }

    public void setIssuedInvoices(Set<Invoice> issuedInvoices) {
        this.issuedInvoices = issuedInvoices;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", mol='" + mol + '\'' +
                ", eik='" + eik + '\'' +
                ", isVATRegistered=" + isVATRegistered +
                ", address='" + address + '\'' +
                ", issuedInvoices=" + issuedInvoices +
                '}';
    }
}
