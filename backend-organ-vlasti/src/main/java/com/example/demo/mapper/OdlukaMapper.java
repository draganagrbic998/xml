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
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.TipOdluke;
import com.example.demo.exist.ExistManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.rdf.OdlukaRDF;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.repository.xml.ZahtevExist;

@Component
public class OdlukaMapper implements MapperInterface {

	@Autowired
	private ZahtevExist zahtevExist;

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private OdlukaRDF odlukaRDF;
	
	private static final String STUB_FILE = Constants.STUB_FOLDER + "odluka.xml";

	@Override
	public Document map(String xml) {
		String broj = this.existManager.nextDocumentId(OdlukaExist.ODLUKA_COLLECTION);
		Document dto = this.domParser.buildDocument(xml);
		Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
		Element odluka = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
		if (dto.getElementsByTagNameNS(Namespaces.ODLUKA, "Uvid").getLength() > 0) {
			odluka.setAttribute("xsi:type", "odluka:TObavestenje");			
		}
		else {
			odluka.setAttribute("xsi:type", "odluka:TOdbijanje");			
		}
		odluka.setAttribute("about", Namespaces.ODLUKA + "/" + broj);
		odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(broj);
		odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(Constants.sdf.format(new Date()));

		DocumentFragment documentFragment = document.createDocumentFragment();
		String brojZahteva = dto.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		Element zahtev = (Element) this.zahtevExist.load(brojZahteva).getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		Element gradjanin = (Element) zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Gradjanin").item(0);
		odluka.setAttribute("href", Namespaces.KORISNIK + "/" + gradjanin.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent());
		documentFragment.appendChild(document.importNode(gradjanin, true));
		//gradjanin.getElementsByTagNameNS(Namespaces.OSNOVA, "potpis").item(0).setTextContent(this.korisnikService.currentUser().getOsoba().getPotpis());
		documentFragment.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		documentFragment.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		odluka.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0));
		((Element) odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0)).setAttribute("href", Namespaces.ZAHTEV + "/" + brojZahteva);
		odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).setTextContent(brojZahteva);
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
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node broj = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
				Node odluka = odlukeDocument.createElementNS(Namespaces.ODLUKA, "Odluka");
				Node tipOdluke = odlukeDocument.createElementNS(Namespaces.ODLUKA, "tipOdluke");
				tipOdluke.setTextContent(getTipOdluke(document) + "");
				odluka.appendChild(tipOdluke);
				odluka.appendChild(odlukeDocument.importNode(broj, true));
				odluka.appendChild(odlukeDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				odluka.appendChild(odlukeDocument.importNode(document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0), true));
				
				Node reference = odlukeDocument.createElementNS(Namespaces.OSNOVA, "Reference");
				DOMParser.setReferences(odlukeDocument, reference, this.odlukaRDF.zalbe(broj.getTextContent()), "zalbe");
				DOMParser.setReferences(odlukeDocument, reference, this.odlukaRDF.resenja(broj.getTextContent()), "resenja");
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
