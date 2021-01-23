package com.example.demo.transformer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.common.Constants;
import com.example.demo.enums.MetadataTip;
import com.example.demo.parser.DOCTransformer;
import com.example.demo.repository.rdf.ZahtevRDF;
import com.example.demo.repository.xml.ZahtevExist;

@Component
public class ZahtevTransformer implements TransformerInterface {
	
	@Autowired
	private ZahtevExist zahtevExist;
	
	@Autowired
	private ZahtevRDF zahtevRDF;
	
	@Autowired
	private DOCTransformer docTransformer;
		
	private static final String XSL_PATH = Constants.XSL_FOLDER + "zahtev.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "zahtev_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "zahtevi" + File.separatorChar;
		
	@Override
	public String html(String documentId) {
		return this.docTransformer.html(this.zahtevExist.load(documentId), XSL_PATH);
	}
	
	@Override
	public Resource generateHtml(String documentId) {
		return this.docTransformer.generateHtml(this.zahtevExist.load(documentId), XSL_PATH, GEN_PATH);
	}
	
	@Override
	public Resource generatePdf(String documentId) {
		return this.docTransformer.generatePdf(this.zahtevExist.load(documentId), XSL_FO_PATH, GEN_PATH);
	}
	
	@Override
	public Resource generateMetadata(String documentId, MetadataTip type) {
		return this.docTransformer.generateMetadata(documentId, this.zahtevRDF.retrieve(documentId), type, GEN_PATH);
	}
	
	public byte[] plainPdf(String documentId) {
		return this.docTransformer.plainPdf(this.zahtevExist.load(documentId), XSL_FO_PATH);
	}

	public byte[] plainHtml(String documentId) {
		return this.docTransformer.plainHtml(this.zahtevExist.load(documentId), XSL_PATH);
	}

}
