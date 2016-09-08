package interns.invoices.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Bulgarian: фактура
 * {@link JsonIdentityInfo} annotation is used every time
 * Jackson serializes your object. It will add an ID to it,
 * so that it won't entirely "scan" the object again every time.
 * We use it to prevent infinite recursion while having chained
 * relations between objects User -> Company -> Invoice -> Company
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@invoiceId")
public class Invoice extends BaseEntity {
    /**
     * Bulgarian: номер на фактура
     */
    @NotNull
    private String invoiceNumber;

    /**
     * Bulgarian: доставчик
     */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Company sender;

    /**
     * Bulgarian: получател
     */
    @OneToOne
    @NotNull
    private Company recipient;

    /**
     * Bulgarian: наименования на стоките/услугите
     */
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
        StringBuilder recipientOutput = new StringBuilder();
        recipientOutput.append("{")
                .append("name:").append(recipient.getName())
                .append(",eik:").append(recipient.getEik())
                .append(",VAT:").append(recipient.getVATNumber())
                .append(",mol:").append(recipient.getMol())
                .append(",address:").append(recipient.getAddress())
                .append("}");

        return "Invoice{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", recipient=" + recipientOutput +
                ", items=" + items +
                '}';
    }
}
