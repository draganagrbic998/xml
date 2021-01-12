
package com.example.demo.ws.zahtev;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _GetZahtevRequest_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevRequest");
    private final static QName _GetZahtevResponse_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevResponse");
    private final static QName _GetZahtevViewRequest_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevViewRequest");
    private final static QName _GetZahtevViewResponse_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevViewResponse");

    public ObjectFactory() {
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zahtev", name = "getZahtevRequest")
    public JAXBElement<String> createGetZahtevRequest(String value) {
        return new JAXBElement<String>(_GetZahtevRequest_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zahtev", name = "getZahtevResponse")
    public JAXBElement<String> createGetZahtevResponse(String value) {
        return new JAXBElement<String>(_GetZahtevResponse_QNAME, String.class, null, value);
    }
    
    @XmlElementDecl(namespace = "http://demo.example.com/ws/zahtev", name = "getZahtevViewRequest")
    public JAXBElement<String> createGetZahtevViewRequest(String value) {
        return new JAXBElement<String>(_GetZahtevViewRequest_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zahtev", name = "getZahtevViewResponse")
    public JAXBElement<byte[]> createGetZahtevViewResponse(byte[] value) {
        return new JAXBElement<byte[]>(_GetZahtevViewResponse_QNAME, byte[].class, null, ((byte[]) value));
    }
    
}
