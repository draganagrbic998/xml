package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.exception.MyException;
import com.example.demo.exist.SearchUtil;
import com.example.demo.mapper.ZalbaMapper;
import com.example.demo.model.Pretraga;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.ZalbaExist;

@Service
public class ZalbaService implements ServiceInterface {

	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private ZalbaMapper zalbaMapper;

	@Autowired
	private ZalbaRDF zalbaRDF;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private JAXBParser jaxbParser;
				
	@Override
	public void add(String xml) {
		Document document = this.zalbaMapper.map(xml);
		Element podaciZahteva = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		podaciZahteva.removeChild(podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		try {
			Element podaciOdluke = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			podaciOdluke.removeChild(podaciOdluke.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		}
		catch(Exception e) {
			;
		}
		this.zalbaExist.update(Utils.getBroj(document), document);
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
		return this.zalbaMapper.map(this.zalbaExist.retrieve("/zalba:Zalba"));
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
		Pretraga pretraga = (Pretraga) this.jaxbParser.unmarshalFromXml(xml, Pretraga.class);
		String xpathExp = String.format("/zalba:Zalba%s", SearchUtil.pretragaToXpath(pretraga));
		ResourceSet resources = this.zalbaExist.retrieve(xpathExp);
		return this.zalbaMapper.map(resources);
	}

	@Override
	public String advancedSearch(String xml) {
		String xpathExp = String.format("/zalba:Zalba%s", this.zalbaRDF.search(xml));
		ResourceSet resources = this.zalbaExist.retrieve(xpathExp);
		return this.zalbaMapper.map(resources);
	}
	
	public List<String> odgovori(String documentId) {
		return this.zalbaRDF.odgovori(documentId);
	}

	public List<String> resenja(String documentId) {
		return this.zalbaRDF.resenja(documentId);
	}
	
	public void odustani(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odustato + "");
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
				document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.obavesteno + "");
				this.zalbaExist.update(Utils.getBroj(document), document);
				this.zalbaRDF.update(Utils.getBroj(document), document);
			}
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
