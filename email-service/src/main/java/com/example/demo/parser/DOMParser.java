package com.example.demo.parser;

import static org.apache.xerces.jaxp.JAXPConstants.JAXP_SCHEMA_LANGUAGE;
import static org.apache.xerces.jaxp.JAXPConstants.W3C_XML_SCHEMA;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.example.demo.exception.MyException;

@Component
public class DOMParser {
		
	private DocumentBuilderFactory builderFactory;
	
	public DOMParser() {
		super();
		this.builderFactory = DocumentBuilderFactory.newInstance();
		this.builderFactory.setNamespaceAware(true);
		this.builderFactory.setIgnoringComments(true);
		this.builderFactory.setIgnoringElementContentWhitespace(true);
		this.builderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
	}
	
	public Document buildDocument(String xml) {
		try {
			DocumentBuilder builder = this.builderFactory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xml)));
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
				
}
