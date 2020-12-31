package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.StatusZahteva;
import com.example.demo.model.enums.TipOdgovora;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.OdgovorRepository;
import com.example.demo.repository.ZahtevRepository;
import com.example.demo.service.email.Email;
import com.example.demo.service.email.EmailService;
import com.ibm.icu.text.SimpleDateFormat;

@Service
public class OdgovorService {
		
	@Autowired
	private OdgovorRepository odgovorRepository;

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private EmailService emailService;
	
	private static final String XSL_FO_PATH_ODBIJANJE = Constants.XSL_FOLDER + File.separatorChar + "odbijanje_fo.xsl";
	private static final String XSL_PATH_ODBIJANJE = Constants.XSL_FOLDER + File.separatorChar + "/odbijanje.xsl";
	private static final String XSL_FO_PATH_OBAVESTENJE = Constants.XSL_FOLDER + File.separatorChar + "obavestenje_fo.xsl";
	private static final String XSL_PATH_OBAVESTENJE = Constants.XSL_FOLDER + File.separatorChar + "/obavestenje.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "obavestenja" + File.separatorChar;

	public void save(String brojZahteva, String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, DOMException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyy.");
		Document document = this.domParser.buildDocument(xml);
		this.domParser.removeXmlSpace(document);
		Element odgovor = (Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0);
		Document zahtevDocument = this.zahtevRepository.load(brojZahteva);
		Element zahtev = (Element) zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		
		DocumentFragment documentFragment = document.createDocumentFragment();
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		Node mesto = document.createElementNS(Namespaces.OSNOVA, "mesto");
		mesto.setTextContent(Constants.TEST_MESTO);
		Node potpis = document.createElementNS(Namespaces.OSNOVA, "potpis");
		potpis.setTextContent(Constants.TEST_POTPIS);
		Element gradjanin = (Element) document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Gradjanin").item(0), true);
		Element organVlasti = (Element) document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);		
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));			
		documentFragment.appendChild(datum);
		documentFragment.appendChild(mesto);
		documentFragment.appendChild(potpis);
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(organVlasti);
		odgovor.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));	

		//nzm sto moram da stavim ovaj prefix zahtev ispred, provericu to kasnije
		Node datumZahteva = document.createElementNS(Namespaces.ODGOVOR, "odgovor:datumZahteva");
		datumZahteva.setTextContent(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		TipOdgovora tipOdgovora = getTipOdgovora(odgovor.getAttributeNS(Namespaces.XSI, "type"));
		if (tipOdgovora.equals(TipOdgovora.obavestenje)) {
			odgovor.insertBefore(datumZahteva, odgovor.getElementsByTagNameNS(Namespaces.ODGOVOR, "Uvid").item(0));
			zahtev.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odobreno + "");
		}
		else {
			odgovor.appendChild(datumZahteva);
			zahtev.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odbijeno + "");
		}
		
		this.zahtevRepository.save(zahtevDocument, brojZahteva);
		String broj = this.odgovorRepository.save(document, null);
		
		
		Element osoba = (Element) gradjanin.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0);
		String mejl = osoba.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent();
		String ime = osoba.getElementsByTagNameNS(Namespaces.OSNOVA, "ime").item(0).getTextContent();
		String prezime = osoba.getElementsByTagNameNS(Namespaces.OSNOVA, "prezime").item(0).getTextContent();
		String naziv = organVlasti.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
		Element sediste = (Element) organVlasti.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0);
		String sedisteEmail = sediste.getElementsByTagNameNS(Namespaces.OSNOVA, "ulica").item(0).getTextContent() + " " 
				+ sediste.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent() + ", " 
				+ sediste.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0).getTextContent();
		String datumZahtevaEmail = sdf2.format(sdf.parse(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent()));
		Email email = new Email();
		email.setTo(mejl);
		email.setSubject("Odgovor o zahtevu za informacije od javnog značaja");		
		String text = "Poštovani/a " + ime + " " + prezime + ", \n\n"
				+ "Odgovor/i na zahtev za informacijama od javnog značaja koji ste podneli dana " 
				+ datumZahtevaEmail + "nalaze se u linkovima ispod: \n"
				+ Constants.BACKEND_URL + "/api/odgovori/" + broj + "/html\n"
				+ Constants.BACKEND_URL + "/api/odgovori/" + broj + "/pdf\n\n"
				+ "Svako dobro, \n\n"
				+ naziv + "\n" 
				+ sedisteEmail;
		email.setText(text);
		this.emailService.sendEmail(email);
		
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
		
		Document odgovoriDocument = this.domParser.emptyDocument();
		Node odgovori = odgovoriDocument.createElementNS(Namespaces.ODGOVOR, "Odgovori");
		odgovoriDocument.appendChild(odgovori);
		ResourceSet result = this.odgovorRepository.list(xpathExp);
		ResourceIterator it = result.getIterator();
		
		while (it.hasMoreResources()) {
			XMLResource resource = (XMLResource) it.nextResource();
			Document document = this.domParser.buildDocument(resource.getContent().toString());
			Node odgovor = odgovoriDocument.createElementNS(Namespaces.ODGOVOR, "Odgovor");
			Node tipOdgovora = odgovoriDocument.createElementNS(Namespaces.ODGOVOR, "tipOdgovora");
			tipOdgovora.setTextContent(getTipOdgovora(((Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0)).getAttributeNS(Namespaces.XSI, "type")) + "");
			odgovor.appendChild(tipOdgovora);
			odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
			odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
			odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.ODGOVOR, "datumZahteva").item(0), true));
			odgovori.appendChild(odgovor);
		}
		
		return this.domParser.buildXml(odgovoriDocument);
	}
	
	public String html(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.odgovorRepository.load(broj);
		String xslPath;
		TipOdgovora tipOdgovora = getTipOdgovora(((Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0)).getAttributeNS(Namespaces.XSI, "type"));
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
		Document document = this.odgovorRepository.load(broj);
		String xslPath;
		TipOdgovora tipOdgovora = getTipOdgovora(((Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0)).getAttributeNS(Namespaces.XSI, "type"));
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
		Document document = this.odgovorRepository.load(broj);
		String xslFoPath;
		TipOdgovora tipOdgovora = getTipOdgovora(((Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0)).getAttributeNS(Namespaces.XSI, "type"));
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
		if (tipOdgovora.equals("odgovor:TObavestenje")) {
			return TipOdgovora.obavestenje;
		}
		return TipOdgovora.odbijanje;
	}
	
}
