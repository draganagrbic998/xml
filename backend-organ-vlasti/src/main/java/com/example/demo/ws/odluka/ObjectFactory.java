
package com.example.demo.ws.odluka;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _GetOdlukaRequest_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaRequest");
    private final static QName _GetOdlukaResponse_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaResponse");
    private final static QName _GetOdlukaViewRequest_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaViewRequest");
    private final static QName _GetOdlukaViewResponse_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaViewResponse");

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

    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaViewRequest")
    public JAXBElement<String> createGetOdlukaViewRequest(String value) {
        return new JAXBElement<String>(_GetOdlukaViewRequest_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaViewResponse")
    public JAXBElement<byte[]> createGetOdlukaViewResponse(byte[] value) {
        return new JAXBElement<byte[]>(_GetOdlukaViewResponse_QNAME, byte[].class, null, ((byte[]) value));
    }

}
