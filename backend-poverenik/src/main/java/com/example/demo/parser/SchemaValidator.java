package com.example.demo.parser;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.SchemaFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.common.MyException;

@Component
public class SchemaValidator {

	private SchemaFactory schemaFactory;
	
	public SchemaValidator() {
		this.schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	}
	
	public void validate(Document document, String path) {
		try {
			this.schemaFactory.newSchema(new File(path)).newValidator().validate(new DOMSource(document));
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
