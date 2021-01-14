package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.mapper.ZahtevMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.model.ZahtevSearch;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.ZahtevRDF;
import com.example.demo.repository.xml.ZahtevExist;

@Service
public class ZahtevService implements ServiceInterface {
	
	@Autowired
	private ZahtevExist zahtevExist;
			
	@Autowired
	private ZahtevRDF zahtevRDF;

	@Autowired
	private ZahtevMapper zahtevMapper;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	//dodaj interfejs za obicnu/naprednu pretragu
		
	@Override
	public void add(String xml) {
		Document document = this.zahtevMapper.map(xml);
		this.zahtevExist.add(document);
		this.zahtevRDF.add(this.xslTransformer.generateMetadata(document));		
	}

	@Override
	public void update(String documentId, Document document) {
		this.zahtevExist.update(documentId, document);
	}
	
	public String advancedSearch(String xml) {
		ZahtevSearch zahtevSearch = (ZahtevSearch) this.jaxbParser.unmarshalFromXml(xml, ZahtevSearch.class);
		String xpathExp = String.format("/zahtev:Zahtev[%s]", this.zahtevRDF.search(zahtevSearch));
		ResourceSet resources = this.zahtevExist.retrieve(xpathExp);
		return this.zahtevMapper.map(resources);
	}

	@Override
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/zahtev:Zahtev";
		}
		else {
			xpathExp = String.format("/zahtev:Zahtev[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		ResourceSet resources = this.zahtevExist.retrieve(xpathExp);
		return this.zahtevMapper.map(resources);
	}

	@Override
	public Document load(String documentId) {
		return this.zahtevExist.load(documentId);
	}
	
}
