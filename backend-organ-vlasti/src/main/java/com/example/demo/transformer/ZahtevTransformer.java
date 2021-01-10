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
import com.example.demo.enums.MetadataType;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.ZahtevRDF;
import com.example.demo.repository.xml.ZahtevExist;

@Component
public class ZahtevTransformer implements TransformerInterface {
	
	@Autowired
	private ZahtevExist zahtevExist;
	
	@Autowired
	private ZahtevRDF zahtevRDF;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private DOMParser domParser;
	
	private static final String XSL_PATH = Constants.XSL_FOLDER + "zahtev.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "zahtev_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "zahtevi" + File.separatorChar;
	@Override
	public String html(String documentId) {
		Document document = this.zahtevExist.load(documentId);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), XSL_PATH);
		return out.toString();
	}
	@Override
	public Resource generateHtml(String documentId) {
		try {
			Document document = this.zahtevExist.load(documentId);
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
			Document document = this.zahtevExist.load(documentId);
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
	public Resource generateMetadata(String documentId, MetadataType type) {
		try {
			ResultSet results = this.zahtevRDF.retrieve(documentId);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (type.equals(MetadataType.xml)) {
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
