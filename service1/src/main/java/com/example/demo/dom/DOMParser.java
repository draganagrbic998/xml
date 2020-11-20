package com.example.demo.dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Component
public class DOMParser {

	private DocumentBuilderFactory factory;
	
	public DOMParser() {
		super();
		this.factory = DocumentBuilderFactory.newInstance();
		this.factory.setValidating(true);
		this.factory.setNamespaceAware(true);
		this.factory.setIgnoringComments(true);
		this.factory.setIgnoringElementContentWhitespace(true);
		//dodaj ono za w32....
		//dodaj errorhandler
	}
	
	public Document buildDocumentFromFile(String path) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = this.factory.newDocumentBuilder();
		return builder.parse(new File(path));
	}
	
}
