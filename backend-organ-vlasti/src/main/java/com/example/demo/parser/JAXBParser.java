package com.example.demo.parser;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.dom.DOMResult;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.common.MyException;

@Component
public class JAXBParser {
	
	public Object unmarshal(Document document, Class<?> cl) {
		try {
			return JAXBContext.newInstance(cl).createUnmarshaller().unmarshal(document);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public Document marshal(Object obj) {
		try {
		    DOMResult result = new DOMResult();
		    JAXBContext.newInstance(obj.getClass()).createMarshaller().marshal(obj, result);
		    return (Document) result.getNode();
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
