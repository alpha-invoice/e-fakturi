package interns.invoices.models;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Bulgarian: Ñ„Ð°ÐºÑ‚ÑƒÑ€Ð° {@link JsonIdentityInfo} annotation is used every
 * time Jackson serializes your object. It will add an ID to it, so that it
 * won't entirely "scan" the object again every time. We use it to prevent
 * infinite recursion while having chained relations between objects UserInfo ->
 * Company -> Invoice -> Company
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@invoiceId")
public class Invoice extends BaseEntity {
    /**
     * Bulgarian: Ð½Ð¾Ð¼ÐµÑ€ Ð½Ð° Ñ„Ð°ÐºÑ‚ÑƒÑ€Ð°
     */
    @NotNull
    private String invoiceNumber;

    @NotNull
    private LocalDate date;

    /**
     * Bulgarian: Ð´Ð¾Ñ�Ñ‚Ð°Ð²Ñ‡Ð¸Ðº
     */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Company sender;

    /**
     * Bulgarian: Ð¿Ð¾Ð»ÑƒÑ‡Ð°Ñ‚ÐµÐ»
     */
    @OneToOne
    @NotNull
    private Company recipient;

    /**
     * Bulgarian: Ð½Ð°Ð¸Ð¼ÐµÐ½Ð¾Ð²Ð°Ð½Ð¸Ñ� Ð½Ð° Ñ�Ñ‚Ð¾ÐºÐ¸Ñ‚Ðµ/ÑƒÑ�Ð»ÑƒÐ³Ð¸Ñ‚Ðµ
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
