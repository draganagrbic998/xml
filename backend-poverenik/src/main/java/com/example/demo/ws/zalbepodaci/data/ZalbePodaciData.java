
package com.example.demo.ws.zalbepodaci.data;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

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

    public BigInteger getZalbeCutanja() {
        return zalbeCutanja;
    }

    public void setZalbeCutanja(BigInteger value) {
        this.zalbeCutanja = value;
    }

    public BigInteger getZalbeDelimicnosti() {
        return zalbeDelimicnosti;
    }

    public void setZalbeDelimicnosti(BigInteger value) {
        this.zalbeDelimicnosti = value;
    }

    public BigInteger getZalbeOdbijanja() {
        return zalbeOdbijanja;
    }

    public void setZalbeOdbijanja(BigInteger value) {
        this.zalbeOdbijanja = value;
    }

}
