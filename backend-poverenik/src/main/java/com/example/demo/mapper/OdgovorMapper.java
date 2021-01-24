package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.service.OdgovorService;

@Component
public class OdgovorMapper implements MapperInterface {

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private OdgovorService odgovorService;
	
	@Override
	public Document map(String xml) {
		return this.domParser.buildDocument(xml);
	}

	@Override
	public String map(ResourceSet resources) {
		try {
			Document odgovoriDocument = this.domParser.emptyDocument();
			Node odgovori = odgovoriDocument.createElementNS(Namespaces.ODGOVOR, "Odgovori");
			odgovoriDocument.appendChild(odgovori);
			ResourceIterator it = resources.getIterator();

			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document odgovorDocument = this.domParser.buildDocument(resource.getContent().toString());
				Node odgovor = odgovoriDocument.createElementNS(Namespaces.ODGOVOR, "Odgovor");
				odgovor.appendChild(odgovoriDocument.importNode(odgovorDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				odgovor.appendChild(odgovoriDocument.importNode(odgovorDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				odgovor.appendChild(odgovoriDocument.importNode(odgovorDocument.getElementsByTagNameNS(Namespaces.ODGOVOR, "datumZalbe").item(0), true));
				
				Node reference = odgovoriDocument.createElementNS(Namespaces.OSNOVA, "Reference");
				Utils.setReferences(odgovoriDocument, reference, this.odgovorService.resenja(Utils.getBroj(odgovorDocument)), "resenja");
				odgovor.appendChild(reference);
				odgovori.appendChild(odgovor);
			}

			return this.domParser.buildXml(odgovoriDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
