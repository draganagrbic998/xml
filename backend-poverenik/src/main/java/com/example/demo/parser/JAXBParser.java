package com.example.demo.parser;

import java.io.StringReader;
import java.io.StringWriter;

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
	
	public Object unmarshalFromXml(String xml, Class<?> cl) {
		try {
			return JAXBContext.newInstance(cl).createUnmarshaller().unmarshal(new StringReader(xml));
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
	
	public String marshalToXml(Object obj) {
		try {
		    StringWriter sw = new StringWriter();
		    JAXBContext.newInstance(obj.getClass()).createMarshaller().marshal(obj, sw);
		    return sw.toString();
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
