package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.exist.SearchUtil;
import com.example.demo.mapper.OdgovorMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.model.ObicnaPretraga;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.xml.OdgovorExist;

@Service
public class OdgovorService implements ServiceInterface {

	@Autowired
	private OdgovorExist odgovorExist;
	
	@Autowired
	private OdgovorRDF odgovorRDF;

	@Autowired
	private OdgovorMapper odgovorMapper;
	
	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private ZalbaService zalbaService;
	
	@Autowired
	private JAXBParser jaxbParser;
				
	@Override
	public void add(String xml) {
		Document document = this.odgovorMapper.map(xml);
		String brojZalbe = Utils.getBroj(document);
		Document zalbaDocument = this.zalbaService.load(brojZalbe);

		this.odgovorExist.update(Utils.getBroj(document), document);
		this.odgovorRDF.add(document);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odgovoreno + "");
		this.zalbaService.update(brojZalbe, zalbaDocument);
	}

	@Override
	public void update(String documentId, Document document) {
		this.odgovorExist.update(documentId, document);
		this.odgovorRDF.update(documentId, document);
	}
	
	@Override
	public void delete(String documentId) {
		this.odgovorExist.delete(documentId);
		this.odgovorRDF.delete(documentId);
	}

	@Override
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/odgovor:Odgovor";
		} 
		else {
			xpathExp = String.format("/odgovor:Odgovor[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		return this.odgovorMapper.map(this.odgovorExist.retrieve(xpathExp));
	}

	@Override
	public Document load(String documentId) {
		return this.odgovorExist.load(documentId);
	}
	
	@Override
	public String nextDocumentId() {
		return this.odgovorExist.nextDocumentId();
	}

	@Override
	public String regularSearch(String xml) {
		ObicnaPretraga pretraga = (ObicnaPretraga) this.jaxbParser.unmarshalFromXml(xml, ObicnaPretraga.class);
		String xpathExp = String.format("/odgovor:Odgovor%s", SearchUtil.pretragaXpath(pretraga));
		ResourceSet resources = this.odgovorExist.retrieve(xpathExp);
		return this.odgovorMapper.map(resources);
	}

	@Override
	public String advancedSearch(String xml) {
		String xpathExp = String.format("/odgovor:Odgovor%s", this.odgovorRDF.search(xml));
		ResourceSet resources = this.odgovorExist.retrieve(xpathExp);
		return this.odgovorMapper.map(resources);
	}

	public List<String> resenja(String documentId) {
		return this.odgovorRDF.resenja(documentId);
	}

}
