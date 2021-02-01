package com.example.demo.transformer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.MetadataType;
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
		
	private static final String XSL_PATH = Constants.XSL_FOLDER + "zahtev.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "zahtev_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "zahtevi" + File.separatorChar;
		
	@Override
	public String html(String documentId) {
		return this.xslTransformer.html(this.zahtevExist.find(documentId), XSL_PATH);
	}
		
	@Override
	public Resource pdf(String documentId) {
		return this.xslTransformer.pdf(this.zahtevExist.find(documentId), XSL_FO_PATH, GEN_PATH);
	}
	
	@Override
	public byte[] byteHtml(String documentId) {
		return this.xslTransformer.byteHtml(this.zahtevExist.find(documentId), XSL_PATH);
	}
	
	@Override
	public byte[] bytePdf(String documentId) {
		return this.xslTransformer.bytePdf(this.zahtevExist.find(documentId), XSL_FO_PATH);
	}
	
	@Override
	public String metadata(String documentId, MetadataType type) {
		return this.xslTransformer.metadata(Namespaces.ZAHTEV + "/" + documentId, this.zahtevRDF.findAll(documentId), type);
	}

}
