package com.example.demo.parser;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
