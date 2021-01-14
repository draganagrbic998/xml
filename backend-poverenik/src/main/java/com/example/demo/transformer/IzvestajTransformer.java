package com.example.demo.transformer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.common.Constants;
import com.example.demo.enums.MetadataTip;
import com.example.demo.parser.DOCTransformer;
import com.example.demo.repository.rdf.IzvestajRDF;
import com.example.demo.repository.xml.IzvestajExist;

@Component
public class IzvestajTransformer implements TransformerInterface {

	@Autowired
	private IzvestajExist izvestajExist;
	
	@Autowired
	private IzvestajRDF izvestajRDF;
	
	@Autowired
	private DOCTransformer docTransformer;

	private static final String XSL_PATH = Constants.XSL_FOLDER + "izvestaj.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "izvestaj_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "izvestaji" + File.separatorChar;
	
	@Override
	public String html(String documentId) {
		return this.docTransformer.html(this.izvestajExist.load(documentId), XSL_PATH);
	}
	
	@Override
	public Resource generateHtml(String documentId) {
		return this.docTransformer.generateHtml(this.izvestajExist.load(documentId), XSL_PATH, GEN_PATH);
	}
	
	@Override
	public Resource generatePdf(String documentId) {
		return this.docTransformer.generatePdf(this.izvestajExist.load(documentId), XSL_FO_PATH, GEN_PATH);
	}
	
	@Override
	public Resource generateMetadata(String documentId, MetadataTip type) {
		return this.docTransformer.generateMetadata(documentId, this.izvestajRDF.retrieve(documentId), type, GEN_PATH);
	}
	
}
