package com.example.demo.service.resenje;

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

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.xml.ResenjeExist;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.TipDokumenta;

@Service
public class ResenjeService {
	
	@Autowired
	private ResenjeExist resenjeExist;
	
	@Autowired
	private ZalbaExist zalbaExist;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private ResenjeMapper resenjeMapper;
	
	@Autowired
	private DOMParser domParser;

	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private SOAPService soapService;

	private static final String XSL_PATH = Constants.XSL_FOLDER + "/resenje.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "/resenje_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "resenja" + File.separatorChar;
	
	public void add(String xml) {
		Document document = this.resenjeMapper.map(xml);
		String brojZalbe = document.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
		Document zalbaDocument = this.zalbaExist.load(brojZalbe);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.reseno + "");
		this.zalbaExist.save(brojZalbe, zalbaDocument);
		this.resenjeExist.save(null, document);
		this.soapService.sendSOAPMessage(this.resenjeMapper.map(document), TipDokumenta.resenje);
	}
	
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp = null;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/resenje:Resenje";
		}
		else {
			xpathExp = String.format("/resenje:Resenje[resenje:PodaciZahteva/mejl='%s']", korisnik.getOsoba().getMejl());
		}		
		ResourceSet resouces = this.resenjeExist.retrieve(xpathExp);
		return this.resenjeMapper.map(resouces);		
	}
	
	public String generateHtml(String broj) {
		Document document = this.resenjeExist.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), XSL_PATH);
		return out.toString();
	}
	
	public Resource generatePdf(String broj) {
		try {
			Document document = this.resenjeExist.load(broj);
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
