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
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;

@Component
public class ResenjeMapper implements MapperInterface {
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
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
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node resenje = resenjaDocument.createElementNS(Namespaces.RESENJE, "Resenje");
				resenje.appendChild(resenjaDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				resenje.appendChild(resenjaDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				resenje.appendChild(resenjaDocument.importNode(document.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0), true));
				resenja.appendChild(resenje);
			}
			
			return this.domParser.buildXml(resenjaDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

	@Override
	public Model map(Document document) {
		Element resenje = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "Resenje").item(0);
		resenje.setAttribute("xmlns:xs", Namespaces.XS);
		resenje.setAttribute("xmlns:pred", Prefixes.PREDIKAT);
		resenje.setAttribute("about", Prefixes.RESENJE_PREFIX + resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		resenje.setAttribute("rel", "pred:izdao");
		resenje.setAttribute("href", Prefixes.KORISNIK_PREFIX + resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent());
		
		((Element) resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("property", "pred:datum");
		((Element) resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("datatype", "xs:string");
		((Element) resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("property", "pred:izdatoU");
		((Element) resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("datatype", "xs:string");
		((Element) resenje.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0)).setAttribute("property", "pred:tip");
		((Element) resenje.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0)).setAttribute("datatype", "xs:string");

		Element brojZahteva = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciZahteva").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
		brojZahteva.setAttribute("rel", "pred:zahtev");
		brojZahteva.setAttribute("href", Prefixes.ZAHTEV_PREFIX + brojZahteva.getTextContent());
		Element brojZalbe = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciZalbe").item(0)).getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0);
		brojZalbe.setAttribute("rel", "pred:zalba");
		brojZalbe.setAttribute("href", Prefixes.ZALBA_PREFIX + brojZalbe.getTextContent());
		
		if (resenje.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciOdluke").getLength() > 0) {
			Element brojOdluke = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciOdluke").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
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
	
}
