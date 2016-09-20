package interns.invoices.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "templates")
public class InvoiceTemplate extends BaseEntity {

    @Lob
    private byte[] userInvoiceTemplate;

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    @JsonBackReference
    private UserInfo userInfo;

    private String name;

    public byte[] getUserInvoiceTemplate() {
        return userInvoiceTemplate;
    }

    public void setUserInvoiceTemplate(byte[] userInvoiceTemplate) {
        this.userInvoiceTemplate = userInvoiceTemplate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
