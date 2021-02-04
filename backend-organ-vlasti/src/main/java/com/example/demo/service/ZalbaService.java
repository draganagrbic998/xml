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
		this.zalbaRDF.update(Utils.getBroj(document), document);	
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
	public String findAll() {
		return this.zalbaMapper.map(this.zalbaExist.findAll("/zalba:Zalba"));
	}
	
	@Override
	public Document find(String documentId) {
		return this.zalbaExist.find(documentId);
	}
	
	@Override
	public String nextDocumentId() {
		return this.zalbaExist.nextDocumentId();
	}
	
	@Override
	public String regularSearch(String xml) {
		Pretraga pretraga = (Pretraga) this.jaxbParser.unmarshalFromXml(xml, Pretraga.class);
		String xpathExp = String.format("/zalba:Zalba%s", SearchUtil.pretragaToXpath(pretraga));
		ResourceSet resources = this.zalbaExist.findAll(xpathExp);
		return this.zalbaMapper.map(resources);
	}

	@Override
	public String advancedSearch(String xml) {
		String xpathExp = String.format("/zalba:Zalba%s", this.zalbaRDF.search(xml));
		ResourceSet resources = this.zalbaExist.findAll(xpathExp);
		return this.zalbaMapper.map(resources);
	}
	
	public List<String> odgovori(String documentId) {
		return this.zalbaRDF.odgovori(documentId);
	}

	public List<String> resenja(String documentId) {
		return this.zalbaRDF.resenja(documentId);
	}

	public void otkazi(String brojZahteva) {
		try {
			String xpathExp = String.format("/zalba:Zalba[zalba:PodaciZahteva/@href='%s'][not(zalba:status='ponisteno' or zalba:status='odobreno' or zalba:status='odbijeno')]", Namespaces.ZAHTEV + "/" + brojZahteva);
			ResourceSet resources = this.zalbaExist.findAll(xpathExp);
			ResourceIterator it = resources.getIterator();
			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
				zalba.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.obavesteno + "");
				if (document.getElementsByTagNameNS(Namespaces.ZALBA, "datumOtkazivanja").getLength() > 0) {
					document.getElementsByTagNameNS(Namespaces.ZALBA, "datumOtkazivanja")
					.item(0).setTextContent(Constants.sdf.format(new Date()));
				}
				else {
					Node datumOtkazivanja = document.createElementNS(Namespaces.ZALBA, "zalba:datumOtkazivanja");
					datumOtkazivanja.setTextContent(Constants.sdf.format(new Date()));
					zalba.insertBefore(datumOtkazivanja, zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0));					
				}
				this.zalbaExist.update(Utils.getBroj(document), document);
				this.zalbaRDF.update(Utils.getBroj(document), document);
			}
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
