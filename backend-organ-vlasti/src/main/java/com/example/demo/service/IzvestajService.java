package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.MyException;
import com.example.demo.mapper.IzvestajMapper;
import com.example.demo.parser.XSLTransformer;
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
	
	@Autowired
	private XSLTransformer xslTransformer;

	@Override
	public void add(String godina) {
		try {
			Document document = this.izvestajMapper.map(godina);
			if (this.izvestajExist.retrieve("/izvestaj:Izvestaj[izvestaj:godina = " + godina + "]").getSize() == 0) {
				this.izvestajExist.add(document);
				this.izvestajRDF.add(this.xslTransformer.generateMetadata(document));
				this.soapService.sendSOAPMessage(document, SOAPDocument.izvestaj);
			}
		}
		catch(Exception e) {
			throw new MyException(e);
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
