
package com.example.demo.ws.odluka;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _GetOdlukaRequest_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaRequest");
    private final static QName _GetOdlukaResponse_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaResponse");
    private final static QName _GetOdlukaHtmlRequest_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaHtmlRequest");
    private final static QName _GetOdlukaHtmlResponse_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaHtmlResponse");
    private final static QName _GetOdlukaPdfRequest_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaPdfRequest");
    private final static QName _GetOdlukaPdfResponse_QNAME = new QName("http://demo.example.com/ws/odluka", "getOdlukaPdfResponse");

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

    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaHtmlRequest")
    public JAXBElement<String> createGetOdlukaHtmlRequest(String value) {
        return new JAXBElement<String>(_GetOdlukaHtmlRequest_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaHtmlResponse")
    public JAXBElement<String> createGetOdlukaHtmlResponse(String value) {
        return new JAXBElement<String>(_GetOdlukaHtmlResponse_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaPdfRequest")
    public JAXBElement<String> createGetOdlukaPdfRequest(String value) {
        return new JAXBElement<String>(_GetOdlukaPdfRequest_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/odluka", name = "getOdlukaPdfResponse")
    public JAXBElement<byte[]> createGetOdlukaPdfResponse(byte[] value) {
        return new JAXBElement<byte[]>(_GetOdlukaPdfResponse_QNAME, byte[].class, null, ((byte[]) value));
    }

}
