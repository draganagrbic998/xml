
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

    private final static QName _GetOdlukaRequest_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaRequest");
    private final static QName _GetOdlukaResponse_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaResponse");

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
    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaRequest")
    public JAXBElement<String> createGetOdlukaRequest(String value) {
        return new JAXBElement<String>(_GetOdlukaRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaResponse")
    public JAXBElement<String> createGetOdlukaResponse(String value) {
        return new JAXBElement<String>(_GetOdlukaResponse_QNAME, String.class, null, value);
    }

}
