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
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.ZahtevRepository;
import com.ibm.icu.text.SimpleDateFormat;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class ZahtevService {
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private OrganVlastiService organVlastiService;
		
	@Autowired
	private XSLTransformer xslTransformer;

	private static final String XSL_PATH = Constants.XSL_FOLDER + File.separatorChar + "zahtev.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + File.separatorChar + "zahtev_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "zahtevi" + File.separatorChar;

	public void save(String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		Document document = this.domParser.buildDocument(xml);
		this.domParser.removeXmlSpace(document);
		Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		
		DocumentFragment documentFragment = document.createDocumentFragment();
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		Node mesto = document.createElementNS(Namespaces.OSNOVA, "mesto");
		mesto.setTextContent(Constants.TEST_MESTO);
		Node potpis = document.createElementNS(Namespaces.OSNOVA, "potpis");
		potpis.setTextContent(Constants.TEST_POTPIS);
		Element korisnik = (Element) this.jaxbParser.marshal(this.korisnikService.currentUser()).getElementsByTagNameNS(Namespaces.OSNOVA, "Korisnik").item(0);
		Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
		Node organVlasti = document.importNode(this.jaxbParser.marshal(this.organVlastiService.load()).getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(mesto);
		documentFragment.appendChild(potpis);
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(organVlasti);
		zahtev.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));

		//nzm sto moram da stavim ovaj prefix zahtev ispred, provericu to kasnije
		Node status = document.createElementNS(Namespaces.ZAHTEV, "zahtev:status");
		status.setTextContent(StatusZahteva.cekanje + "");
		zahtev.appendChild(status);
		this.zahtevRepository.save(document, null);
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

		Document zahteviDocument = this.domParser.emptyDocument();
		Node zahtevi = zahteviDocument.createElementNS(Namespaces.ZAHTEV, "Zahtevi");
		zahteviDocument.appendChild(zahtevi);
		ResourceSet result = this.zahtevRepository.list(xpathExp);
		ResourceIterator it = result.getIterator();
		
		while (it.hasMoreResources()) {
			XMLResource resource = (XMLResource) it.nextResource();
			Document document = this.domParser.buildDocument(resource.getContent().toString());
			Node zahtev = zahteviDocument.createElementNS(Namespaces.ZAHTEV, "Zahtev");
			zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
			zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
			zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZAHTEV, "tipZahteva").item(0), true));
			zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0), true));
			zahtevi.appendChild(zahtev);			
		}
		return this.domParser.buildXml(zahteviDocument);
		
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
