package interns.invoices.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Used for defining recipient and sender of an {@link Invoice}.
 * {@link JsonIdentityInfo} annotation is used every time
 * Jackson serializes your object. It will add an ID to it,
 * so that it won't entirely "scan" the object again every time.
 * We use it to prevent infinite recursion while having chained
 * relations between objects UserInfo -> Company -> Invoice -> Company
 */
@Entity(name = "companies")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@companyId")
public class Company extends BaseEntity {
    /** Bulgarian: Ð¸Ð¼Ðµ Ð½Ð° Ñ„Ð¸Ñ€Ð¼Ð° */
    private String name;
    /** Bulgarian: ÐœÐžÐ› */
    private String mol;
    /** Bulgarian: Ð•Ð˜Ðš */
    @Column(unique = true)
    @Length(min = 9, max = 9)
    private String eik;
    /** Bulgarian: Ñ€ÐµÐ³Ð¸Ñ�Ñ‚Ñ€Ð¸Ñ€Ð°Ð½ Ð¿Ð¾ Ð”Ð”Ð¡ */
    private boolean isVATRegistered;
    /** Bulgarian: Ð°Ð´Ñ€ÐµÑ� Ð½Ð° Ñ„Ð¸Ñ€Ð¼Ð°Ñ‚Ð° */
    private String address;
    /** Bulgarian: Ð¸Ð·Ð´Ð°Ð´ÐµÐ½Ð¸ Ñ„Ð°ÐºÑ‚ÑƒÑ€Ð¸ Ð¾Ñ‚ Ñ„Ð¸Ñ€Ð¼Ð° */
    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Invoice> issuedInvoices;
    /** Bulgarian: Ð¿Ð¾Ñ‚Ñ€ÐµÐ±Ð¸Ñ‚ÐµÐ»Ñ�, Ð·Ð°Ð¿Ð¸Ñ�Ð°Ð» Ñ„Ð¸Ñ€Ð¼Ð°Ñ‚Ð° */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserInfo owner;

    @OneToOne(mappedBy = "company")
    private InvoiceTemplate template;

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

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }

    public InvoiceTemplate getTemplate() {
        return template;
    }

    public void setTemplate(InvoiceTemplate template) {
        this.template = template;
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
