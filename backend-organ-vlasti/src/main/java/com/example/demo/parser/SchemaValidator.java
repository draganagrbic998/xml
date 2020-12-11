package com.example.demo.parser;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

@Component
public class SchemaValidator {

	private SchemaFactory schemaFactory;
	
	public SchemaValidator() {
		this.schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	}
	
	public Schema generateSchema(String path) throws SAXException {
		return this.schemaFactory.newSchema(new File(path));
	}
	
}
