
package com.example.demo.ws.zahtev;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _GetZahtevRequest_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevRequest");
    private final static QName _GetZahtevResponse_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevResponse");
    private final static QName _GetZahtevHtmlRequest_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevHtmlRequest");
    private final static QName _GetZahtevHtmlResponse_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevHtmlResponse");
    private final static QName _GetZahtevPdfRequest_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevPdfRequest");
    private final static QName _GetZahtevPdfResponse_QNAME = new QName("http://demo.example.com/ws/zahtev", "getZahtevPdfResponse");

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

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zahtev", name = "getZahtevHtmlRequest")
    public JAXBElement<String> createGetZahtevHtmlRequest(String value) {
        return new JAXBElement<String>(_GetZahtevHtmlRequest_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zahtev", name = "getZahtevHtmlResponse")
    public JAXBElement<String> createGetZahtevHtmlResponse(String value) {
        return new JAXBElement<String>(_GetZahtevHtmlResponse_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zahtev", name = "getZahtevPdfRequest")
    public JAXBElement<String> createGetZahtevPdfRequest(String value) {
        return new JAXBElement<String>(_GetZahtevPdfRequest_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zahtev", name = "getZahtevPdfResponse")
    public JAXBElement<byte[]> createGetZahtevPdfResponse(byte[] value) {
        return new JAXBElement<byte[]>(_GetZahtevPdfResponse_QNAME, byte[].class, null, ((byte[]) value));
    }

}
