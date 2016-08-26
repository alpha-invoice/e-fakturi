package interns.invoices.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Used for defining recipient and sender of an
 * {@link Invoice}
 */
@Entity
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

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", mol='" + mol + '\'' +
                ", eik='" + eik + '\'' +
                ", isVATRegistered=" + isVATRegistered +
                ", address='" + address + '\'' +
                '}';
    }
}
