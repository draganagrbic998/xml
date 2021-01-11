package com.example.demo.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Prefixes;
import com.example.demo.enums.StatusZahteva;
import com.example.demo.exist.ExistManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.OrganVlastiService;

@Component
public class ZahtevMapper implements MapperInterface {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private OrganVlastiService organVlastiService;

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private KorisnikExist korisnikExist;
	
	@Autowired
	private ExistManager existManager;

	@Override
	public Document map(String xml) {
		Document document = this.domParser.buildDocument(xml);
		Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		
		Node broj = document.createElementNS(Namespaces.OSNOVA, "broj");
		broj.setTextContent(this.existManager.nextDocumentId(ZahtevExist.ZAHTEV_COLLECTION));
		Node datum = document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0);
		datum.setTextContent(Constants.sdf.format(new Date()));
		zahtev.insertBefore(broj, datum);

		Element korisnik = (Element) this.korisnikExist.load(this.korisnikService.currentUser().getOsoba().getMejl()).getElementsByTagNameNS(Namespaces.OSNOVA, "Korisnik").item(0);
		Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
		Node organVlasti = document.importNode(this.organVlastiService.load().getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(organVlasti);
		zahtev.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		
		Node status = document.createElementNS(Namespaces.ZAHTEV, "zahtev:status");
		status.setTextContent(StatusZahteva.cekanje + "");
		zahtev.appendChild(status);
		zahtev.setAttribute("about", Prefixes.ZAHTEV_PREFIX + broj.getTextContent());
		return document;
	}

	@Override
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
}
