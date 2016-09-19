package interns.invoices.models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity(name = "templates")
public class InvoiceTemplate extends BaseEntity {

    @Lob
    private byte[] userInvoiceTemplate;

    @OneToOne
    private Company company;

    public byte[] getUserInvoiceTemplate() {
        return userInvoiceTemplate;
    }

    public void setUserInvoiceTemplate(byte[] userInvoiceTemplate) {
        this.userInvoiceTemplate = userInvoiceTemplate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
