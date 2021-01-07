
package com.example.demo.ws.odluka;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.demo.ws.odluka package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateOdlukaRequest_QNAME = new QName("http://demo.example.com/ws/odluka", "createOdlukaRequest");
    private final static QName _CreateOdlukaResponse_QNAME = new QName("http://demo.example.com/ws/odluka", "createOdlukaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.demo.ws.odluka
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "createOdlukaRequest")
    public JAXBElement<String> createCreateOdlukaRequest(String value) {
        return new JAXBElement<String>(_CreateOdlukaRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "createOdlukaResponse")
    public JAXBElement<String> createCreateOdlukaResponse(String value) {
        return new JAXBElement<String>(_CreateOdlukaResponse_QNAME, String.class, null, value);
    }

}
