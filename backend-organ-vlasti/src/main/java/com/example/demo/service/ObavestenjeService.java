package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
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
import com.example.demo.controller.ObavestenjeDTO;
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.StatusZahteva;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.ObavestenjeRepository;
import com.example.demo.repository.ZahtevRepository;
import com.ibm.icu.text.SimpleDateFormat;

@Service
public class ObavestenjeService {
		
	@Autowired
	private ObavestenjeRepository obavestenjeRepository;

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + File.separatorChar + "obavestenje_fo.xsl";
	private static final String XSL_PATH = Constants.XSL_FOLDER + File.separatorChar + "/obavestenje.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "obavestenja" + File.separatorChar;

	public void save(String brojZahteva, String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		Document document = this.domParser.buildDocument(xml);
		this.domParser.removeXmlSpace(document);
		Element obavestenje = (Element) document.getElementsByTagNameNS(Namespaces.OBAVESTENJE, "Obavestenje").item(0);
		Document zahtevDocument = this.zahtevRepository.load(brojZahteva);
		Element zahtev = (Element) zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		
		DocumentFragment documentFragment = document.createDocumentFragment();
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		Node mesto = document.createElementNS(Namespaces.OSNOVA, "mesto");
		mesto.setTextContent(Constants.TEST_MESTO);
		Node potpis = document.createElementNS(Namespaces.OSNOVA, "potpis");
		potpis.setTextContent(Constants.TEST_POTPIS);
		Node gradjanin = document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Gradjanin").item(0), true);
		Node organVlasti = document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);		
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));			
		documentFragment.appendChild(datum);
		documentFragment.appendChild(mesto);
		documentFragment.appendChild(potpis);
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(organVlasti);
		obavestenje.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));	

		//nzm sto moram da stavim ovaj prefix zahtev ispred, provericu to kasnije
		Node datumZahteva = document.createElementNS(Namespaces.OBAVESTENJE, "obavestenje:datumZahteva");
		datumZahteva.setTextContent(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		obavestenje.appendChild(datumZahteva);
		
		zahtev.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odobreno + "");
		this.zahtevRepository.save(zahtevDocument, brojZahteva);
		this.obavestenjeRepository.save(document, null);
	}
	
	public List<ObavestenjeDTO> retrieve() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException{
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/obavestenje:Obavestenje";
		}
		else {
			xpathExp = String.format("/obavestenje:Obavestenje[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		
		List<ObavestenjeDTO> obavestenja = new ArrayList<>();
		ResourceSet result = this.obavestenjeRepository.list(xpathExp);
		ResourceIterator it = result.getIterator();
		while (it.hasMoreResources()) {
			XMLResource resource = (XMLResource) it.nextResource();
			Document document = this.domParser.buildDocument(resource.getContent().toString());
			String broj = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
			String datum = document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent();
			String datumZahteva = document.getElementsByTagNameNS(Namespaces.OBAVESTENJE, "datumZahteva").item(0).getTextContent();
			obavestenja.add(new ObavestenjeDTO(broj, datum, datumZahteva));
		}
		return obavestenja;
	}
	
	public Resource generateHtml(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.obavestenjeRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, XSL_PATH);
		Path file = Paths.get(GEN_PATH + broj + ".html");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
	public Resource generatePdf(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.obavestenjeRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, XSL_FO_PATH);
		Path file = Paths.get(GEN_PATH + broj + ".pdf");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
}
