
package com.example.demo.ws.zalba;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateZalba_QNAME = new QName("http://demo.example.com/ws/zalba", "createZalba");
    private final static QName _ObustaviZalba_QNAME = new QName("http://demo.example.com/ws/zalba", "obustaviZalba");
    private final static QName _OdustaniZalba_QNAME = new QName("http://demo.example.com/ws/zalba", "odustaniZalba");

    public ObjectFactory() {
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zalba", name = "createZalba")
    public JAXBElement<String> createCreateZalba(String value) {
        return new JAXBElement<String>(_CreateZalba_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zalba", name = "obustaviZalba")
    public JAXBElement<String> createObustaviZalba(String value) {
        return new JAXBElement<String>(_ObustaviZalba_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/zalba", name = "odustaniZalba")
    public JAXBElement<String> createOdustaniZalba(String value) {
        return new JAXBElement<String>(_OdustaniZalba_QNAME, String.class, null, value);
    }

}
