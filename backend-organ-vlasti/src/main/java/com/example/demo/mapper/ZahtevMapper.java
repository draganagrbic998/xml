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
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZahteva;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.OrganVlastiService;
import com.example.demo.service.ZahtevService;

@Component
public class ZahtevMapper implements MapperInterface {

	public static final String STUB_FILE = Constants.STUB_FOLDER + "zahtev.xml";

	@Autowired
	private DOMParser domParser;
				
	@Autowired
	private ZahtevService zahtevService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private OrganVlastiService organVlastiService;
	
	@Override
	public Document map(String xml) {
		Document dto = this.domParser.buildDocument(xml);
		Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
		Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();

		zahtev.setAttribute("about", Namespaces.ZAHTEV + "/" + this.zahtevService.nextDocumentId());
		zahtev.setAttribute("href", Namespaces.KORISNIK + "/" + this.korisnikService.currentUser().getMejl());
		zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(Constants.sdf.format(new Date()));

		Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
		gradjanin.appendChild(document.importNode(this.korisnikService.loadUser(this.korisnikService.currentUser().getMejl()).getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		gradjanin.appendChild(document.importNode(this.korisnikService.loadUser(this.korisnikService.currentUser().getMejl()).getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(document.importNode(this.organVlastiService.load().getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		documentFragment.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		zahtev.insertBefore(documentFragment, zahtev.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0));

		zahtev.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.ZAHTEV, "tipZahteva").item(0), true));
		try {
			zahtev.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.ZAHTEV, "tipDostave").item(0), true));
			zahtev.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.ZAHTEV, "opisDostave").item(0), true));
		}
		catch(Exception e) {
			;
		}

		Node vreme = document.createElementNS(Namespaces.ZAHTEV, "zahtev:vreme");
		vreme.setTextContent(new Date().getTime() + "");
		zahtev.appendChild(vreme);		
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
				Document zahtevDocument = this.domParser.buildDocument(resource.getContent().toString());
				Node zahtev = zahteviDocument.createElementNS(Namespaces.ZAHTEV, "Zahtev");

				Node broj = zahteviDocument.createElementNS(Namespaces.OSNOVA, "broj");
				broj.setTextContent(Utils.getBroj(zahtevDocument));
				zahtev.appendChild(broj);
				zahtev.appendChild(zahteviDocument.importNode(zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "tipZahteva").item(0), true));
				zahtev.appendChild(zahteviDocument.importNode(zahtevDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				zahtev.appendChild(zahteviDocument.importNode(zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0), true));
			
				Node reference = zahteviDocument.createElementNS(Namespaces.OSNOVA, "Reference");
				Utils.setReferences(zahteviDocument, reference, this.zahtevService.odluke(Utils.getBroj(zahtevDocument)), "odluke");
				Utils.setReferences(zahteviDocument, reference, this.zahtevService.zalbe(Utils.getBroj(zahtevDocument)), "zalbe");
				Utils.setReferences(zahteviDocument, reference, this.zahtevService.resenja(Utils.getBroj(zahtevDocument)), "resenja");
				zahtev.appendChild(reference);
				zahtevi.appendChild(zahtev);						
			}
			
			
			return this.domParser.buildXml(zahteviDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public static StatusZahteva getStatusZahteva(Document document) {
		return StatusZahteva.valueOf(document.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).getTextContent());
	}
	
}
