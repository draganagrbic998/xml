package com.example.demo.mapper;

import java.io.StringReader;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Prefixes;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.enums.TipZalbe;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;

@Component
public class ZalbaMapper implements MapperInterface {

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private XSLTransformer xslTransformer;

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
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node zalba = zalbeDocument.createElementNS(Namespaces.ZALBA, "Zalba");
				Node tipZalbe = zalbeDocument.createElementNS(Namespaces.ZALBA, "tipZalbe");
				tipZalbe.setTextContent(getTipZalbe(document) + "");
				zalba.appendChild(tipZalbe);
				zalba.appendChild(zalbeDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				zalba.appendChild(zalbeDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				zalba.appendChild(zalbeDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0), true));
				zalbe.appendChild(zalba);
			}

			return this.domParser.buildXml(zalbeDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

	@Override
	public Model map(Document document) {
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		zalba.setAttribute("xmlns:xs", Namespaces.XS);
		zalba.setAttribute("xmlns:pred", Prefixes.PREDIKAT);
		zalba.setAttribute("about", Prefixes.ZALBA_PREFIX + zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		zalba.setAttribute("rel", "pred:podneo");
		zalba.setAttribute("href", Prefixes.KORISNIK_PREFIX + zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent());
		
		Node tipZalbe = document.createElementNS(Namespaces.ZALBA, "tipZalbe");
		tipZalbe.setTextContent(getTipZalbe(document) + "");
		zalba.appendChild(tipZalbe);
		
		((Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "tipZalbe").item(0)).setAttribute("property", "pred:tip");
		((Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "tipZalbe").item(0)).setAttribute("datatype", "xs:string");
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("property", "pred:datum");
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("datatype", "xs:string");
		
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("property", "pred:mesto");
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("datatype", "xs:string");
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(1)).setAttribute("property", "pred:izdatoU");
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(1)).setAttribute("datatype", "xs:string");
		
		Element brojZahteva = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
		brojZahteva.setAttribute("rel", "pred:zahtev");
		brojZahteva.setAttribute("href", Prefixes.ZAHTEV_PREFIX + brojZahteva.getTextContent());
		
		if (zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").getLength() > 0) {
			Element brojOdluke = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
			brojOdluke.setAttribute("rel", "pred:odluka");
			brojOdluke.setAttribute("href", Prefixes.ODLUKA_PREFIX + brojOdluke.getTextContent());			
		}
		
		String result = this.xslTransformer.generateMetadata(this.domParser.buildXml(document)).toString();
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", Prefixes.PREDIKAT);
		model.read(new StringReader(result), null);
		return model;
	}
	
	public String getBroj(Document document) {
		return document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
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
	
}
