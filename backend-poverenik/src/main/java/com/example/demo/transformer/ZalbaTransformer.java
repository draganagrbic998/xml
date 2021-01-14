package com.example.demo.transformer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.enums.MetadataTip;
import com.example.demo.enums.TipZalbe;
import com.example.demo.mapper.ZalbaMapper;
import com.example.demo.parser.DOCTransformer;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.ZalbaExist;

@Component
public class ZalbaTransformer implements TransformerInterface {
	
	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private ZalbaRDF zalbaRDF;
	
	@Autowired
	private DOCTransformer docTransformer;

	private static final String XSL_PATH_CUTANJE = Constants.XSL_FOLDER + "zalba_cutanje.xsl";
	private static final String XSL_PATH_ODLUKA = Constants.XSL_FOLDER + "zalba_odluka.xsl";
	private static final String XSL_FO_PATH_CUTANJE = Constants.XSL_FOLDER + "zalba_cutanje_fo.xsl";
	private static final String XSL_FO_PATH_ODLUKA = Constants.XSL_FOLDER + "zalba_odluka_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "zalbe" + File.separatorChar;
	
	@Override
	public String html(String documentId) {
		Document document = this.zalbaExist.load(documentId);
		if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.odluka)) {
			return this.docTransformer.html(document, XSL_PATH_ODLUKA);
		}
		return this.docTransformer.html(document, XSL_PATH_CUTANJE);
	}
	
	@Override
	public Resource generateHtml(String documentId) {
		Document document = this.zalbaExist.load(documentId);
		if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.odluka)) {
			return this.docTransformer.generateHtml(document, XSL_PATH_ODLUKA, GEN_PATH);
		}
		return this.docTransformer.generateHtml(document, XSL_PATH_CUTANJE, GEN_PATH);
	}
	
	@Override
	public Resource generatePdf(String documentId) {
		Document document = this.zalbaExist.load(documentId);
		if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.odluka)) {
			return this.docTransformer.generatePdf(document, XSL_FO_PATH_ODLUKA, GEN_PATH);
		}
		return this.docTransformer.generatePdf(document, XSL_FO_PATH_CUTANJE, GEN_PATH);
	}
	
	@Override
	public Resource generateMetadata(String documentId, MetadataTip type) {
		return this.docTransformer.generateMetadata(documentId, this.zalbaRDF.retrieve(documentId), type, GEN_PATH);
	}

}
