package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.mapper.IzvestajMapper;
import com.example.demo.repository.xml.IzvestajExist;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.SOAPDocument;

@Service
public class IzvestajService implements ServiceInterface {

	@Autowired
	private IzvestajExist izvestajExist;
	
	@Autowired
	private IzvestajMapper izvestajMapper;

	@Autowired
	private SOAPService soapService;

	@Override
	public void add(String xml) {
		Document document = this.izvestajMapper.map(xml);
		this.izvestajExist.add(document);
		this.soapService.sendSOAPMessage(document, SOAPDocument.izvestaj);		
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