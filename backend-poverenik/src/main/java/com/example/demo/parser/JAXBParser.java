package com.example.demo.parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

@Component
public class JAXBParser {
	
	public Object unmarshal(XMLResource resource, Class<?> cl) throws JAXBException, XMLDBException {
		JAXBContext context = JAXBContext.newInstance(cl);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller.unmarshal(resource.getContentAsDOM());
	}
	
}
