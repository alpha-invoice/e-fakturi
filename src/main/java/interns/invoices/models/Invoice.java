package interns.invoices.models;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Bulgarian: фактура
 */
@Entity
public class Invoice extends BaseEntity {
    /** Bulgarian: номер на фактура */
    @NotNull
    private String invoiceNumber;

    /** Bulgarian: доставчик */
    @OneToOne
    @NotNull
    private Company sender;

    /** Bulgarian: получател */
    @OneToOne
    @NotNull
    private Company recipient;

    /** Bulgarian: наименования на стоките/услугите */
    @ElementCollection()
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull
    private List<Item> items;

    public Invoice() {
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Company getSender() {
        return sender;
    }

    public void setSender(Company sender) {
        this.sender = sender;
    }

    public Company getRecipient() {
        return recipient;
    }

    public void setRecipient(Company recipient) {
        this.recipient = recipient;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", items=" + items +
                '}';
    }
}
