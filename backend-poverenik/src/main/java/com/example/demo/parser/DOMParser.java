package com.example.demo.parser;

import static org.apache.xerces.jaxp.JAXPConstants.JAXP_SCHEMA_LANGUAGE;
import static org.apache.xerces.jaxp.JAXPConstants.W3C_XML_SCHEMA;

import java.io.File;
import java.io.IOException;
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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.demo.constants.Namespaces;

@Component
public class DOMParser {
	
	@Autowired
	private DOMErrorHandler errorHandler;
	
	private DocumentBuilderFactory builderFactory;
	private TransformerFactory transformerFactory;
	
	public DOMParser() {
		super();
		this.builderFactory = DocumentBuilderFactory.newInstance();
		//this.builderFactory.setValidating(true);
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
	
	public String buildXml(Document document) throws TransformerException {
		StringWriter string = new StringWriter();
		Transformer transformer = this.transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");	
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		transformer.transform(new DOMSource(document), new StreamResult(string));
		return string.toString();
	}
	
	public void removeXmlSpace(Document document) {
		try {
			((Element) document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0)).removeAttribute("xml:space");
		}catch(Exception e) {};
		try {
			((Element) document.getElementsByTagNameNS(Namespaces.DOKUMENT, "Odluka").item(0)).removeAttribute("xml:space");
		}catch(Exception e) {};
		try {
			((Element) document.getElementsByTagNameNS(Namespaces.DOKUMENT, "Pasus").item(0)).removeAttribute("xml:space");
		}catch(Exception e) {};
		
		NodeList bolds = document.getElementsByTagNameNS(Namespaces.OSNOVA, "bold");
		for (int i = 0; i < bolds.getLength(); ++i) {
			Element bold = (Element) bolds.item(i);
			bold.removeAttribute("xml:space");
		}
		NodeList italics = document.getElementsByTagNameNS(Namespaces.OSNOVA, "italic");
		for (int i = 0; i < italics.getLength(); ++i) {
			Element italic = (Element) italics.item(i);
			italic.removeAttribute("xml:space");
		}
		NodeList zakoni = document.getElementsByTagNameNS(Namespaces.DOKUMENT, "zakon");
		for (int i = 0; i < zakoni.getLength(); ++i) {
			Element zakon = (Element) zakoni.item(i);
			zakon.removeAttribute("xml:space");
		}
	}
	
}
