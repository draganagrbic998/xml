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
import com.example.demo.service.OdlukaService;
import com.example.demo.service.ZahtevService;

@Component
public class OdlukaMapper implements MapperInterface {

	public static final String STUB_FILE = Constants.STUB_FOLDER + "odluka.xml";

	@Autowired
	private DOMParser domParser;

	@Autowired
	private OdlukaService odlukaService;
	
	@Autowired
	private ZahtevService zahtevService;
		
	@Override
	public Document map(String xml) {
		Document dto = this.domParser.buildDocument(xml);
		Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
		Element odluka = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
		String brojZahteva = dto.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		Element zahtev = (Element) this.zahtevService.find(brojZahteva).getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();

		if (dto.getElementsByTagNameNS(Namespaces.ODLUKA, "Uvid").getLength() > 0) {
			odluka.setAttribute("xsi:type", "odluka:TObavestenje");			
		}
		else {
			odluka.setAttribute("xsi:type", "odluka:TOdbijanje");			
		}

		odluka.setAttribute("about", Namespaces.ODLUKA + "/" + this.odlukaService.nextDocumentId());
		odluka.setAttribute("href", zahtev.getAttribute("href"));
		odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(Constants.sdf.format(new Date()));
		
		documentFragment.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Gradjanin").item(0), true));
		documentFragment.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		documentFragment.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		
		Element datumZahteva = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0);
		datumZahteva.setAttribute("href", zahtev.getAttribute("about"));
		datumZahteva.setTextContent(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		odluka.insertBefore(documentFragment, datumZahteva);
		
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
				Node broj = odlukeDocument.createElementNS(Namespaces.OSNOVA, "broj");
				broj.setTextContent(Utils.getBroj(odlukaDocument));
				odluka.appendChild(broj);
				odluka.appendChild(odlukeDocument.importNode(odlukaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				odluka.appendChild(odlukeDocument.importNode(odlukaDocument.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0), true));
				
				Node reference = odlukeDocument.createElementNS(Namespaces.OSNOVA, "Reference");
				Utils.setReferences(odlukeDocument, reference, this.odlukaService.zalbe(Utils.getBroj(odlukaDocument)), "zalbe");
				Utils.setReferences(odlukeDocument, reference, this.odlukaService.resenja(Utils.getBroj(odlukaDocument)), "resenja");
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
