package com.example.demo.parser;

import javax.xml.bind.JAXBContext;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.exception.MyException;

@Component
public class JAXBParser {
	
	public Object unmarshalFromDoc(Document document, Class<?> cl) {
		try {
			return JAXBContext.newInstance(cl).createUnmarshaller().unmarshal(document);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
