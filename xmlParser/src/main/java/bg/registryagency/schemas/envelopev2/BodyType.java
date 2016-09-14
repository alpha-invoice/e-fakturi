//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.11 at 06:59:59 PM EEST 
//


package bg.registryagency.schemas.envelopev2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import bg.registryagency.schemas.deedv2.DeedType;


/**
 * <p>Java class for BodyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BodyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Deeds">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.registryagency.bg/schemas/deedv2}Deed" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SearchCriteria" type="{http://www.registryagency.bg/schemas/envelopev2}SearchCriteria" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BodyType", propOrder = {
    "deeds",
    "searchCriteria"
})
public class BodyType {

    @XmlElement(name = "Deeds", required = true)
    protected BodyType.Deeds deeds;
    @XmlElement(name = "SearchCriteria")
    protected SearchCriteria searchCriteria;

    /**
     * Gets the value of the deeds property.
     * 
     * @return
     *     possible object is
     *     {@link BodyType.Deeds }
     *     
     */
    public BodyType.Deeds getDeeds() {
        return deeds;
    }

    /**
     * Sets the value of the deeds property.
     * 
     * @param value
     *     allowed object is
     *     {@link BodyType.Deeds }
     *     
     */
    public void setDeeds(BodyType.Deeds value) {
        this.deeds = value;
    }

    /**
     * Gets the value of the searchCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link SearchCriteria }
     *     
     */
    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * Sets the value of the searchCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchCriteria }
     *     
     */
    public void setSearchCriteria(SearchCriteria value) {
        this.searchCriteria = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://www.registryagency.bg/schemas/deedv2}Deed" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "deed"
    })
    public static class Deeds {

        @XmlElement(name = "Deed", namespace = "http://www.registryagency.bg/schemas/deedv2")
        protected List<DeedType> deed;

        /**
         * Gets the value of the deed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the deed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDeed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DeedType }
         * 
         * 
         */
        public List<DeedType> getDeed() {
            if (deed == null) {
                deed = new ArrayList<DeedType>();
            }
            return this.deed;
        }

    }

}