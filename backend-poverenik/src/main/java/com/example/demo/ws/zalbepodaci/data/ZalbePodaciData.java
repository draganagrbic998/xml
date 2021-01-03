
package com.example.demo.ws.zalbepodaci.data;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for zalbePodaci complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="zalbePodaci"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="zalbeCutanja" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *         &lt;element name="zalbeDelimicnosti" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *         &lt;element name="zalbeOdbijanja" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "zalbePodaci", propOrder = {
    "zalbeCutanja",
    "zalbeDelimicnosti",
    "zalbeOdbijanja"
})
public class ZalbePodaciData {

    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger zalbeCutanja;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger zalbeDelimicnosti;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger zalbeOdbijanja;

    /**
     * Gets the value of the zalbeCutanja property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getZalbeCutanja() {
        return zalbeCutanja;
    }

    /**
     * Sets the value of the zalbeCutanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setZalbeCutanja(BigInteger value) {
        this.zalbeCutanja = value;
    }

    /**
     * Gets the value of the zalbeDelimicnosti property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getZalbeDelimicnosti() {
        return zalbeDelimicnosti;
    }

    /**
     * Sets the value of the zalbeDelimicnosti property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setZalbeDelimicnosti(BigInteger value) {
        this.zalbeDelimicnosti = value;
    }

    /**
     * Gets the value of the zalbeOdbijanja property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getZalbeOdbijanja() {
        return zalbeOdbijanja;
    }

    /**
     * Sets the value of the zalbeOdbijanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setZalbeOdbijanja(BigInteger value) {
        this.zalbeOdbijanja = value;
    }

}
