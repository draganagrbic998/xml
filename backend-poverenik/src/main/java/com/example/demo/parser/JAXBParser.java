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
	    //JAXBContext context = JAXBContext.newInstance(obj.getClass());//ovo sam dodala
	    //Marshaller marshaller = context.createMarshaller();//ovo sam dodala
		//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);	//ovo sam dodala
		//marshaller.marshal(obj, result);//ovo sam dodala
	    JAXBContext.newInstance(obj.getClass()).createMarshaller().marshal(obj, result);
		
		//			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());

	    return (Document) result.getNode();
	}
	
}
