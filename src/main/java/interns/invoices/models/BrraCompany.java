package interns.invoices.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "brra_company")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@brracompanyId")
public class BrraCompany extends BaseEntity {

    @Column(unique = true)
    private String eik;

    private String name;

    private String mol;

    private String address;

    public BrraCompany() {

    }

    /**
     * @return the eik
     */
    public String getEik() {
        return eik;
    }

    /**
     * @param eik
     *            the eik to set
     */
    public void setEik(String eik) {
        this.eik = eik;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the mol
     */
    public String getMol() {
        return mol;
    }

    /**
     * @param mol
     *            the mol to set
     */
    public void setMol(String mol) {
        this.mol = mol;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

}
