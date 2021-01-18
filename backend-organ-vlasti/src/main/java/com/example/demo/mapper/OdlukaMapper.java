package com.example.demo.mapper;

import java.util.Date;

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
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.TipOdluke;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.rdf.OdlukaRDF;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.repository.xml.ZahtevExist;

@Component
public class OdlukaMapper implements MapperInterface {

	@Autowired
	private OdlukaExist odlukaExist;
	
	@Autowired
	private OdlukaRDF odlukaRDF;

	@Autowired
	private ZahtevExist zahtevExist;

	@Autowired
	private DOMParser domParser;
		
	private static final String STUB_FILE = Constants.STUB_FOLDER + "odluka.xml";

	@Override
	public Document map(String xml) {
		Document dto = this.domParser.buildDocument(xml);
		String broj = this.odlukaExist.nextDocumentId();
		Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
		Element odluka = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
		String brojZahteva = dto.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		Element zahtev = (Element) this.zahtevExist.load(brojZahteva).getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();

		if (dto.getElementsByTagNameNS(Namespaces.ODLUKA, "Uvid").getLength() > 0) {
			odluka.setAttribute("xsi:type", "odluka:TObavestenje");			
		}
		else {
			odluka.setAttribute("xsi:type", "odluka:TOdbijanje");			
		}

		odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(broj);
		odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(Constants.sdf.format(new Date()));
		odluka.setAttribute("about", Namespaces.ODLUKA + "/" + broj);
		odluka.setAttribute("href", zahtev.getAttribute("href"));
		
		documentFragment.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Gradjanin").item(0), true));
		documentFragment.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		documentFragment.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		
		Element brojZahtevaNode = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0);
		brojZahtevaNode.setTextContent(brojZahteva);
		brojZahtevaNode.setAttribute("href", zahtev.getAttribute("about"));
		odluka.insertBefore(documentFragment, brojZahtevaNode);
		odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0).setTextContent(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		
		try {
			odluka.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.ODLUKA, "Uvid").item(0), true));
			odluka.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.ODLUKA, "kopija").item(0), true));
			String datumUvida = Constants.sdf.format(Constants.sdf2.parse(
					document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumUvida")
					.item(0).getTextContent()));
			document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumUvida").item(0).setTextContent(datumUvida);
		}
		catch(Exception e) {
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
				Document odlukaDocument = this.domParser.buildDocument(resource.getContent().toString());
				Node odluka = odlukeDocument.createElementNS(Namespaces.ODLUKA, "Odluka");
				Node tipOdluke = odlukeDocument.createElementNS(Namespaces.ODLUKA, "tipOdluke");
				tipOdluke.setTextContent(getTipOdluke(odlukaDocument) + "");
				
				odluka.appendChild(tipOdluke);
				odluka.appendChild(odlukeDocument.importNode(odlukaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				odluka.appendChild(odlukeDocument.importNode(odlukaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				odluka.appendChild(odlukeDocument.importNode(odlukaDocument.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0), true));
				
				Node reference = odlukeDocument.createElementNS(Namespaces.OSNOVA, "Reference");
				Utils.setReferences(odlukeDocument, reference, this.odlukaRDF.zalbe(Utils.getBroj(odlukaDocument)), "zalbe");
				Utils.setReferences(odlukeDocument, reference, this.odlukaRDF.resenja(Utils.getBroj(odlukaDocument)), "resenja");
				odluka.appendChild(reference);
				odluke.appendChild(odluka);
			}

			return this.domParser.buildXml(odlukeDocument);
		} 
		catch (Exception e) {
			throw new MyException(e);
		}
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
