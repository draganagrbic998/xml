package com.example.demo.service.odluka;

import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import com.example.demo.fuseki.Prefixes;
import com.example.demo.model.enums.TipOdgovora;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.service.KorisnikService;
import com.ibm.icu.text.SimpleDateFormat;

@Component
public class OdlukaMapper {
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private ZahtevExist zahtevRepository;
	
	@Autowired
	private KorisnikService korisnikService;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
	//private static final SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyy.");
	
	public Document map(String xml) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		
		Document document = this.domParser.buildDocument(xml);
		Element odgovor = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odgovor").item(0);
		String brojZahteva = odgovor.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		Document zahtevDocument = this.zahtevRepository.load(brojZahteva);
		Element zahtev = (Element) zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		Element gradjanin = (Element) document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Gradjanin").item(0), true);
		Element organVlasti = (Element) document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);		
		gradjanin.getElementsByTagNameNS(Namespaces.OSNOVA, "potpis").item(0).setTextContent(this.korisnikService.currentUser().getOsoba().getPotpis());
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));			
		documentFragment.appendChild(datum);
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(organVlasti);
		odgovor.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));	

		Node datumZahteva = document.createElementNS(Namespaces.ODLUKA, "odgovor:datumZahteva");
		datumZahteva.setTextContent(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		TipOdgovora tipOdgovora = getTipOdgovora(document);
		if (tipOdgovora.equals(TipOdgovora.obavestenje)) {
			odgovor.insertBefore(datumZahteva, odgovor.getElementsByTagNameNS(Namespaces.ODLUKA, "Uvid").item(0));
		}
		else {
			odgovor.appendChild(datumZahteva);
		}
		
		return document;
		//String broj = this.odlukaRepository.save(document, null);
		
		/*
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
		this.emailService.sendEmail(email);*/
	}
	
	public String map(ResourceSet resources) throws ParserConfigurationException, XMLDBException, SAXException, IOException, TransformerException {
		Document odgovoriDocument = this.domParser.emptyDocument();
		Node odgovori = odgovoriDocument.createElementNS(Namespaces.ODLUKA, "Odgovori");
		odgovoriDocument.appendChild(odgovori);
		ResourceIterator it = resources.getIterator();
		
		while (it.hasMoreResources()) {
			XMLResource resource = (XMLResource) it.nextResource();
			Document document = this.domParser.buildDocument(resource.getContent().toString());
			Node odgovor = odgovoriDocument.createElementNS(Namespaces.ODLUKA, "Odgovor");
			Node tipOdgovora = odgovoriDocument.createElementNS(Namespaces.ODLUKA, "tipOdgovora");
			tipOdgovora.setTextContent(getTipOdgovora(document) + "");
			odgovor.appendChild(tipOdgovora);
			odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
			odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
			odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0), true));
			odgovori.appendChild(odgovor);
		}
		
		return this.domParser.buildXml(odgovoriDocument);
	}
	
	public Model[] map(Document document) {
		String email = this.korisnikService.currentUser().getOsoba().getMejl();
		String broj = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		String datum = document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent();
		String mesto = document.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0).getTextContent();
		String brojZahteva = document.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", Prefixes.PREDIKAT);
		
		model.add(
			model.createResource(Prefixes.ODLUKA_PREFIX + broj),
			model.createProperty(Prefixes.PREDIKAT, "datum"),
			model.createLiteral(datum)
		);
		
		model.add(
			model.createResource(Prefixes.ODLUKA_PREFIX + broj),
			model.createProperty(Prefixes.PREDIKAT, "mesto"),
			model.createLiteral(mesto)
		);
		
		model.add(
			model.createResource(Prefixes.ODLUKA_PREFIX + broj),
			model.createProperty(Prefixes.PREDIKAT, "sastavljenoOdStrane"),
			model.createResource(Prefixes.KORISNIK_PREFIX + email)
		);
		
		model.add(
			model.createResource(Prefixes.ODLUKA_PREFIX + broj),
			model.createProperty(Prefixes.PREDIKAT + "podneseniZahtev"),
			model.createResource(Prefixes.ZAHTEV_PREFIX + brojZahteva)
		);
		
		
		Model model2 = ModelFactory.createDefaultModel();
		model2.setNsPrefix("pred", Prefixes.PREDIKAT);
		
		
		model2.add(
			model.createResource(Prefixes.KORISNIK_PREFIX + email),
			model.createProperty(Prefixes.PREDIKAT, "sastavio"),
			model.createResource(Prefixes.ODLUKA_PREFIX + broj)
		);
		
		Model model3 = ModelFactory.createDefaultModel();
		model3.setNsPrefix("pred", Prefixes.PREDIKAT);
		
		model3.add(
			model.createResource(Prefixes.ZAHTEV_PREFIX + brojZahteva),
			model.createProperty(Prefixes.PREDIKAT + "donesenaOdluka"),
			model.createResource(Prefixes.ODLUKA_PREFIX + broj)
		);
		

		Model[] models = new Model[3];
		models[0] = model;
		models[1] = model2;
		models[2] = model3;
		return models;
	}
	
	public static TipOdgovora getTipOdgovora(Document document) {
		String tipOdgovora = ((Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odgovor").item(0)).getAttributeNS(Namespaces.XSI, "type");
		if (tipOdgovora.contains("TObavestenje")) {
			return TipOdgovora.obavestenje;
		}
		return TipOdgovora.odbijanje;
	}
	
}
