package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.mapper.ResenjeMapper;
import com.example.demo.repository.rdf.ResenjeRDF;
import com.example.demo.repository.xml.ResenjeExist;

@Service
public class ResenjeService implements ServiceInterface {
	
	@Autowired
	private ResenjeExist resenjeExist;
	
	@Autowired
	private ResenjeRDF resenjeRDF;

	@Autowired
	private ResenjeMapper resenjeMapper;

	@Override
	public void add(String xml) {
		Document document = this.resenjeMapper.map(xml);
		this.resenjeExist.update(this.resenjeMapper.getBroj(document), document);
		this.resenjeRDF.add(this.resenjeMapper.map(document));				
	}

	@Override
	public void update(String documentId, Document document) {
		this.resenjeExist.update(documentId, document);
	}

	@Override
	public String retrieve() {
		ResourceSet resouces = this.resenjeExist.retrieve("/resenje:Resenje");
		return this.resenjeMapper.map(resouces);		
	}

	@Override
	public Document load(String documentId) {
		return this.resenjeExist.load(documentId);
	}
	
}