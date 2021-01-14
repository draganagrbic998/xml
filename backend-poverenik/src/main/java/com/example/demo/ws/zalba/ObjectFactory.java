package com.example.demo.ws.zalba;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private final static QName _OtkaziZalbu_QNAME = new QName("http://demo.example.com/ws/zalba", "otkaziZalbu");

	public ObjectFactory() {
	}

	@XmlElementDecl(namespace = "http://demo.example.com/ws/zalba", name = "otkaziZalbu")
	public JAXBElement<String> createOtkaziZalbu(String value) {
		return new JAXBElement<String>(_OtkaziZalbu_QNAME, String.class, null, value);
	}

}
