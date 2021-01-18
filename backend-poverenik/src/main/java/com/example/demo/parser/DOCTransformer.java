package com.example.demo.parser;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.common.Namespaces;
import com.example.demo.enums.MetadataTip;
import com.example.demo.exception.MyException;

@Component
public class DOCTransformer {

	@Autowired
	private DOMParser domParser;

	@Autowired
	private XSLTransformer xslTransformer;
			
	public String html(Document document, String xslPath) {
		return this.xslTransformer.generateHtml(this.domParser.buildXml(document), xslPath).toString();
	}

	public Resource generateHtml(Document document, String xslPath, String genPath) {
		try {
			ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), xslPath);
			Path file = Paths.get(genPath
					+ document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent() + ".html");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		} 
		catch (Exception e) {
			throw new MyException(e);
		}
	}

	public Resource generatePdf(Document document, String xslFoPath, String genPath) {
		try {
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(this.domParser.buildXml(document), xslFoPath);
			Path file = Paths.get(genPath
					+ document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent() + ".pdf");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		} 
		catch (Exception e) {
			throw new MyException(e);
		}
	}

	public Resource generateMetadata(String broj, ResultSet results, MetadataTip type, String genPath) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (type.equals(MetadataTip.xml)) {
				ResultSetFormatter.outputAsXML(out, results);
			} 
			else {
				ResultSetFormatter.outputAsJSON(out, results);
			}
			Path file = Paths.get(genPath + broj + "_metadata." + type);
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		} 
		catch (Exception e) {
			throw new MyException(e);
		}
	}

}
