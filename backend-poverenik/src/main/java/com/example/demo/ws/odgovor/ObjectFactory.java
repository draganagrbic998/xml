
package com.example.demo.ws.odgovor;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateOdgovor_QNAME = new QName("http://demo.example.com/ws/odgovor", "createOdgovor");

    public ObjectFactory() {
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/odgovor", name = "createOdgovor")
    public JAXBElement<String> createCreateOdgovor(String value) {
        return new JAXBElement<String>(_CreateOdgovor_QNAME, String.class, null, value);
    }

}
