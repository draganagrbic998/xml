package com.example.demo.service.zahtev;

import java.io.StringReader;
import java.util.Date;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Prefixes;
import com.example.demo.enums.StatusZahteva;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.OrganVlastiService;
import com.ibm.icu.text.SimpleDateFormat;

@Component
public class ZahtevMapper {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private OrganVlastiService organVlastiService;

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private XSLTransformer xslTransformer;

	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	public Document map(String xml) {
		Document document = this.domParser.buildDocument(xml);
		Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		Element korisnik = (Element) this.jaxbParser.marshal(this.korisnikService.currentUser()).getElementsByTagNameNS(Namespaces.OSNOVA, "Korisnik").item(0);
		Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
		Node organVlasti = document.importNode(this.jaxbParser.marshal(this.organVlastiService.load()).getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(organVlasti);
		zahtev.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		Node status = document.createElementNS(Namespaces.ZAHTEV, "zahtev:status");
		status.setTextContent(StatusZahteva.cekanje + "");
		zahtev.appendChild(status);
		return document;
	}
	
	public String map(ResourceSet resources) {
		try {
			Document zahteviDocument = this.domParser.emptyDocument();
			Node zahtevi = zahteviDocument.createElementNS(Namespaces.ZAHTEV, "Zahtevi");
			zahteviDocument.appendChild(zahtevi);
			ResourceIterator it = resources.getIterator();
			
			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node zahtev = zahteviDocument.createElementNS(Namespaces.ZAHTEV, "Zahtev");
				zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZAHTEV, "tipZahteva").item(0), true));
				zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				zahtev.appendChild(zahteviDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0), true));
				zahtevi.appendChild(zahtev);			
			}
			return this.domParser.buildXml(zahteviDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public Model map(Document document) {
		Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		zahtev.setAttribute("xmlns:xs", Namespaces.XS);
		zahtev.setAttribute("xmlns:pred", Prefixes.PREDIKAT);
		zahtev.setAttribute("about", Prefixes.ZAHTEV_PREFIX + zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		
		zahtev.setAttribute("rel", "pred:podneo");
		zahtev.setAttribute("href", Prefixes.KORISNIK_PREFIX + this.korisnikService.currentUser().getOsoba().getMejl());
		
		((Element) zahtev.getElementsByTagNameNS(Namespaces.ZAHTEV, "tipZahteva").item(0)).setAttribute("property", "pred:tip");
		((Element) zahtev.getElementsByTagNameNS(Namespaces.ZAHTEV, "tipZahteva").item(0)).setAttribute("datatype", "xs:string");

		((Element) zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("property", "pred:datum");
		((Element) zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("datatype", "xs:string");
		
		((Element) zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("property", "pred:mesto");
		((Element) zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("datatype", "xs:string");

		((Element) zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(1)).setAttribute("property", "pred:izdatoU");
		((Element) zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(1)).setAttribute("datatype", "xs:string");

		String result = this.xslTransformer.generateMetadata(this.domParser.buildXml(document)).toString();
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", Prefixes.PREDIKAT);
		model.read(new StringReader(result), null);
		return model;
	}
	
}
