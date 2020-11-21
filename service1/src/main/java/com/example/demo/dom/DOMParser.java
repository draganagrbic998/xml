package com.example.demo.dom;

import static org.apache.xerces.jaxp.JAXPConstants.JAXP_SCHEMA_LANGUAGE;
import static org.apache.xerces.jaxp.JAXPConstants.W3C_XML_SCHEMA;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Component
public class DOMParser {
	
	@Autowired
	private DOMErrorHandler errorHandler;

	private DocumentBuilderFactory builderFactory;
	private TransformerFactory transformerFactory;
	
	public DOMParser() {
		super();
		this.builderFactory = DocumentBuilderFactory.newInstance();
		this.builderFactory.setValidating(true);
		this.builderFactory.setNamespaceAware(true);
		this.builderFactory.setIgnoringComments(true);
		this.builderFactory.setIgnoringElementContentWhitespace(true);
		this.builderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		this.transformerFactory = TransformerFactory.newInstance();
	}
	
	public Document buildDocumentFromFile(String path) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = this.builderFactory.newDocumentBuilder();
		builder.setErrorHandler(this.errorHandler);
		return builder.parse(new File(path));
	}
	
	public void transformDocument(Document document, OutputStream out) throws TransformerException {
		Transformer transformer = this.transformerFactory.newTransformer();
		transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(out);
		transformer.transform(source, result);
	}
	
}
