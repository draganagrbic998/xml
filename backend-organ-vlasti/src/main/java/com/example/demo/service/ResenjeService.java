package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
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
	
	@Autowired
	private ZalbaService zalbaService;

	@Override
	public void add(String xml) {
		Document document = this.resenjeMapper.map(xml);
		this.resenjeExist.update(this.resenjeMapper.getBroj(document), document);
		String brojZalbe = document.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
		Document zalbaDocument = this.zalbaService.load(brojZalbe);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.reseno + "");
		this.zalbaService.update(brojZalbe, zalbaDocument);
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
