package com.example.demo.parser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

@Component
public class XSLTransformer {

	private TransformerFactory transformerFactory;
	
	@Autowired
	private DOMParser domParser;

	public XSLTransformer() {
		super();
		this.transformerFactory = TransformerFactory.newInstance();	//da koristim mozda iz domparsera??
	}
	
	private String fopPath = "data/fop.conf";
	
	
	public ByteArrayOutputStream generatePdf(Document document, String xslFoPath) throws TransformerException, SAXException, IOException {
		StreamSource in = new StreamSource(new StringReader(this.domParser.buildXml(document)));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Transformer transformer = this.transformerFactory.newTransformer(new StreamSource(new File(xslFoPath)));
		FopFactory foFactory = FopFactory.newInstance(new File(fopPath));
		FOUserAgent foUserAgent = foFactory.newFOUserAgent();
		Fop fop = foFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
		Result output = new SAXResult(fop.getDefaultHandler());
		transformer.transform(in, output);
		return out;
	}
	
	public ByteArrayOutputStream generateHtml(Document document, String xslPath) throws TransformerException, SAXException, IOException {
		StreamSource in = new StreamSource(new StringReader(this.domParser.buildXml(document)));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Transformer transformer = this.transformerFactory.newTransformer(new StreamSource(new File(xslPath)));
		Result output = new StreamResult(out);
		transformer.transform(in, output);
		return out;
	}
	
	private void testHtml(Node node, String xslPath, String xslTestPath) throws TransformerException, IOException {
		StreamSource in = new StreamSource(new StringReader(this.domParser.buildXml(node)));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Transformer transformer = this.transformerFactory.newTransformer(new StreamSource(new File(xslPath)));
		Result output = new StreamResult(out);
		transformer.transform(in, output);
		FileOutputStream fout = new FileOutputStream(new File(xslTestPath));
		fout.write(out.toByteArray());
		fout.close();
	}
	
	private void testPdf(Node node, String xslFoPath, String xslFoTestPath) throws TransformerException, SAXException, IOException {
		StreamSource in = new StreamSource(new StringReader(this.domParser.buildXml(node)));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Transformer transformer = this.transformerFactory.newTransformer(new StreamSource(new File(xslFoPath)));
		FopFactory foFactory = FopFactory.newInstance(new File(fopPath));
		FOUserAgent foUserAgent = foFactory.newFOUserAgent();
		Fop fop = foFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
		Result output = new SAXResult(fop.getDefaultHandler());
		transformer.transform(in, output);
		FileOutputStream fout = new FileOutputStream(new File(xslFoTestPath));
		fout.write(out.toByteArray());
		fout.close();
	}
	
	public void testTransforming(Node node, String xslPath, String xslFoPath, String xslTestPath, String xslFoTestPath) throws TransformerException, FileNotFoundException, IOException, SAXException {
		this.testHtml(node, xslPath, xslTestPath);
		this.testPdf(node, xslFoPath, xslFoTestPath);
	}

	
	public String xml2html(XMLResource resource, String xslFilePath) throws TransformerException, XMLDBException {
		String xmlString = this.domParser.buildXml(resource.getContentAsDOM());
		
		StreamSource xslStream = new StreamSource(new File(xslFilePath));
		
		StreamSource in = new StreamSource(new StringReader(xmlString));
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		Result out = new StreamResult(outStream);
		
		Transformer transformer = this.transformerFactory.newTransformer(xslStream);
		//transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		// Generate XHTML
		//transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

		transformer.transform(in, out);
		System.out.println(outStream.toString());
		return outStream.toString();
	}
	
	public ByteArrayOutputStream xml2pdf(String sourceStr, String xsltFoPath) throws Exception {
		File xslFile = new File(xsltFoPath);
		StreamSource transformSource = new StreamSource(xslFile);
		StreamSource source = new StreamSource(new StringReader(sourceStr));
		FopFactory fopFactory = FopFactory.newInstance(new File(fopPath));
		
		FOUserAgent userAgent = fopFactory.newFOUserAgent();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		Transformer xslFoTransformer = this.transformerFactory.newTransformer(transformSource);
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
		Result res = new SAXResult(fop.getDefaultHandler());
		xslFoTransformer.transform(source, res);
		return outStream;
	}
	
}
