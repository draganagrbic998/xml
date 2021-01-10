package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.mapper.ZalbaMapper;
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

	@Override
	public void add(String xml) {
		Document document = this.zalbaMapper.map(xml);
		this.zalbaExist.update(this.zalbaMapper.getBroj(document), document);
		this.zalbaRDF.add(this.zalbaMapper.map(document));		
	}

	@Override
	public void update(String documentId, Document document) {
		this.zalbaExist.update(documentId, document);
	}

	@Override
	public String retrieve() {
		ResourceSet resources = this.zalbaExist.retrieve("/zalba:Zalba");
		return this.zalbaMapper.map(resources);
	}

	@Override
	public Document load(String documentId) {
		return this.zalbaExist.load(documentId);
	}
	
}
