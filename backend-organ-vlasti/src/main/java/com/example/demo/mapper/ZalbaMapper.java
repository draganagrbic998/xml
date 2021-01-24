package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.enums.TipZalbe;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.service.ZalbaService;

@Component
public class ZalbaMapper implements MapperInterface {

	@Autowired
	private DOMParser domParser;

	@Autowired
	private ZalbaService zalbaService;
		
	@Override
	public Document map(String xml) {
		Document document = this.domParser.buildDocument(xml);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.prosledjeno + "");
		return this.domParser.buildDocument(xml);
	}

	@Override
	public String map(ResourceSet resources) {
		try {
			Document zalbeDocument = this.domParser.emptyDocument();
			Node zalbe = zalbeDocument.createElementNS(Namespaces.ZALBA, "Zalbe");
			zalbeDocument.appendChild(zalbe);
			ResourceIterator it = resources.getIterator();

			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document zalbaDocument = this.domParser.buildDocument(resource.getContent().toString());
				Node zalba = zalbeDocument.createElementNS(Namespaces.ZALBA, "Zalba");
				Node tipZalbe = zalbeDocument.createElementNS(Namespaces.ZALBA, "tipZalbe");
				tipZalbe.setTextContent(getTipZalbe(zalbaDocument) + "");
				
				zalba.appendChild(tipZalbe);
				zalba.appendChild(zalbeDocument.importNode(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				zalba.appendChild(zalbeDocument.importNode(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				zalba.appendChild(zalbeDocument.importNode(zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0), true));
				
				Node reference = zalbeDocument.createElementNS(Namespaces.OSNOVA, "Reference");
				Utils.setReferences(zalbeDocument, reference, this.zalbaService.odgovori(Utils.getBroj(zalbaDocument)), "odgovori");
				Utils.setReferences(zalbeDocument, reference, this.zalbaService.resenja(Utils.getBroj(zalbaDocument)), "resenja");
				zalba.appendChild(reference);
				zalbe.appendChild(zalba);
			}

			return this.domParser.buildXml(zalbeDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public static TipZalbe getTipZalbe(Document document) {
		String tipZalbe = ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0)).getAttributeNS(Namespaces.XSI, "type");
		if (tipZalbe.contains("TZalbaCutanje")) {
			if (document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").getLength() == 0) {
				return TipZalbe.cutanje;
			}
			return TipZalbe.delimicnost;
		}
		return TipZalbe.odluka;
	}
	
	public static StatusZalbe getStatusZalbe(Document document) {
		return StatusZalbe.valueOf(document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).getTextContent());
	}
	
}
