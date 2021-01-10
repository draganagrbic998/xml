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
public class IzvestajMapper implements MapperInterface {

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
			Document izvestajiDocument = this.domParser.emptyDocument();
			Node izvestaji = izvestajiDocument.createElementNS(Namespaces.IZVESTAJ, "Izvestaji");
			izvestajiDocument.appendChild(izvestaji);
			ResourceIterator it = resources.getIterator();

			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node izvestaj = izvestajiDocument.createElementNS(Namespaces.IZVESTAJ, "Izvestaj");
				izvestaj.appendChild(izvestajiDocument
						.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				izvestaj.appendChild(izvestajiDocument
						.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				izvestaj.appendChild(izvestajiDocument
						.importNode(document.getElementsByTagNameNS(Namespaces.IZVESTAJ, "godina").item(0), true));
				izvestaji.appendChild(izvestaj);
			}

			return this.domParser.buildXml(izvestajiDocument);
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	@Override
	public Model map(Document document) {
		Element izvestaj = (Element) document.getElementsByTagNameNS(Namespaces.IZVESTAJ, "Izvestaj").item(0);
		izvestaj.setAttribute("xmlns:xs", Namespaces.XS);
		izvestaj.setAttribute("xmlns:pred", Prefixes.PREDIKAT);
		izvestaj.setAttribute("about", Prefixes.IZVESTAJ_PREFIX
				+ izvestaj.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		izvestaj.setAttribute("rel", "pred:podneo");

		izvestaj.setAttribute("href", Prefixes.KORISNIK_PREFIX
				+ izvestaj.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent());

		((Element) izvestaj.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("property",
				"pred:datum");
		((Element) izvestaj.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("datatype",
				"xs:string");

		((Element) izvestaj.getElementsByTagNameNS(Namespaces.IZVESTAJ, "godina").item(0)).setAttribute("property",
				"pred:godina");
		((Element) izvestaj.getElementsByTagNameNS(Namespaces.IZVESTAJ, "godina").item(0)).setAttribute("datatype",
				"xs:string");

		String result = this.xslTransformer.generateMetadata(this.domParser.buildXml(document)).toString();
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", Prefixes.PREDIKAT);
		model.read(new StringReader(result), null);
		return model;
	}

}
