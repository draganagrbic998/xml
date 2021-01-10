package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.mapper.ZalbaMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.ws.utils.SOAPDocument;
import com.example.demo.ws.utils.SOAPService;

@Service
public class ZalbaService implements ServiceInterface {
	
	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private ZalbaMapper zalbaMapper;

	@Autowired
	private ZalbaRDF zalbaRDF;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private SOAPService soapService;

	@Override
	public void add(String xml) {
		Document document = this.zalbaMapper.map(xml);
		this.zalbaExist.add(document);
		this.zalbaRDF.add(this.zalbaMapper.map(document));
	}

	@Override
	public void update(String documentId, Document document) {
		this.zalbaExist.update(documentId, document);
	}

	@Override
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/zalba:Zalba";
		} else {
			xpathExp = String.format("/zalba:Zalba[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		ResourceSet resources = this.zalbaExist.retrieve(xpathExp);
		return this.zalbaMapper.map(resources);
	}

	@Override
	public Document load(String documentId) {
		return this.zalbaExist.load(documentId);
	}
	
	public void odustani(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odustato + "");
		this.zalbaExist.update(broj, document);
		// dodaj slanje mejla
	}

	public void obustavi(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.obustaljveno + "");
		this.zalbaExist.update(broj, document);
	}

	public void prosledi(String broj) {
		Document document = this.zalbaExist.load(broj);
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		zalba.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.prosledjeno + "");
		Node datumProsledjivanja = document.createElementNS(Namespaces.ZALBA, "zalba:datumProsledjivanja");
		datumProsledjivanja.setTextContent(Constants.sdf.format(new Date()));
		zalba.insertBefore(datumProsledjivanja, document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0));
		this.zalbaExist.update(broj, document);
		this.soapService.sendSOAPMessage(null, document, SOAPDocument.zalba);
	}
	
}
