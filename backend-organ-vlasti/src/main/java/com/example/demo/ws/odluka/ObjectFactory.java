
package com.example.demo.ws.odluka;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _GetOdlukaRequest_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaRequest");
    private final static QName _GetOdlukaResponse_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaResponse");

    public ObjectFactory() {
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaRequest")
    public JAXBElement<String> createGetOdlukaRequest(String value) {
        return new JAXBElement<String>(_GetOdlukaRequest_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaResponse")
    public JAXBElement<String> createGetOdlukaResponse(String value) {
        return new JAXBElement<String>(_GetOdlukaResponse_QNAME, String.class, null, value);
    }

}
