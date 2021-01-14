package com.example.demo.transformer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.common.Constants;
import com.example.demo.enums.MetadataTip;
import com.example.demo.parser.DOCTransformer;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.xml.OdgovorExist;

@Component
public class OdgovorTransformer implements TransformerInterface {

	@Autowired
	private OdgovorExist odgovorExist;
	
	@Autowired
	private OdgovorRDF odgovorRDF;
	
	@Autowired
	private DOCTransformer docTransformer;

	private static final String XSL_PATH = Constants.XSL_FOLDER + "odgovor.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "odgovor_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "odgovori" + File.separatorChar;
	
	@Override
	public String html(String documentId) {
		return this.docTransformer.html(this.odgovorExist.load(documentId), XSL_PATH);
	}
	
	@Override
	public Resource generateHtml(String documentId) {
		return this.docTransformer.generateHtml(this.odgovorExist.load(documentId), XSL_PATH, GEN_PATH);
	}
	
	@Override
	public Resource generatePdf(String documentId) {
		return this.docTransformer.generatePdf(this.odgovorExist.load(documentId), XSL_FO_PATH, GEN_PATH);
	}
	
	@Override
	public Resource generateMetadata(String documentId, MetadataTip type) {
		return this.docTransformer.generateMetadata(documentId, this.odgovorRDF.retrieve(documentId), type, GEN_PATH);
	}
	
}
