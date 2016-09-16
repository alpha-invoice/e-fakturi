package bg.registryagency.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import bg.registryagency.exception.InvalidDeedException;
import bg.registryagency.schemas.deedv2.DeedType;
import bg.registryagency.utility.DeedTypeParser;

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

    @Transient
    private LocalDate dateLastModified;

    public BrraCompany() {

    }

    public static BrraCompany createInstance(DeedType dt, LocalDate date) throws InvalidDeedException {
        BrraCompany company = new BrraCompany();
        DeedTypeParser deedTypeParser = new DeedTypeParser(dt);

        company.setEik(deedTypeParser.getEik());
        company.setName(deedTypeParser.getName());
        company.setDateLastModified(date);
        company.setMol(deedTypeParser.getMol());
        company.setAddress(deedTypeParser.getAddress());

        return company;
    }

    public BrraCompany updateCompanyData(BrraCompany newCompanyData) {
        String address = newCompanyData.getAddress();
        if (address != null && !address.trim().isEmpty()) {
            this.address = address;
        }
        String name = newCompanyData.getName();
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        String mol = newCompanyData.getMol();
        if (mol != null && !mol.trim().isEmpty()) {
            this.mol = mol;
        }
        LocalDate dateLastModified = newCompanyData.getDateLastModified();
        if (dateLastModified != null) {
            this.dateLastModified = dateLastModified;
        }
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEik() {
        return eik;
    }

    public void setEik(String eik) {
        this.eik = eik;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(LocalDate dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    @Override
    public String toString() {
        return "BrraCompany [id=" + id + ", eik=" + eik + ", name=" + name + ", mol=" + mol + ", address=" + address
                + "dateModified: " + dateLastModified + "]";
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((dateLastModified == null) ? 0 : dateLastModified.hashCode());
        result = prime * result + ((eik == null) ? 0 : eik.hashCode());
        result = prime * result + ((mol == null) ? 0 : mol.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BrraCompany other = (BrraCompany) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (dateLastModified == null) {
            if (other.dateLastModified != null)
                return false;
        } else if (!dateLastModified.equals(other.dateLastModified))
            return false;
        if (eik == null) {
            if (other.eik != null)
                return false;
        } else if (!eik.equals(other.eik))
            return false;
        if (mol == null) {
            if (other.mol != null)
                return false;
        } else if (!mol.equals(other.mol))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
