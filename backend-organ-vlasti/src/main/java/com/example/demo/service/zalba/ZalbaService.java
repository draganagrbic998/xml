package com.example.demo.service.zalba;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.constants.Constants;
import com.example.demo.exception.MyException;
import com.example.demo.model.enums.TipZalbe;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.xml.ZalbaExist;

@Service
public class ZalbaService {

	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private ZalbaMapper zalbaMapper;

	@Autowired
	private XSLTransformer xslTransformer;

	private static final String XSL_PATH_CUTANJE = Constants.XSL_FOLDER + File.separatorChar + "/zalba_cutanje.xsl";
	private static final String XSL_FO_PATH_CUTANJE = Constants.XSL_FOLDER + File.separatorChar + "zalba_cutanje_fo.xsl";
	private static final String XSL_PATH_ODLUKA = Constants.XSL_FOLDER + File.separatorChar + "/zalba_odluka.xsl";
	private static final String XSL_FO_PATH_ODLUKA = Constants.XSL_FOLDER + File.separatorChar + "/zalba_odluka_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "zalbe" + File.separatorChar;
	
	public void save(String xml) {
		Document document = this.zalbaMapper.map(xml);
		this.zalbaExist.save(this.zalbaMapper.getBroj(document), document);
	}

	public String retrieve() {
		//zabrani gradjaninu da na ovom serveru gleda ista
		ResourceSet resources = this.zalbaExist.retrieve("/zalba:Zalba");
		return this.zalbaMapper.map(resources);
	}

	public String generateHtml(String broj) {
		Document document = this.zalbaExist.load(broj);
		String xslPath;
		TipZalbe tipZalbe = ZalbaMapper.getTipZalbe(document);
		if (tipZalbe.equals(TipZalbe.cutanje)) {
			xslPath = XSL_PATH_CUTANJE;
		} 
		else {
			xslPath = XSL_PATH_ODLUKA;
		}
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, xslPath);
		return out.toString();
	}

	public Resource generatePdf(String broj) {
		try {
			Document document = this.zalbaExist.load(broj);
			String xslFoPath;
			if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.cutanje)) {
				xslFoPath = XSL_FO_PATH_CUTANJE;
			} else {
				xslFoPath = XSL_FO_PATH_ODLUKA;
			}
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, xslFoPath);
			Path file = Paths.get(GEN_PATH + broj + ".pdf");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}