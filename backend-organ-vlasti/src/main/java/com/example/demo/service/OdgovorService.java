package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.exception.ResourceTakenException;
import com.example.demo.exist.SearchUtil;
import com.example.demo.mapper.OdgovorMapper;
import com.example.demo.mapper.ZalbaMapper;
import com.example.demo.model.Pretraga;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.SOAPActions;

@Service
public class OdgovorService implements ServiceInterface {

	@Autowired
	private OdgovorExist odgovorExist;
	
	@Autowired
	private OdgovorRDF odgovorRDF;

	@Autowired
	private OdgovorMapper odgovorMapper;

	@Autowired
	private ZalbaService zalbaService;
		
	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private JAXBParser jaxbParser;
		
	@Override
	public void add(String xml) {
		Document document = this.odgovorMapper.map(xml);
		Document zalbaDocument = this.zalbaService.find(Utils.getBroj(document));
		if (!ZalbaMapper.getStatusZalbe(zalbaDocument).equals(StatusZalbe.prosledjeno)) {
			throw new ResourceTakenException();
		}

		this.soapService.sendSOAPMessage(document, SOAPActions.create_odgovor);	
		this.odgovorExist.update(Utils.getBroj(document), document);
		this.odgovorRDF.add(document);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odgovoreno + "");
		this.zalbaService.update(Utils.getBroj(zalbaDocument), zalbaDocument);
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
	public String findAll() {
		return this.odgovorMapper.map(this.odgovorExist.findAll("/odgovor:Odgovor"));
	}
	
	@Override
	public Document find(String documentId) {
		return this.odgovorExist.find(documentId);
	}
	
	@Override
	public String nextDocumentId() {
		return this.odgovorExist.nextDocumentId();
	}
	
	@Override
	public String regularSearch(String xml) {
		Pretraga pretraga = (Pretraga) this.jaxbParser.unmarshalFromXml(xml, Pretraga.class);
		String xpathExp = String.format("/odgovor:Odgovor%s", SearchUtil.pretragaToXpath(pretraga));
		ResourceSet resources = this.odgovorExist.findAll(xpathExp);
		return this.odgovorMapper.map(resources);
	}

	@Override
	public String advancedSearch(String xml) {
		String xpathExp = String.format("/odgovor:Odgovor%s", this.odgovorRDF.search(xml));
		ResourceSet resources = this.odgovorExist.findAll(xpathExp);
		return this.odgovorMapper.map(resources);
	}

	public List<String> resenja(String documentId) {
		return this.odgovorRDF.resenja(documentId);
	}

}
