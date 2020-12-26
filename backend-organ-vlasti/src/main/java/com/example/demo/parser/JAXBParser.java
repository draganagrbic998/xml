package com.example.demo.parser;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

@Component
public class JAXBParser {
	
	public Object unmarshal(String xml, Class<?> cl) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(cl);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller.unmarshal(new StringReader(xml));
	}
	
	public Object unmarshal(XMLResource xml, Class<?> cl) throws JAXBException, XMLDBException {
		JAXBContext context = JAXBContext.newInstance(cl);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller.unmarshal(xml.getContentAsDOM());
	}
	
	public OutputStream marshal(Object obj, Class<?> cl) throws JAXBException {
		OutputStream out = new ByteArrayOutputStream();
		JAXBContext context = JAXBContext.newInstance(cl);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(obj, out);
		return out;
	}
	
}
