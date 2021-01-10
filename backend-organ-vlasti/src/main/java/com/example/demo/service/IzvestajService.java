package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.mapper.IzvestajMapper;
import com.example.demo.repository.rdf.IzvestajRDF;
import com.example.demo.repository.xml.IzvestajExist;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.SOAPDocument;

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

	@Override
	public void add(String godina) {
		Document document = this.izvestajMapper.map(godina);
		
		try {
			if (this.izvestajExist.retrieve("/izvestaj:Izvestaj[izvestaj:godina = " + godina + "]").getSize() == 0) {
				this.izvestajExist.add(document);
				this.soapService.sendSOAPMessage(document, SOAPDocument.izvestaj);
				//this.izvestajRDF.add(this.izvestajMapper.map(document));
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(String documentId, Document document) {
		this.izvestajExist.update(documentId, document);
	}

	@Override
	public String retrieve() {
		ResourceSet resources = this.izvestajExist.retrieve("/izvestaj:Izvestaj");
		return this.izvestajMapper.map(resources);
	}

	@Override
	public Document load(String documentId) {
		return this.izvestajExist.load(documentId);
	}

}
