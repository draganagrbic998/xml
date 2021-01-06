package com.example.demo.service.zahtev;

import java.util.Date;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.exception.MyException;
import com.example.demo.fuseki.Prefixes;
import com.example.demo.model.enums.StatusZahteva;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.OrganVlastiService;
import com.ibm.icu.text.SimpleDateFormat;

@Component
public class ZahtevMapper {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private OrganVlastiService organVlastiService;

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private JAXBParser jaxbParser;
		
	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

	public Document map(String xml) {
		try {
			Document document = this.domParser.buildDocument(xml);
			Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
			DocumentFragment documentFragment = document.createDocumentFragment();
			
			Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
			datum.setTextContent(sdf.format(new Date()));
			Element korisnik = (Element) this.jaxbParser.marshal(this.korisnikService.currentUser()).getElementsByTagNameNS(Namespaces.OSNOVA, "Korisnik").item(0);
			Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
			gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
			gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
			Node organVlasti = document.importNode(this.jaxbParser.marshal(this.organVlastiService.load()).getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);
			documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
			documentFragment.appendChild(datum);
			documentFragment.appendChild(gradjanin);
			documentFragment.appendChild(organVlasti);
			zahtev.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
			Node status = document.createElementNS(Namespaces.ZAHTEV, "zahtev:status");
			status.setTextContent(StatusZahteva.cekanje + "");
			zahtev.appendChild(status);
			
			return document;
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public String map(ResourceSet resources) {
		try {
			Document zahteviDocument = this.domParser.emptyDocument();
			Node zahtevi = zahteviDocument.createElementNS(Namespaces.ZAHTEV, "Zahtevi");
			zahteviDocument.appendChild(zahtevi);
			ResourceIterator it = resources.getIterator();
			
			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node zahtev = zahteviDocument.createElementNS(Namespaces.ZAHTEV, "Zahtev");
				zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZAHTEV, "tipZahteva").item(0), true));
				zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0), true));
				zahtevi.appendChild(zahtev);			
			}
			return this.domParser.buildXml(zahteviDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public Model[] map(Document document) {
		String email = document.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent();
		String broj = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		String datum = document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent();
		String mesto = document.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0).getTextContent();
		
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", Prefixes.PREDIKAT);
		
		model.add(
			model.createResource(Prefixes.ZAHTEV_PREFIX + broj),
			model.createProperty(Prefixes.PREDIKAT, "datum"),
			model.createLiteral(datum)
		);
		
		model.add(
			model.createResource(Prefixes.ZAHTEV_PREFIX + broj),
			model.createProperty(Prefixes.PREDIKAT, "mesto"),
			model.createLiteral(mesto)
		);
		
		model.add(
			model.createResource(Prefixes.ZAHTEV_PREFIX + broj),
			model.createProperty(Prefixes.PREDIKAT, "sastavljenoOdStrane"),
			model.createResource(Prefixes.KORISNIK_PREFIX + email)
		);
		
		Model model2 = ModelFactory.createDefaultModel();
		model2.setNsPrefix("pred", Prefixes.PREDIKAT);
		
		model2.add(
			model.createResource(Prefixes.KORISNIK_PREFIX + email),
			model.createProperty(Prefixes.PREDIKAT, "sastavio"),
			model.createResource(Prefixes.ZAHTEV_PREFIX + broj)
		);

		Model[] models = new Model[2];
		models[0] = model;
		models[1] = model2;
		return models;
	}
	
}
