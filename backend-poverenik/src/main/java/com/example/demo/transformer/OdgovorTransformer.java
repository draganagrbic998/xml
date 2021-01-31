package com.example.demo.transformer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.common.Constants;
import com.example.demo.enums.MetadataType;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.xml.OdgovorExist;

@Component
public class OdgovorTransformer implements TransformerInterface {

	@Autowired
	private OdgovorExist odgovorExist;
	
	@Autowired
	private OdgovorRDF odgovorRDF;
	
	@Autowired
	private XSLTransformer xslTransformer;

	private static final String XSL_PATH = Constants.XSL_FOLDER + "odgovor.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "odgovor_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "odgovori" + File.separatorChar;
	
	@Override
	public String html(String documentId) {
		return this.xslTransformer.html(this.odgovorExist.load(documentId), XSL_PATH);
	}
		
	@Override
	public Resource pdf(String documentId) {
		return this.xslTransformer.pdf(this.odgovorExist.load(documentId), XSL_FO_PATH, GEN_PATH);
	}
	
	@Override
	public byte[] byteHtml(String documentId) {
		return this.xslTransformer.byteHtml(this.odgovorExist.load(documentId), XSL_PATH);
	}
	
	@Override
	public byte[] bytePdf(String documentId) {
		return this.xslTransformer.bytePdf(this.odgovorExist.load(documentId), XSL_FO_PATH);
	}

	@Override
	public String metadata(String documentId, MetadataType type) {
		return this.xslTransformer.metadata(this.odgovorRDF.retrieve(documentId), type);
	}
		
}
