//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.11 at 06:59:59 PM EEST 
//


package bg.registryagency.schemas.deedv2.fields;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Indent" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IndentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CountryID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="CountryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="CompetentAuthorityForRegistration" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RegistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IsForeignTraderText" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonType", propOrder = {
    "indent",
    "name",
    "indentType",
    "countryID",
    "countryName"
})
@XmlSeeAlso({
    SubjectType.class
})
public class PersonType {

    @XmlElement(name = "Indent", required = true)
    protected String indent;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "IndentType", required = true)
    protected String indentType;
    @XmlElement(name = "CountryID", required = true, type = Long.class, nillable = true)
    protected Long countryID;
    @XmlElement(name = "CountryName", required = true)
    protected String countryName;
    @XmlAttribute(name = "CompetentAuthorityForRegistration")
    protected String competentAuthorityForRegistration;
    @XmlAttribute(name = "RegistrationNumber")
    protected String registrationNumber;
    @XmlAttribute(name = "IsForeignTraderText")
    protected Integer isForeignTraderText;

    /**
     * Gets the value of the indent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndent() {
        return indent;
    }

    /**
     * Sets the value of the indent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndent(String value) {
        this.indent = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the indentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndentType() {
        return indentType;
    }

    /**
     * Sets the value of the indentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndentType(String value) {
        this.indentType = value;
    }

    /**
     * Gets the value of the countryID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountryID() {
        return countryID;
    }

    /**
     * Sets the value of the countryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountryID(Long value) {
        this.countryID = value;
    }

    /**
     * Gets the value of the countryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the value of the countryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryName(String value) {
        this.countryName = value;
    }

    /**
     * Gets the value of the competentAuthorityForRegistration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompetentAuthorityForRegistration() {
        return competentAuthorityForRegistration;
    }

    /**
     * Sets the value of the competentAuthorityForRegistration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompetentAuthorityForRegistration(String value) {
        this.competentAuthorityForRegistration = value;
    }

    /**
     * Gets the value of the registrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the value of the registrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrationNumber(String value) {
        this.registrationNumber = value;
    }

    /**
     * Gets the value of the isForeignTraderText property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsForeignTraderText() {
        return isForeignTraderText;
    }

    /**
     * Sets the value of the isForeignTraderText property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsForeignTraderText(Integer value) {
        this.isForeignTraderText = value;
    }

}
