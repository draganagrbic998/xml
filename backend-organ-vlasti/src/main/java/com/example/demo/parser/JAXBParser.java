package com.example.demo.parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.dom.DOMResult;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
public class JAXBParser {
	
	public Object unmarshal(Document document, Class<?> cl) throws JAXBException {
		return JAXBContext.newInstance(cl).createUnmarshaller().unmarshal(document);
	}
	
	public Document marshal(Object obj) throws JAXBException {
	    DOMResult result = new DOMResult();
	    JAXBContext.newInstance(obj.getClass()).createMarshaller().marshal(obj, result);
	    return (Document) result.getNode();
	}
	
}
