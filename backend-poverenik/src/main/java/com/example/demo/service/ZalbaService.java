package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.mapper.ZalbaMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.model.ZalbaPretraga;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.ws.utils.SOAPActions;
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
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private DOMParser domParser;

	@Override
	public void add(String xml) {
		Document document = this.zalbaMapper.map(xml);
		this.zalbaExist.add(document);
		this.zalbaRDF.add(document);
	}

	@Override
	public void update(String documentId, Document document) {
		this.zalbaExist.update(documentId, document);
		this.zalbaRDF.update(documentId, document);
	}
	
	@Override
	public void delete(String documentId) {
		this.zalbaExist.delete(documentId);
		this.zalbaRDF.delete(documentId);
	}

	@Override
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/zalba:Zalba";
		} 
		else {
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
		boolean propagate = false;
		Document document = this.zalbaExist.load(broj);
		if (!document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).getTextContent().equals(StatusZalbe.cekanje + "")) {
			propagate = true;
		}
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odustato + "");
		this.zalbaExist.update(broj, document);
		if (propagate) {
			this.soapService.sendSOAPMessage(this.domParser.buildDocument("<broj>" + broj + "</broj>"), SOAPActions.zalba_odustani);
		}
	}

	public void obustavi(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.obustavljeno + "");
		this.zalbaExist.update(broj, document);
		this.soapService.sendSOAPMessage(this.domParser.buildDocument("<broj>" + broj + "</broj>"), SOAPActions.zalba_obustavi);
	}
	
	public void otkazi(String brojZahteva) {
		try {
			String xpathExp = String.format("/zalba:Zalba[zalba:PodaciZahteva/broj='%s']", brojZahteva);
			ResourceSet resources = this.zalbaExist.retrieve(xpathExp);
			ResourceIterator it = resources.getIterator();
			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.ispravljeno + "");
				this.zalbaExist.update(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent(), document);
			}
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

	public void prosledi(String broj) {
		Document document = this.zalbaExist.load(broj);
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.prosledjeno + "");
		Node datumProsledjivanja = document.createElementNS(Namespaces.ZALBA, "zalba:datumProsledjivanja");
		datumProsledjivanja.setTextContent(Constants.sdf.format(new Date()));
		zalba.insertBefore(datumProsledjivanja, document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0));
		this.zalbaExist.update(broj, document);

		Element podaciZahteva = (Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		podaciZahteva.removeChild(podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		try {
			Element podaciOdluke = (Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			podaciOdluke.removeChild(podaciOdluke.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		}
		catch(Exception e) {
			;
		}
		this.soapService.sendSOAPMessage(document, SOAPActions.create_zalba);
	}

	@Override
	public String advancedSearch(String xml) {
		ZalbaPretraga search = (ZalbaPretraga) this.jaxbParser.unmarshalFromXml(xml, ZalbaPretraga.class);
		String xpathExp = String.format("/zalba:Zalba[%s]", this.zalbaRDF.search(search));
		ResourceSet resources = this.zalbaExist.retrieve(xpathExp);
		return this.zalbaMapper.map(resources);
	}
	
}
