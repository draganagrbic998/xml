package com.example.demo.service.odluka;

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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.StatusZahteva;
import com.example.demo.model.enums.TipOdgovora;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.KorisnikRDF;
import com.example.demo.repository.rdf.OdlukaRDF;
import com.example.demo.repository.rdf.ZahtevRDF;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.service.KorisnikService;

@Service
public class OdlukaService {
		
	@Autowired
	private OdlukaExist odlukaRepository;
	
	@Autowired
	private ZahtevExist zahtevRepository;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private XSLTransformer xslTransformer;
		
	@Autowired
	private OdlukaMapper odlukaMapper;
	
	//@Autowired
	//private EmailService emailService;
	
	@Autowired
	private KorisnikRDF korisnikRDF;
	
	@Autowired
	private ZahtevRDF zahtevRDF;
	
	@Autowired
	private OdlukaRDF odlukaRDF;

	private static final String XSL_FO_PATH_ODBIJANJE = Constants.XSL_FOLDER + File.separatorChar + "odbijanje_fo.xsl";
	private static final String XSL_PATH_ODBIJANJE = Constants.XSL_FOLDER + File.separatorChar + "/odbijanje.xsl";
	private static final String XSL_FO_PATH_OBAVESTENJE = Constants.XSL_FOLDER + File.separatorChar + "obavestenje_fo.xsl";
	private static final String XSL_PATH_OBAVESTENJE = Constants.XSL_FOLDER + File.separatorChar + "/obavestenje.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "odgovori" + File.separatorChar;

	public void add(String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, DOMException, ParseException {
		
		Document document = this.odlukaMapper.map(xml);
		String brojZahteva = document.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		//document.removeChild(document.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0));
		Document zahtevDocument = this.zahtevRepository.load(brojZahteva);
		if (OdlukaMapper.getTipOdgovora(document).equals(TipOdgovora.obavestenje)) {
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odobreno + "");
		}
		else {
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odbijeno + "");
		}
		this.odlukaRepository.save(null, document);
		this.zahtevRepository.save(brojZahteva, zahtevDocument);
		Model[] models = this.odlukaMapper.map(document);
		this.odlukaRDF.save(models[0]);
		this.korisnikRDF.save(models[1]);
		this.zahtevRDF.save(models[2]);
	}
	
	public String retrieve() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException, TransformerException{
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/odgovor:Odgovor";
		}
		else {
			xpathExp = String.format("/odgovor:Odgovor[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}

		ResourceSet resources = this.odlukaRepository.list(xpathExp);
		return this.odlukaMapper.map(resources);
	}
	
	public String html(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.odlukaRepository.load(broj);
		String xslPath;
		TipOdgovora tipOdgovora = getTipOdgovora(((Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odgovor").item(0)).getAttributeNS(Namespaces.XSI, "type"));
		if (tipOdgovora.equals(TipOdgovora.obavestenje)) {
			xslPath = XSL_PATH_OBAVESTENJE;
		}
		else {
			xslPath = XSL_PATH_ODBIJANJE;
		}
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, xslPath);
		return out.toString();
	}
	
	public Resource generateHtml(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.odlukaRepository.load(broj);
		String xslPath;
		TipOdgovora tipOdgovora = getTipOdgovora(((Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odgovor").item(0)).getAttributeNS(Namespaces.XSI, "type"));
		if (tipOdgovora.equals(TipOdgovora.obavestenje)) {
			xslPath = XSL_PATH_OBAVESTENJE;
		}
		else {
			xslPath = XSL_PATH_ODBIJANJE;
		}
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, xslPath);
		Path file = Paths.get(GEN_PATH + broj + ".html");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
	public Resource generatePdf(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.odlukaRepository.load(broj);
		String xslFoPath;
		TipOdgovora tipOdgovora = getTipOdgovora(((Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odgovor").item(0)).getAttributeNS(Namespaces.XSI, "type"));
		if (tipOdgovora.equals(TipOdgovora.obavestenje)) {
			xslFoPath = XSL_FO_PATH_OBAVESTENJE;
		}
		else {
			xslFoPath = XSL_FO_PATH_ODBIJANJE;
		}
		ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, xslFoPath);
		Path file = Paths.get(GEN_PATH + broj + ".pdf");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
	public static TipOdgovora getTipOdgovora(String tipOdgovora) {
		if (tipOdgovora.contains("TObavestenje")) {
			return TipOdgovora.obavestenje;
		}
		return TipOdgovora.odbijanje;
	}
	
}
