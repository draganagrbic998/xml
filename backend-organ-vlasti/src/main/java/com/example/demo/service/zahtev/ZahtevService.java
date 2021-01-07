package com.example.demo.service.zahtev;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.constants.Constants;
import com.example.demo.exception.MyException;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.ZahtevRDF;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.service.KorisnikService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class ZahtevService {
	
	@Autowired
	private ZahtevExist zahtevExist;
			
	@Autowired
	private KorisnikService korisnikService;
			
	@Autowired
	private ZahtevRDF zahtevRDF;

	@Autowired
	private ZahtevMapper zahtevMapper;
	
	@Autowired
	private XSLTransformer xslTransformer;

	private static final String XSL_PATH = Constants.XSL_FOLDER + File.separatorChar + "zahtev.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + File.separatorChar + "zahtev_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "zahtevi" + File.separatorChar;

	public void add(String xml) {
		Document document = this.zahtevMapper.map(xml);
		this.zahtevExist.save(null, document);
		Model model = this.zahtevMapper.map(document);
		this.zahtevRDF.save(model);
	}
	
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/zahtev:Zahtev";
		}
		else {
			xpathExp = String.format("/zahtev:Zahtev[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		ResourceSet resources = this.zahtevExist.list(xpathExp);
		return this.zahtevMapper.map(resources);
	}
	
	public String generateHtml(String broj) {
		Document document = this.zahtevExist.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, XSL_PATH);
		return out.toString();
	}
	
	public Resource generatePdf(String broj) {
		try {
			Document document = this.zahtevExist.load(broj);
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, XSL_FO_PATH);
			Path file = Paths.get(GEN_PATH + broj + ".pdf");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
