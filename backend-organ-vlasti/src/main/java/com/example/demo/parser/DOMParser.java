package com.example.demo.parser;

import static org.apache.xerces.jaxp.JAXPConstants.JAXP_SCHEMA_LANGUAGE;
import static org.apache.xerces.jaxp.JAXPConstants.W3C_XML_SCHEMA;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

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
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
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
	
	public Document buildDocument(String xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = this.builderFactory.newDocumentBuilder();
		builder.setErrorHandler(this.errorHandler);
		return builder.parse(new InputSource(new StringReader(xml)));
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
		transformer.transform(new DOMSource(document), new StreamResult(out));
	}
	
	public String buildXml(Node node) throws TransformerException {
		StringWriter string = new StringWriter();
		Transformer transformer = this.transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");	
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		transformer.transform(new DOMSource(node), new StreamResult(string));
		return string.toString();
	}
	
}
