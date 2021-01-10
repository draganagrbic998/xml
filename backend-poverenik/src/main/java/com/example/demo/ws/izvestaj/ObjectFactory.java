
package com.example.demo.ws.izvestaj;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateIzvestaj_QNAME = new QName("http://demo.example.com/ws/izvestaj", "createIzvestaj");

    public ObjectFactory() {
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/izvestaj", name = "createIzvestaj")
    public JAXBElement<String> createCreateIzvestaj(String value) {
        return new JAXBElement<String>(_CreateIzvestaj_QNAME, String.class, null, value);
    }

}
