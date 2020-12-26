package com.example.demo.parser;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.dom.DOMResult;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

@Component
public class JAXBParser {
	
	
	public Object unmarshal(String xml, Class<?> cl) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(cl);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller.unmarshal(new StringReader(xml));
	}
	
	public Object unmarshal(Document document, Class<?> cl) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(cl);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller.unmarshal(document);
	}
	
	public Document marshal(Object obj) throws JAXBException {
		/*
		OutputStream out = new ByteArrayOutputStream();
		JAXBContext context = JAXBContext.newInstance(cl);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(obj, out);
		return out;*/
	    DOMResult res = new DOMResult();
	    JAXBContext context = JAXBContext.newInstance(obj.getClass());
	    Marshaller marshaller = context.createMarshaller();
		//marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
	    marshaller.marshal(obj, res);
	    return (Document) res.getNode();

	}
	
}
