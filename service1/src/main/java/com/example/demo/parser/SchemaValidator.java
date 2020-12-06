package com.example.demo.parser;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

@Component
public class SchemaValidator {

	private SchemaFactory factory;
	
	public SchemaValidator() {
		this.factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	}
	
	public Schema generateSchema(String fileName) throws SAXException {
		return this.factory.newSchema(new File(fileName));
	}
	
}
