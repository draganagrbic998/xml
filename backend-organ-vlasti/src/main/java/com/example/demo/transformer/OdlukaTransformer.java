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
import com.example.demo.enums.TipOdluke;
import com.example.demo.mapper.OdlukaMapper;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.OdlukaRDF;
import com.example.demo.repository.xml.OdlukaExist;

@Component
public class OdlukaTransformer implements TransformerInterface {
	
	@Autowired
	private OdlukaExist odlukaExist;
	
	@Autowired
	private OdlukaRDF odlukaRDF;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private DOMParser domParser;

	private static final String XSL_PATH_OBAVESTENJE = Constants.XSL_FOLDER + "obavestenje.xsl";
	private static final String XSL_PATH_ODBIJANJE = Constants.XSL_FOLDER + "odbijanje.xsl";
	private static final String XSL_FO_PATH_OBAVESTENJE = Constants.XSL_FOLDER + "obavestenje_fo.xsl";
	private static final String XSL_FO_PATH_ODBIJANJE = Constants.XSL_FOLDER + "odbijanje_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "odluke" + File.separatorChar;
	
	@Override
	public String html(String documentId) {
		Document document = this.odlukaExist.load(documentId);
		String xslPath;
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			xslPath = XSL_PATH_OBAVESTENJE;
		}
		else {
			xslPath = XSL_PATH_ODBIJANJE;
		}
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), xslPath);
		return out.toString();
	}
	@Override
	public Resource generateHtml(String documentId) {
		try {
			Document document = this.odlukaExist.load(documentId);
			String xslPath;
			if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
				xslPath = XSL_PATH_OBAVESTENJE;
			}
			else {
				xslPath = XSL_PATH_ODBIJANJE;
			}
			ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), xslPath);
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
			Document document = this.odlukaExist.load(documentId);
			String xslFoPath;
			if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
				xslFoPath = XSL_FO_PATH_OBAVESTENJE;
			}
			else {
				xslFoPath = XSL_FO_PATH_ODBIJANJE;
			}
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(this.domParser.buildXml(document), xslFoPath);
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
			ResultSet results = this.odlukaRDF.retrieve(documentId);
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
