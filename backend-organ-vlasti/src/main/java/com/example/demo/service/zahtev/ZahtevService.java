package com.example.demo.service.zahtev;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.KorisnikRDF;
import com.example.demo.repository.ZahtevRDF;
import com.example.demo.repository.ZahtevExist;
import com.example.demo.service.KorisnikService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class ZahtevService {
	
	@Autowired
	private ZahtevExist zahtevRepository;
			
	@Autowired
	private KorisnikService korisnikService;
			
	@Autowired
	private ZahtevRDF zahtevRDF;
	
	@Autowired
	private KorisnikRDF korisnikRDF;
	
	@Autowired
	private ZahtevMapper zahtevMapper;
	
	@Autowired
	private XSLTransformer xslTransformer;

	private static final String XSL_PATH = Constants.XSL_FOLDER + File.separatorChar + "zahtev.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + File.separatorChar + "zahtev_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "zahtevi" + File.separatorChar;

	public void add(String xml) throws DOMException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException, JAXBException, XMLDBException, TransformerException {
		Document document = this.zahtevMapper.map(xml);
		this.zahtevRepository.save(null, document);
		Model[] models = this.zahtevMapper.map(document);
		this.zahtevRDF.save(models[0]);
		this.korisnikRDF.save(models[1]);		
	}
	
	public String retrieve() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException, TransformerException, DOMException, ParseException{
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/zahtev:Zahtev";
		}
		else {
			xpathExp = String.format("/zahtev:Zahtev[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		ResourceSet resources = this.zahtevRepository.list(xpathExp);
		return this.zahtevMapper.map(resources);
	}
	
	public String generateHtml(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.zahtevRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, XSL_PATH);
		return out.toString();
	}
	
	public Resource generatePdf(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.zahtevRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, XSL_FO_PATH);
		Path file = Paths.get(GEN_PATH + broj + ".pdf");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
}
