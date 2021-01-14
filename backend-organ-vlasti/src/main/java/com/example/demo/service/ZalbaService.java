package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.mapper.ZalbaMapper;
import com.example.demo.parser.XSLTransformer;
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
	private XSLTransformer xslTransformer;

	@Override
	public void add(String xml) {
		Document document = this.zalbaMapper.map(xml);
		this.zalbaExist.update(this.zalbaMapper.getBroj(document), document);
		this.zalbaRDF.add(this.xslTransformer.generateMetadata(document));		
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
	
	public void odustani(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odustato + "");
		this.zalbaExist.update(broj, document);
		//slanje mejla
	}

	public void obustavi(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.obustavljeno + "");
		this.zalbaExist.update(broj, document);
		//slanje mejla
	}
	
}
