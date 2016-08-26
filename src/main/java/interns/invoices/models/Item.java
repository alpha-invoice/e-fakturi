package interns.invoices.models;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 * A single element of the description.
 * The VAT price is calculated if the
 * sender {@link Company#isVATRegistered}.
 */
@Embeddable
public class Item {
    /** Bulgarian: описание */
    private String description;
    /** Bulgarian: количество */
    private Integer quantity;
    /**
     * Bulgarian: цена <strong>без ДДС</strong>
     * Price of a single item without VAT
     */
    private Double priceWithoutVAT;

    public Item() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPriceWithoutVAT() {
        return priceWithoutVAT;
    }

    public void setPriceWithoutVAT(Double priceWithoutVAT) {
        this.priceWithoutVAT = priceWithoutVAT;
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", quantity=" + quantity +
                ", priceWithoutVAT=" + priceWithoutVAT +
                '}';
    }
}
