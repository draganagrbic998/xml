package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.exception.MyException;
import com.example.demo.exception.ResourceTakenException;
import com.example.demo.mapper.ZalbaMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.ws.utils.SOAPActions;
import com.example.demo.ws.utils.SOAPService;

@Service
public class ZalbaService implements ServiceInterface {
	
	public static final String PONISTAVANJE_STUB = Constants.STUB_FOLDER + "ponistavanje.xml";
	
	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private ZalbaRDF zalbaRDF;
	
	@Autowired
	private ZalbaMapper zalbaMapper;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private SOAPService soapService;
		
	@Autowired
	private DOMParser domParser;
		
	@Autowired
	private ResenjeService resenjeService;
	
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
			xpathExp = String.format("/zalba:Zalba[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		return this.zalbaMapper.map(this.zalbaExist.retrieve(xpathExp));
	}

	@Override
	public Document load(String documentId) {
		return this.zalbaExist.load(documentId);
	}

	@Override
	public String nextDocumentId() {
		return this.zalbaExist.nextDocumentId();
	}
	
	@Override
	public String regularSearch(String xml) {
		return null;
	}

	@Override
	public String advancedSearch(String xml) {
		return null;
	}
	
	public List<String> odgovori(String documentId) {
		return this.zalbaRDF.odgovori(documentId);
	}
	
	public List<String> resenja(String documentId) {
		return this.zalbaRDF.resenja(documentId);
	}
	
	public void obustavi(String broj) {
		Document document = this.zalbaExist.load(broj);
		StatusZalbe status = ZalbaMapper.getStatusZalbe(document);
		if (!status.equals(StatusZalbe.odustato) && !status.equals(StatusZalbe.obavesteno)) {
			throw new ResourceTakenException();
		}
		
		Document dto = this.domParser.buildDocumentFromFile(PONISTAVANJE_STUB);
		dto.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0)
			.setTextContent(ZalbaMapper.getStatusZalbe(document) + "");
		dto.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0)
			.setTextContent(broj);
		this.resenjeService.add(this.domParser.buildXml(dto));
	}

	public void prosledi(String broj) {
		Document document = this.zalbaExist.load(broj);
		StatusZalbe status = ZalbaMapper.getStatusZalbe(document);
		if (!status.equals(StatusZalbe.cekanje)) {
			throw new ResourceTakenException();
		}

		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.prosledjeno + "");
		Node datumProsledjivanja = document.createElementNS(Namespaces.ZALBA, "zalba:datumProsledjivanja");
		datumProsledjivanja.setTextContent(Constants.sdf.format(new Date()));
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		zalba.insertBefore(datumProsledjivanja, document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0));
		this.soapService.sendSOAPMessage(document, SOAPActions.create_zalba);
		this.zalbaExist.update(broj, document);
		this.zalbaRDF.update(broj, document);
	}
	
	public void odustani(String broj) {
		Document document = this.zalbaExist.load(broj);
		StatusZalbe status = ZalbaMapper.getStatusZalbe(document);
		if (!status.equals(StatusZalbe.cekanje) && !status.equals(StatusZalbe.prosledjeno) && !status.equals(StatusZalbe.odgovoreno)) {
			throw new ResourceTakenException();
		}
		
		if (document.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").getLength() > 0) {
			this.soapService.sendSOAPMessage(this.domParser.buildDocument("<broj>" + broj + "</broj>"), SOAPActions.zalba_odustani);
		}
				
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		zalba.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odustato + "");
		Node datumOtkazivanja = document.createElementNS(Namespaces.ZALBA, "zalba:datumOtkazivanja");
		datumOtkazivanja.setTextContent(Constants.sdf.format(new Date()));
		zalba.insertBefore(datumOtkazivanja, zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0));
		this.zalbaExist.update(broj, document);
		this.zalbaRDF.update(broj, document);
	}

	public void otkazi(String brojZahteva) {
		try {
			String xpathExp = String.format("/zalba:Zalba[zalba:PodaciZahteva/@href='%s']", Namespaces.ZAHTEV + "/" + brojZahteva);
			ResourceSet resources = this.zalbaExist.retrieve(xpathExp);
			ResourceIterator it = resources.getIterator();
			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
				zalba.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.obavesteno + "");
				Node datumOtkazivanja = document.createElementNS(Namespaces.ZALBA, "zalba:datumOtkazivanja");
				datumOtkazivanja.setTextContent(Constants.sdf.format(new Date()));
				zalba.insertBefore(datumOtkazivanja, zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0));
				this.zalbaExist.update(Utils.getBroj(document), document);
				this.zalbaRDF.update(Utils.getBroj(document), document);
			}
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}
