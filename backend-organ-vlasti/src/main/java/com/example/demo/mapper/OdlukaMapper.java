package com.example.demo.mapper;

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
import com.example.demo.enums.TipOdluke;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.service.KorisnikService;

@Component
public class OdlukaMapper implements MapperInterface {

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private ZahtevExist zahtevExist;

	@Autowired
	private DOMParser domParser;

	@Autowired
	private XSLTransformer xslTransformer;

	@Override
	public Document map(String xml) {
		Document document = this.domParser.buildDocument(xml);
		Element odluka = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
		String brojZahteva = odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		Element zahtev = (Element) this.zahtevExist.load(brojZahteva).getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();

		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(Constants.sdf.format(new Date()));
		Element gradjanin = (Element) document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Gradjanin").item(0), true);
		gradjanin.getElementsByTagNameNS(Namespaces.OSNOVA, "potpis").item(0).setTextContent(this.korisnikService.currentUser().getOsoba().getPotpis());
		Node organVlasti = document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(organVlasti);
		odluka.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));

		Node datumZahteva = document.createElementNS(Namespaces.ODLUKA, "odluka:datumZahteva");
		datumZahteva.setTextContent(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		odluka.insertBefore(datumZahteva, odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0));

		try {
			String datumUvida = Constants.sdf.format(Constants.sdf2.parse(
					document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumUvida")
					.item(0).getTextContent()));
			document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumUvida").item(0).setTextContent(datumUvida);
		} 
		catch (Exception e) {
			;
		}
		
		return document;
	}

	@Override
	public String map(ResourceSet resources) {
		try {
			Document odlukeDocument = this.domParser.emptyDocument();
			Node odluke = odlukeDocument.createElementNS(Namespaces.ODLUKA, "Odluke");
			odlukeDocument.appendChild(odluke);
			ResourceIterator it = resources.getIterator();

			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node odluka = odlukeDocument.createElementNS(Namespaces.ODLUKA, "Odluka");
				Node tipOdluke = odlukeDocument.createElementNS(Namespaces.ODLUKA, "tipOdluke");
				tipOdluke.setTextContent(getTipOdluke(document) + "");
				odluka.appendChild(tipOdluke);
				odluka.appendChild(odlukeDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				odluka.appendChild(odlukeDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				odluka.appendChild(odlukeDocument.importNode(document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0), true));
				odluke.appendChild(odluka);
			}

			return this.domParser.buildXml(odlukeDocument);
		} 
		catch (Exception e) {
			throw new MyException(e);
		}
	}

	@Override
	public Model map(Document document) {
		Element odluka = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
		odluka.setAttribute("xmlns:xs", Namespaces.XS);
		odluka.setAttribute("xmlns:pred", Prefixes.PREDIKAT);
		odluka.setAttribute("about", Prefixes.ODLUKA_PREFIX + odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		odluka.setAttribute("rel", "pred:izdao");
		odluka.setAttribute("href", Prefixes.KORISNIK_PREFIX + this.korisnikService.currentUser().getOsoba().getMejl());
		
		Element brojZahteva = (Element) odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0);
		brojZahteva.setAttribute("rel", "pred:zahtev");
		brojZahteva.setAttribute("href", Prefixes.ZAHTEV_PREFIX + brojZahteva.getTextContent());
		Node tipOdluke = document.createElementNS(Namespaces.ODLUKA, "tipOdluke");
		tipOdluke.setTextContent(getTipOdluke(document) + "");
		odluka.appendChild(tipOdluke);
		
		((Element) odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "tipOdluke").item(0)).setAttribute("property", "pred:tip");
		((Element) odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "tipOdluke").item(0)).setAttribute("datatype", "xs:string");
		((Element) odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("property", "pred:datum");
		((Element) odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("datatype", "xs:string");

		((Element) odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("property", "pred:mesto");
		((Element) odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("datatype", "xs:string");
		((Element) odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(1)).setAttribute("property", "pred:izdatoU");
		((Element) odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(1)).setAttribute("datatype", "xs:string");

		String result = this.xslTransformer.generateMetadata(this.domParser.buildXml(document)).toString();
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", Prefixes.PREDIKAT);
		model.read(new StringReader(result), null);
		return model;
	}
	
	public static TipOdluke getTipOdluke(Document document) {
		String tipOdluke = ((Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka")
				.item(0)).getAttributeNS(Namespaces.XSI, "type");
		if (tipOdluke.contains("TObavestenje")) {
			return TipOdluke.obavestenje;
		}
		return TipOdluke.odbijanje;
	}

}
