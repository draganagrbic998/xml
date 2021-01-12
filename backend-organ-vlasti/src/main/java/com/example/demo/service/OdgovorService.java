package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.mapper.OdgovorMapper;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.SOAPDocument;

@Service
public class OdgovorService implements ServiceInterface {

	@Autowired
	private OdgovorExist odgovorExist;
	
	@Autowired
	private OdgovorRDF odgovorRDF;

	@Autowired
	private OdgovorMapper odgovorMapper;

	@Autowired
	private ZalbaService zalbaService;
	
	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Override
	public void add(String xml) {
		Document document = this.odgovorMapper.map(xml);
		String brojZalbe = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		this.odgovorExist.update(brojZalbe, document);
		this.odgovorRDF.add(this.xslTransformer.generateMetadata(document));
		Document zalbaDocument = this.zalbaService.load(brojZalbe);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odgovoreno + "");
		this.zalbaService.update(brojZalbe, zalbaDocument);
		this.soapService.sendSOAPMessage(document, SOAPDocument.odgovor);	
	}

	@Override
	public void update(String documentId, Document document) {
		this.odgovorExist.update(documentId, document);
	}

	@Override
	public String retrieve() {
		ResourceSet resources = this.odgovorExist.retrieve("/odgovor:Odgovor");
		return this.odgovorMapper.map(resources);
	}

	@Override
	public Document load(String documentId) {
		return this.odgovorExist.load(documentId);
	}

}
