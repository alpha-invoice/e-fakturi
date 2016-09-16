package interns.invoices.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "brra_companies")
public class BrraCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String eik;

    @Size(max = 511)
    private String name;

    private String mol;

    @Size(max = 511)
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
