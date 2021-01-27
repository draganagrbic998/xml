package com.example.demo.transformer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.enums.MetadataType;
import com.example.demo.enums.TipZalbe;
import com.example.demo.mapper.ZalbaMapper;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.ZalbaExist;

@Component
public class ZalbaTransformer implements TransformerInterface {
	
	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private ZalbaRDF zalbaRDF;
	
	@Autowired
	private XSLTransformer xslTransformer;

	private static final String XSL_PATH_CUTANJE = Constants.XSL_FOLDER + "zalba_cutanje.xsl";
	private static final String XSL_PATH_ODLUKA = Constants.XSL_FOLDER + "zalba_odluka.xsl";
	private static final String XSL_FO_PATH_CUTANJE = Constants.XSL_FOLDER + "zalba_cutanje_fo.xsl";
	private static final String XSL_FO_PATH_ODLUKA = Constants.XSL_FOLDER + "zalba_odluka_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "zalbe" + File.separatorChar;
	
	@Override
	public String html(String documentId) {
		Document document = this.zalbaExist.load(documentId);
		if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.odluka)) {
			return this.xslTransformer.html(document, XSL_PATH_ODLUKA);
		}
		return this.xslTransformer.html(document, XSL_PATH_CUTANJE);
	}
		
	@Override
	public Resource pdf(String documentId) {
		Document document = this.zalbaExist.load(documentId);
		if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.odluka)) {
			return this.xslTransformer.pdf(document, XSL_FO_PATH_ODLUKA, GEN_PATH);
		}
		return this.xslTransformer.pdf(document, XSL_FO_PATH_CUTANJE, GEN_PATH);
	}
	
	@Override
	public byte[] byteHtml(String documentId) {
		Document document = this.zalbaExist.load(documentId);
		if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.odluka)) {
			return this.xslTransformer.byteHtml(document, XSL_PATH_ODLUKA);
		}
		return this.xslTransformer.byteHtml(document, XSL_PATH_CUTANJE);
	}
	
	@Override
	public byte[] bytePdf(String documentId) {
		Document document = this.zalbaExist.load(documentId);
		if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.odluka)) {
			return this.xslTransformer.bytePdf(document, XSL_FO_PATH_ODLUKA);
		}
		return this.xslTransformer.bytePdf(document, XSL_FO_PATH_CUTANJE);
	}
	
	@Override
	public Resource metadata(String documentId, MetadataType type) {
		return this.xslTransformer.metadata(documentId, this.zalbaRDF.retrieve(documentId), type, GEN_PATH);
	}

}
