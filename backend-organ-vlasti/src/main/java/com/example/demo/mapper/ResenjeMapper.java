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
import com.example.demo.enums.StatusResenja;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;

@Component
public class ResenjeMapper implements MapperInterface {
	
	@Autowired
	private DOMParser domParser;
		
	@Override
	public Document map(String xml) {
		return this.domParser.buildDocument(xml);
	}

	@Override
	public String map(ResourceSet resources) {
		try {
			Document resenjaDocument = this.domParser.emptyDocument();
			Node resenja = resenjaDocument.createElementNS(Namespaces.RESENJE, "Resenja");
			resenjaDocument.appendChild(resenja);
			ResourceIterator it = resources.getIterator();
			
			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document resenjeDocument = this.domParser.buildDocument(resource.getContent().toString());
				Node resenje = resenjaDocument.createElementNS(Namespaces.RESENJE, "Resenje");
				
				Node broj = resenjaDocument.createElementNS(Namespaces.OSNOVA, "broj");
				broj.setTextContent(Utils.getBroj(resenjeDocument));
				resenje.appendChild(broj);
				resenje.appendChild(resenjaDocument.importNode(resenjeDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				resenje.appendChild(resenjaDocument.importNode(resenjeDocument.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0), true));
				resenja.appendChild(resenje);
			}
			
			return this.domParser.buildXml(resenjaDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public static StatusResenja getStatusResenja(Document document) {
		try {
			return StatusResenja.valueOf(document.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0).getTextContent());
		}
		catch(Exception e) {
			return StatusResenja.ponisteno;
		}
	}
		
}
