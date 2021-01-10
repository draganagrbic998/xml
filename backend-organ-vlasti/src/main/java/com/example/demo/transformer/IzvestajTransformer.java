package com.example.demo.transformer;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.enums.MetadataTip;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.IzvestajRDF;
import com.example.demo.repository.xml.IzvestajExist;

@Component
public class IzvestajTransformer implements TransformerInterface {

	@Autowired
	private IzvestajExist izvestajExist;
	
	@Autowired
	private IzvestajRDF izvestajRDF;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private DOMParser domParser;

	private static final String XSL_PATH = Constants.XSL_FOLDER + "izvestaj.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "izvestaj_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "izvestaji" + File.separatorChar;
	
	@Override
	public String html(String documentId) {
		Document document = this.izvestajExist.load(documentId);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), XSL_PATH);
		return out.toString();
	}
	
	@Override
	public Resource generateHtml(String documentId) {
		try {
			Document document = this.izvestajExist.load(documentId);
			ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), XSL_PATH);
			Path file = Paths.get(GEN_PATH + documentId + ".html");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	@Override
	public Resource generatePdf(String documentId) {
		try {
			Document document = this.izvestajExist.load(documentId);
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(this.domParser.buildXml(document), XSL_FO_PATH);
			Path file = Paths.get(GEN_PATH + documentId + ".pdf");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	@Override
	public Resource generateMetadata(String documentId, MetadataTip type) {
		try {
			ResultSet results = this.izvestajRDF.retrieve(documentId);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (type.equals(MetadataTip.xml)) {
				ResultSetFormatter.outputAsXML(out, results);
			}
			else {
				ResultSetFormatter.outputAsJSON(out, results);
			}
			Path file = Paths.get(GEN_PATH + documentId + "_metadata." + type);
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
