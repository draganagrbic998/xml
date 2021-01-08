package com.example.demo.service.odgovor;

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

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.repository.xml.ZalbaExist;

@Service
public class OdgovorService {
	
	@Autowired
	private OdgovorExist odgovorExist;
	
	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private OdgovorMapper odgovorMapper;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	private static final String XSL_PATH = Constants.XSL_FOLDER + File.separatorChar + "odgovor.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + File.separatorChar + "odgovor_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "odgovori" + File.separatorChar;
	
	public void save(String xml) {
		Document document = this.odgovorMapper.map(xml);
		this.odgovorExist.save(null, document);
		String brojZalbe = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		Document zalbaDocument = this.zalbaExist.load(brojZalbe);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odgovoreno + "");
		this.zalbaExist.save(brojZalbe, zalbaDocument);
	}
	
	public String generateHtml(String broj) {
		Document document = this.odgovorExist.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), XSL_PATH);
		return out.toString();
	}
	
	public Resource generatePdf(String broj) {
		try {
			Document document = this.odgovorExist.load(broj);
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(this.domParser.buildXml(document), XSL_FO_PATH);
			Path file = Paths.get(GEN_PATH + broj + ".pdf");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}
