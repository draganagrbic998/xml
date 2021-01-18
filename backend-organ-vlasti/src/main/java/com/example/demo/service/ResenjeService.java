package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.mapper.ResenjeMapper;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.ResenjeRDF;
import com.example.demo.repository.rdf.ZalbaRDF;
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
	
	@Autowired
	private ZalbaRDF zalbaRDF;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Override
	public void add(String xml) {
		Document document = this.resenjeMapper.map(xml);
		String brojZalbe = document.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
		Document zalbaDocument = this.zalbaService.load(brojZalbe);

		this.resenjeExist.update(Utils.getBroj(document), document);
		this.resenjeRDF.add(document);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.reseno + "");
		this.zalbaService.update(brojZalbe, zalbaDocument);
		this.zalbaRDF.update(brojZalbe, zalbaDocument);
	}

	@Override
	public void update(String documentId, Document document) {
		this.resenjeExist.update(documentId, document);
		this.resenjeRDF.update(documentId, document);
	}
	
	@Override
	public void delete(String documentId) {
		this.resenjeExist.delete(documentId);
		this.resenjeRDF.delete(documentId);
	}

	@Override
	public Document load(String documentId) {
		return this.resenjeExist.load(documentId);
	}

	@Override
	public String retrieve() {
		return this.resenjeMapper.map(this.resenjeExist.retrieve("/resenje:Resenje"));		
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
