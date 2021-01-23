package com.example.demo.transformer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.enums.MetadataType;
import com.example.demo.enums.TipOdluke;
import com.example.demo.mapper.OdlukaMapper;
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

	private static final String XSL_PATH_OBAVESTENJE = Constants.XSL_FOLDER + "obavestenje.xsl";
	private static final String XSL_PATH_ODBIJANJE = Constants.XSL_FOLDER + "odbijanje.xsl";
	private static final String XSL_FO_PATH_OBAVESTENJE = Constants.XSL_FOLDER + "obavestenje_fo.xsl";
	private static final String XSL_FO_PATH_ODBIJANJE = Constants.XSL_FOLDER + "odbijanje_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "odluke" + File.separatorChar;
	
	@Override
	public String html(String documentId) {
		Document document = this.odlukaExist.load(documentId);
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			return this.xslTransformer.html(document, XSL_PATH_OBAVESTENJE);
		}
		return this.xslTransformer.html(document, XSL_PATH_ODBIJANJE);
	}
		
	@Override
	public Resource pdf(String documentId) {
		Document document = this.odlukaExist.load(documentId);
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			return this.xslTransformer.pdf(document, XSL_FO_PATH_OBAVESTENJE, GEN_PATH);
		}
		return this.xslTransformer.pdf(document, XSL_FO_PATH_ODBIJANJE, GEN_PATH);
	}
	
	@Override
	public byte[] byteHtml(String documentId) {
		Document document = this.odlukaExist.load(documentId);
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			return this.xslTransformer.byteHtml(document, XSL_PATH_OBAVESTENJE);
		}
		return this.xslTransformer.byteHtml(document, XSL_PATH_ODBIJANJE);
	}
	
	@Override
	public byte[] bytePdf(String documentId) {
		Document document = this.odlukaExist.load(documentId);
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			return this.xslTransformer.bytePdf(document, XSL_FO_PATH_OBAVESTENJE);
		}
		return this.xslTransformer.bytePdf(document, XSL_FO_PATH_ODBIJANJE);
	}
	
	@Override
	public Resource metadata(String documentId, MetadataType type) {
		return this.xslTransformer.metadata(documentId, this.odlukaRDF.retrieve(documentId), type, GEN_PATH);
	}
	
}
