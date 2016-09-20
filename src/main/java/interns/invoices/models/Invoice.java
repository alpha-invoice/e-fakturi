package interns.invoices.models;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Bulgarian: Ã‘â€žÃ�Â°Ã�ÂºÃ‘â€šÃ‘Æ’Ã‘â‚¬Ã�Â° {@link JsonIdentityInfo} annotation is used every
 * time Jackson serializes your object. It will add an ID to it, so that it
 * won't entirely "scan" the object again every time. We use it to prevent
 * infinite recursion while having chained relations between objects UserInfo ->
 * Company -> Invoice -> Company
 */
@Entity
public class Invoice extends BaseEntity {
    /**
     * Bulgarian: Ã�Â½Ã�Â¾Ã�Â¼Ã�ÂµÃ‘â‚¬ Ã�Â½Ã�Â° Ã‘â€žÃ�Â°Ã�ÂºÃ‘â€šÃ‘Æ’Ã‘â‚¬Ã�Â°
     */
    @NotNull
    private String invoiceNumber;

    @CreationTimestamp
    private Timestamp createdAt;

    /**
     * Bulgarian: Ã�Â´Ã�Â¾Ã‘ï¿½Ã‘â€šÃ�Â°Ã�Â²Ã‘â€¡Ã�Â¸Ã�Âº
     */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private Company sender;

    /**
     * Bulgarian: Ã�Â¿Ã�Â¾Ã�Â»Ã‘Æ’Ã‘â€¡Ã�Â°Ã‘â€šÃ�ÂµÃ�Â»
     */
    @OneToOne
    @NotNull
    private Company recipient;

    /**
     * Bulgarian: Ã�Â½Ã�Â°Ã�Â¸Ã�Â¼Ã�ÂµÃ�Â½Ã�Â¾Ã�Â²Ã�Â°Ã�Â½Ã�Â¸Ã‘ï¿½ Ã�Â½Ã�Â° Ã‘ï¿½Ã‘â€šÃ�Â¾Ã�ÂºÃ�Â¸Ã‘â€šÃ�Âµ/Ã‘Æ’Ã‘ï¿½Ã�Â»Ã‘Æ’Ã�Â³Ã�Â¸Ã‘â€šÃ�Âµ
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
        
    public Timestamp getCreatedAt() {
        return createdAt;
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
