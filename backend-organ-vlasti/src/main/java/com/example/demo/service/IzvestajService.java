package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.example.demo.exception.MyException;
import com.example.demo.exception.ResourceTakenException;
import com.example.demo.mapper.IzvestajMapper;
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
	
	@Override
	public void add(String godina) {
		try {
			if (this.izvestajExist.retrieve("/izvestaj:Izvestaj[izvestaj:godina = " + godina + "]").getSize() > 0) {
				throw new ResourceTakenException();
			}
		}
		catch(Exception e) {
			throw new MyException(e);
		}
		Document document = this.izvestajMapper.map(godina);
		this.izvestajExist.add(document);
		this.izvestajRDF.add(document);
		this.soapService.sendSOAPMessage(document, SOAPActions.create_izvestaj);
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
	public Document load(String documentId) {
		return this.izvestajExist.load(documentId);
	}

	@Override
	public String retrieve() {
		return this.izvestajMapper.map(this.izvestajExist.retrieve("/izvestaj:Izvestaj"));
	}

	@Override
	public String regularSearch(String xml) {
		return null;
	}

	@Override
	public String advancedSearch(String xml) {
		return null;
	}

}
