package com.example.demo.parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.dom.DOMResult;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
public class JAXBParser {
	
	
	
	public Object unmarshal(Document document, Class<?> cl) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(cl);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller.unmarshal(document);
	}
	
	public Document marshal(Object obj) throws JAXBException {
	    DOMResult res = new DOMResult();
	    JAXBContext context = JAXBContext.newInstance(obj.getClass());
	    Marshaller marshaller = context.createMarshaller();	    
	    marshaller.marshal(obj, res);
	    return (Document) res.getNode();
	}
	
}
