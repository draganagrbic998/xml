package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZahteva;
import com.example.demo.exist.SearchUtil;
import com.example.demo.mapper.ZahtevMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Pretraga;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.ZahtevRDF;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.service.email.NotificationManager;

@Service
public class ZahtevService implements ServiceInterface {
	
	public static final long CUTANJE_MILISECONDS = 10000;

	@Autowired
	private ZahtevExist zahtevExist;
			
	@Autowired
	private ZahtevRDF zahtevRDF;

	@Autowired
	private ZahtevMapper zahtevMapper;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private NotificationManager notificationManager;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private JAXBParser jaxbParser;
					
	@Override
	public void add(String xml) {
		Document document = this.zahtevMapper.map(xml);
		this.zahtevExist.add(document);
		this.zahtevRDF.add(document);		
	}

	@Override
	public void update(String documentId, Document document) {
		this.zahtevExist.update(documentId, document);
		this.zahtevRDF.update(documentId, document);
	}
	
	@Override
	public void delete(String documentId) {
		this.zahtevExist.delete(documentId);
		this.zahtevRDF.delete(documentId);
	}

	@Override
	public String findAll() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/zahtev:Zahtev";
		}
		else {
			xpathExp = String.format("/zahtev:Zahtev[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		return this.zahtevMapper.map(this.zahtevExist.findAll(xpathExp));
	}

	@Override
	public Document find(String documentId) {
		return this.zahtevExist.find(documentId);
	}
	
	@Override
	public String nextDocumentId() {
		return this.zahtevExist.nextDocumentId();
	}

	@Override
	public String regularSearch(String xml) {
		Korisnik korisnik = this.korisnikService.currentUser();
		String prefix;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			prefix = "/zahtev:Zahtev";
		}
		else {
			prefix = String.format("/zahtev:Zahtev[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		Pretraga pretraga = (Pretraga) this.jaxbParser.unmarshalFromXml(xml, Pretraga.class);
		String xpathExp = String.format("%s%s", prefix, SearchUtil.pretragaToXpath(pretraga));
		ResourceSet resources = this.zahtevExist.findAll(xpathExp);
		return this.zahtevMapper.map(resources);
	}

	@Override
	public String advancedSearch(String xml) {
		Korisnik korisnik = this.korisnikService.currentUser();
		String prefix;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			prefix = "/zahtev:Zahtev";
		}
		else {
			prefix = String.format("/zahtev:Zahtev[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		String xpathExp = String.format("%s%s", prefix, this.zahtevRDF.search(xml));
		ResourceSet resources = this.zahtevExist.findAll(xpathExp);
		return this.zahtevMapper.map(resources);
	}

	public List<String> odluke(String documentId) {
		return this.zahtevRDF.odluke(documentId);
	}

	public List<String> zalbe(String documentId) {
		return this.zahtevRDF.zalbe(documentId);
	}

	public List<String> resenja(String documentId) {
		return this.zahtevRDF.resenja(documentId);
	}

	//@Scheduled(fixedDelay = CUTANJE_MILISECONDS_LIMIT, initialDelay = CUTANJE_MILISECONDS_LIMIT)
	public void cutanjeUprave() throws XMLDBException {
		String xpathExp = String.format("/zahtev:Zahtev[(%d - zahtev:vreme) >= %d][zahtev:status = 'cekanje']", new Date().getTime(), CUTANJE_MILISECONDS);
		ResourceSet resources = this.zahtevExist.findAll(xpathExp);
		ResourceIterator it = resources.getIterator();
		while (it.hasMoreResources()) {
			XMLResource resource = (XMLResource) it.nextResource();
			Document document = this.domParser.buildDocument(resource.getContent().toString());
			document.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odbijeno + "");
			this.zahtevExist.update(Utils.getBroj(document), document);
			
			this.notificationManager.notifyCutanjeUprave(document);
			System.out.println("ULOVLJEN ZAHTEV BROJ " + Utils.getBroj(document));
		}
	}
	
}
