package com.example.demo.dom;

import static org.apache.xerces.jaxp.JAXPConstants.JAXP_SCHEMA_LANGUAGE;
import static org.apache.xerces.jaxp.JAXPConstants.W3C_XML_SCHEMA;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Component
public class DOMParser {
	
	@Autowired
	private DOMErrorHandler errorHandler;

	private DocumentBuilderFactory factory;
	
	public DOMParser() {
		super();
		this.factory = DocumentBuilderFactory.newInstance();
		this.factory.setValidating(true);
		this.factory.setNamespaceAware(true);
		this.factory.setIgnoringComments(true);
		this.factory.setIgnoringElementContentWhitespace(true);
		this.factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
	}
	
	public Document buildDocumentFromFile(String path) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = this.factory.newDocumentBuilder();
		builder.setErrorHandler(this.errorHandler);
		return builder.parse(new File(path));
	}
	
}
