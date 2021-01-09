package com.example.demo.parser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Component;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;

@Component
public class XSLTransformer {

	private TransformerFactory transformerFactory;
			
	public XSLTransformer() {
		super();
		this.transformerFactory = TransformerFactory.newInstance();
	}
	
	public ByteArrayOutputStream generateMetadata(String xml) {
		try {
			StreamSource in = new StreamSource(new StringReader(xml));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Transformer transformer = this.transformerFactory.newTransformer(new StreamSource(new File(Constants.GRDDL_XSL)));
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult output = new StreamResult(out);
			transformer.transform(in, output);
			return out;
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public ByteArrayOutputStream generateHtml(String xml, String xslPath) {
		try {
			StreamSource in = new StreamSource(new StringReader(xml));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Transformer transformer = this.transformerFactory.newTransformer(new StreamSource(new File(xslPath)));
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");	
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			StreamResult output = new StreamResult(out);
			transformer.transform(in, output);
			return out;
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
		
	public ByteArrayOutputStream generatePdf(String xml, String xslFoPath) {
		try {
			StreamSource in = new StreamSource(new StringReader(xml));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Transformer transformer = this.transformerFactory.newTransformer(new StreamSource(new File(xslFoPath)));
			FopFactory foFactory = FopFactory.newInstance(new File(Constants.FOP_CONF));
			FOUserAgent foUserAgent = foFactory.newFOUserAgent();
			Fop fop = foFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
			Result output = new SAXResult(fop.getDefaultHandler());
			transformer.transform(in, output);
			return out;
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
