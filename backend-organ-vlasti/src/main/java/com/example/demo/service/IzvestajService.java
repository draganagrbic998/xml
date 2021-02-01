package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.exception.MyException;
import com.example.demo.exception.ResourceTakenException;
import com.example.demo.exist.SearchUtil;
import com.example.demo.mapper.IzvestajMapper;
import com.example.demo.model.Pretraga;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.IzvestajRDF;
import com.example.demo.repository.xml.IzvestajExist;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.SOAPActions;

@Service
public class IzvestajService implements ServiceInterface {

	@Autowired
	private IzvestajExist izvestajExist;

	@Autowired
	private IzvestajRDF izvestajRDF;

	@Autowired
	private IzvestajMapper izvestajMapper;

	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Override
	public void add(String godina) {
		try {
			if (this.izvestajExist.findAll("/izvestaj:Izvestaj[izvestaj:godina = " + godina + "]").getSize() > 0) {
				throw new ResourceTakenException();
			}
		}
		catch(Exception e) {
			throw new MyException(e);
		}
		Document document = this.izvestajMapper.map(godina);
		this.soapService.sendSOAPMessage(document, SOAPActions.create_izvestaj);
		this.izvestajExist.add(document);
		this.izvestajRDF.add(document);
	}

	@Override
	public void update(String documentId, Document document) {
		this.izvestajExist.update(documentId, document);
		this.izvestajRDF.update(documentId, document);
	}
	
	@Override
	public void delete(String documentId) {
		this.izvestajExist.delete(documentId);
		this.izvestajRDF.delete(documentId);
	}

	@Override
	public String findAll() {
		return this.izvestajMapper.map(this.izvestajExist.findAll("/izvestaj:Izvestaj"));
	}
	
	@Override
	public Document find(String documentId) {
		return this.izvestajExist.find(documentId);
	}
	
	@Override
	public String nextDocumentId() {
		return this.izvestajExist.nextDocumentId();
	}

	@Override
	public String regularSearch(String xml) {
		Pretraga pretraga = (Pretraga) this.jaxbParser.unmarshalFromXml(xml, Pretraga.class);
		String xpathExp = String.format("/izvestaj:Izvestaj%s", SearchUtil.pretragaToXpath(pretraga));
		ResourceSet resources = this.izvestajExist.findAll(xpathExp);
		return this.izvestajMapper.map(resources);
	}

	@Override
	public String advancedSearch(String xml) {
		String xpathExp = String.format("/izvestaj:Izvestaj%s", this.izvestajRDF.search(xml));
		ResourceSet resources = this.izvestajExist.findAll(xpathExp);
		return this.izvestajMapper.map(resources);
	}

}
