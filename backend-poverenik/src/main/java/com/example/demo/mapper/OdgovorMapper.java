package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.parser.DOMParser;

@Component
public class OdgovorMapper implements MapperInterface {

	@Autowired
	private DOMParser domParser;
	
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
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node odgovor = odgovoriDocument.createElementNS(Namespaces.ODGOVOR, "Odgovor");
				odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.ODGOVOR, "datumZalbe").item(0), true));
				odgovori.appendChild(odgovor);
			}

			return this.domParser.buildXml(odgovoriDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public String getBroj(Document document) {
		return document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
	}
	
}
