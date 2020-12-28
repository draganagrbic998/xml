package com.example.demo.parser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.example.demo.constants.Constants;

@Component
public class XSLTransformer {

	@Autowired
	private DOMParser domParser;

	private TransformerFactory transformerFactory;
	
	public XSLTransformer() {
		super();
		this.transformerFactory = TransformerFactory.newInstance();	//da koristim mozda iz domparsera??
	}
	
	public ByteArrayOutputStream generateHtml(Document document, String xslPath) throws TransformerException {
		StreamSource in = new StreamSource(new StringReader(this.domParser.buildXml(document)));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Transformer transformer = this.transformerFactory.newTransformer(new StreamSource(new File(xslPath)));
		Result output = new StreamResult(out);
		transformer.transform(in, output);
		//out.toString() koristi kad oces da vratis html kao tekst
		//transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		//transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");
		return out;
	}
		
	public ByteArrayOutputStream generatePdf(Document document, String xslFoPath) throws TransformerException, SAXException, IOException {
		StreamSource in = new StreamSource(new StringReader(this.domParser.buildXml(document)));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Transformer transformer = this.transformerFactory.newTransformer(new StreamSource(new File(xslFoPath)));
		FopFactory foFactory = FopFactory.newInstance(new File(Constants.FOP_CONF));
		FOUserAgent foUserAgent = foFactory.newFOUserAgent();
		Fop fop = foFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
		Result output = new SAXResult(fop.getDefaultHandler());
		transformer.transform(in, output);
		return out;
	}
	
}
