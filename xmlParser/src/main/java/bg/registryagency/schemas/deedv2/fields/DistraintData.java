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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{http://www.registryagency.bg/schemas/deedv2/Fields}Subject"/>
 *         &lt;element ref="{http://www.registryagency.bg/schemas/deedv2/Fields}Address"/>
 *         &lt;element ref="{http://www.registryagency.bg/schemas/deedv2/Fields}Price"/>
 *       &lt;/all>
 *       &lt;attGroup ref="{http://www.registryagency.bg/schemas/deedv2/Fields}RecordCommonAttributes"/>
 *       &lt;attGroup ref="{http://www.registryagency.bg/schemas/deedv2/Fields}FieldCommonAttributes"/>
 *       &lt;attribute name="CourtLegalExecutor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="CaseNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Court" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LegalExecutor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ADV" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IncomingAmount" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RemainingAmount" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EnterprisesLikeCombination" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SeparateAssets" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="AssetsOfCompany" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "DistraintData")
public class DistraintData {

    @XmlElement(name = "Subject", required = true)
    protected SubjectType subject;
    @XmlElement(name = "Address", required = true)
    protected AddressType address;
    @XmlElement(name = "Price", required = true)
    protected PriceType price;
    @XmlAttribute(name = "CourtLegalExecutor")
    protected String courtLegalExecutor;
    @XmlAttribute(name = "CaseNumber")
    protected String caseNumber;
    @XmlAttribute(name = "Court")
    protected String court;
    @XmlAttribute(name = "LegalExecutor")
    protected String legalExecutor;
    @XmlAttribute(name = "ADV")
    protected String adv;
    @XmlAttribute(name = "IncomingAmount")
    protected String incomingAmount;
    @XmlAttribute(name = "RemainingAmount")
    protected String remainingAmount;
    @XmlAttribute(name = "EnterprisesLikeCombination")
    protected String enterprisesLikeCombination;
    @XmlAttribute(name = "SeparateAssets")
    protected String separateAssets;
    @XmlAttribute(name = "AssetsOfCompany")
    protected String assetsOfCompany;
    @XmlAttribute(name = "RecordID", required = true)
    protected int recordID;
    @XmlAttribute(name = "GroupID", required = true)
    protected int groupID;
    @XmlAttribute(name = "RecordIncomingNumber", required = true)
    protected String recordIncomingNumber;
    @XmlAttribute(name = "FieldIdent", required = true)
    protected String fieldIdent;
    @XmlAttribute(name = "FieldOperation", required = true)
    protected FieldOperation fieldOperation;
    @XmlAttribute(name = "FieldEntryNumber", required = true)
    protected String fieldEntryNumber;
    @XmlAttribute(name = "FieldActionDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fieldActionDate;

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link SubjectType }
     *     
     */
    public SubjectType getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectType }
     *     
     */
    public void setSubject(SubjectType value) {
        this.subject = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link PriceType }
     *     
     */
    public PriceType getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceType }
     *     
     */
    public void setPrice(PriceType value) {
        this.price = value;
    }

    /**
     * Gets the value of the courtLegalExecutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourtLegalExecutor() {
        return courtLegalExecutor;
    }

    /**
     * Sets the value of the courtLegalExecutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourtLegalExecutor(String value) {
        this.courtLegalExecutor = value;
    }

    /**
     * Gets the value of the caseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseNumber() {
        return caseNumber;
    }

    /**
     * Sets the value of the caseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseNumber(String value) {
        this.caseNumber = value;
    }

    /**
     * Gets the value of the court property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourt() {
        return court;
    }

    /**
     * Sets the value of the court property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourt(String value) {
        this.court = value;
    }

    /**
     * Gets the value of the legalExecutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalExecutor() {
        return legalExecutor;
    }

    /**
     * Sets the value of the legalExecutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalExecutor(String value) {
        this.legalExecutor = value;
    }

    /**
     * Gets the value of the adv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADV() {
        return adv;
    }

    /**
     * Sets the value of the adv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADV(String value) {
        this.adv = value;
    }

    /**
     * Gets the value of the incomingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomingAmount() {
        return incomingAmount;
    }

    /**
     * Sets the value of the incomingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomingAmount(String value) {
        this.incomingAmount = value;
    }

    /**
     * Gets the value of the remainingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemainingAmount() {
        return remainingAmount;
    }

    /**
     * Sets the value of the remainingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemainingAmount(String value) {
        this.remainingAmount = value;
    }

    /**
     * Gets the value of the enterprisesLikeCombination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnterprisesLikeCombination() {
        return enterprisesLikeCombination;
    }

    /**
     * Sets the value of the enterprisesLikeCombination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnterprisesLikeCombination(String value) {
        this.enterprisesLikeCombination = value;
    }

    /**
     * Gets the value of the separateAssets property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeparateAssets() {
        return separateAssets;
    }

    /**
     * Sets the value of the separateAssets property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeparateAssets(String value) {
        this.separateAssets = value;
    }

    /**
     * Gets the value of the assetsOfCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssetsOfCompany() {
        return assetsOfCompany;
    }

    /**
     * Sets the value of the assetsOfCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssetsOfCompany(String value) {
        this.assetsOfCompany = value;
    }

    /**
     * Gets the value of the recordID property.
     * 
     */
    public int getRecordID() {
        return recordID;
    }

    /**
     * Sets the value of the recordID property.
     * 
     */
    public void setRecordID(int value) {
        this.recordID = value;
    }

    /**
     * Gets the value of the groupID property.
     * 
     */
    public int getGroupID() {
        return groupID;
    }

    /**
     * Sets the value of the groupID property.
     * 
     */
    public void setGroupID(int value) {
        this.groupID = value;
    }

    /**
     * Gets the value of the recordIncomingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordIncomingNumber() {
        return recordIncomingNumber;
    }

    /**
     * Sets the value of the recordIncomingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordIncomingNumber(String value) {
        this.recordIncomingNumber = value;
    }

    /**
     * Gets the value of the fieldIdent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldIdent() {
        return fieldIdent;
    }

    /**
     * Sets the value of the fieldIdent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldIdent(String value) {
        this.fieldIdent = value;
    }

    /**
     * Gets the value of the fieldOperation property.
     * 
     * @return
     *     possible object is
     *     {@link FieldOperation }
     *     
     */
    public FieldOperation getFieldOperation() {
        return fieldOperation;
    }

    /**
     * Sets the value of the fieldOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldOperation }
     *     
     */
    public void setFieldOperation(FieldOperation value) {
        this.fieldOperation = value;
    }

    /**
     * Gets the value of the fieldEntryNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldEntryNumber() {
        return fieldEntryNumber;
    }

    /**
     * Sets the value of the fieldEntryNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldEntryNumber(String value) {
        this.fieldEntryNumber = value;
    }

    /**
     * Gets the value of the fieldActionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFieldActionDate() {
        return fieldActionDate;
    }

    /**
     * Sets the value of the fieldActionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFieldActionDate(XMLGregorianCalendar value) {
        this.fieldActionDate = value;
    }

}
