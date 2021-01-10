
package com.example.demo.ws.resenje;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateResenje_QNAME = new QName("http://demo.example.com/ws/resenje", "createResenje");

    public ObjectFactory() {
    }

    @XmlElementDecl(namespace = "http://demo.example.com/ws/resenje", name = "createResenje")
    public JAXBElement<String> createCreateResenje(String value) {
        return new JAXBElement<String>(_CreateResenje_QNAME, String.class, null, value);
    }

}
