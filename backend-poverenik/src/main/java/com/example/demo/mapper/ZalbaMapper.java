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
import com.example.demo.common.Prefixes;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.enums.TipZalbe;
import com.example.demo.exist.ExistManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.SOAPDocument;

@Component
public class ZalbaMapper implements MapperInterface {

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private KorisnikExist korisnikExist;
	
	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private ExistManager existManager;

	@Override
	public Document map(String xml) {
		Document document = this.domParser.buildDocument(xml);
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		
		Node broj = document.createElementNS(Namespaces.OSNOVA, "broj");
		broj.setTextContent(this.existManager.nextDocumentId(ZalbaExist.ZALBA_COLLECTION));
		Node datum = document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0);
		datum.setTextContent(Constants.sdf.format(new Date()));
		zalba.insertBefore(broj, datum);
		
		Element korisnik = (Element) this.korisnikExist.load(this.korisnikService.currentUser().getOsoba().getMejl()).getElementsByTagNameNS(Namespaces.OSNOVA, "Korisnik").item(0);
		Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
		documentFragment.appendChild(gradjanin);
		String brojZahteva;
		
		if (document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").getLength() > 0) {
			String brojOdluke = ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
			Document odlukaDocument = this.domParser.buildDocument(this.soapService.sendSOAPMessage(brojOdluke, null, SOAPDocument.odluka));
			Element odluka = (Element) odlukaDocument.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
			Node podaciOdluke = document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			podaciOdluke.appendChild(document.importNode(odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
			podaciOdluke.appendChild(document.importNode(odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
			brojZahteva = odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		}
		else {
			brojZahteva = ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();		
		}
		
		Document zahtevDocument = this.domParser.buildDocument(this.soapService.sendSOAPMessage(brojZahteva, null, SOAPDocument.zahtev));
		Element zahtev = (Element) zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		documentFragment.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		zalba.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		
		Node status = document.createElementNS(Namespaces.ZALBA, "zalba:status");
		status.setTextContent(StatusZalbe.cekanje + "");
		zalba.insertBefore(status, document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0));
		
		Element podaciZahteva = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(brojZahteva);
		((Element) podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0)).setAttribute("href", Prefixes.ZAHTEV_PREFIX + brojZahteva);
		podaciZahteva.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
		podaciZahteva.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		zalba.setAttribute("about", Prefixes.ZALBA_PREFIX + broj.getTextContent());
		return document;
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
				if (document.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").getLength() > 0)
					zalba.appendChild(zalbeDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").item(0), true));
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
	
}
