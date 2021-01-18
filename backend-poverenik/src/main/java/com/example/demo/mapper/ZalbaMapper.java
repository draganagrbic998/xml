package com.example.demo.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.enums.TipZalbe;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.SOAPActions;

@Component
public class ZalbaMapper implements MapperInterface {
	
	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private ZalbaRDF zalbaRDF;

	@Autowired
	private KorisnikExist korisnikExist;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private SOAPService soapService;

	@Autowired
	private DOMParser domParser;
				
	private static final String STUB_FILE = Constants.STUB_FOLDER + "zalba.xml";
	
	@Override
	public Document map(String xml) {
		Document dto = this.domParser.buildDocument(xml);
		String broj = this.zalbaExist.nextDocumentId();
		Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		
		NodeList tipCutanja = dto.getElementsByTagNameNS(Namespaces.ZALBA, "tipCutanja");
		if (tipCutanja.getLength() > 0) {
			zalba.setAttribute("xsi:type", "zalba:TZalbaCutanje");
			zalba.getElementsByTagNameNS(Namespaces.ZALBA, "tipCutanja").item(0).setTextContent(tipCutanja.item(0).getTextContent());
		}
		else {
			zalba.setAttribute("xsi:type", "zalba:TZalbaOdluka");		
			zalba.removeChild(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "tipCutanja").item(0));
		}
		
		zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(broj);
		zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(Constants.sdf.format(new Date()));
		zalba.setAttribute("about", Namespaces.ZALBA + "/" + broj);
		zalba.setAttribute("href", Namespaces.KORISNIK + "/" + this.korisnikService.currentUser().getMejl());
				
		Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
		gradjanin.appendChild(document.importNode(this.korisnikExist.load(this.korisnikService.currentUser().getMejl()).getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		gradjanin.appendChild(document.importNode(this.korisnikExist.load(this.korisnikService.currentUser().getMejl()).getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
		documentFragment.appendChild(gradjanin);
		String brojZahteva;
		
		String lozinka = dto.getElementsByTagNameNS(Namespaces.OSNOVA, "lozinka").item(0).getTextContent();
		if (dto.getElementsByTagNameNS(Namespaces.ZALBA, "brojOdluke").getLength() > 0) {
			String brojOdluke = dto.getElementsByTagNameNS(Namespaces.ZALBA, "brojOdluke").item(0).getTextContent();
			Element odluka = (Element) this.domParser.buildDocument(this.soapService
					.sendSOAPMessage(this.domParser.buildDocument(
							String.format("<pretraga><broj>%s</broj><lozinka>%s</lozinka></pretraga>", brojOdluke, lozinka)),
							SOAPActions.get_odluka))
					.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
			Element podaciOdluke = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			podaciOdluke.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(brojOdluke);
			podaciOdluke.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
			podaciOdluke.setAttribute("href", odluka.getAttribute("about"));
			podaciOdluke.appendChild(document.importNode(odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
			brojZahteva = odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		}
		else {
			zalba.removeChild(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0));
			brojZahteva = dto.getElementsByTagNameNS(Namespaces.ZALBA, "brojZahteva").item(0).getTextContent();
		}
		
		Element zahtev = (Element) this.domParser.buildDocument(this.soapService
				.sendSOAPMessage(this.domParser.buildDocument(
						String.format("<pretraga><broj>%s</broj><lozinka>%s</lozinka></pretraga>", brojZahteva, lozinka)),
						SOAPActions.get_zahtev))
				.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		documentFragment.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		documentFragment.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		zalba.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0));
		
		Element podaciZahteva = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(brojZahteva);
		podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		podaciZahteva.setAttribute("href", zahtev.getAttribute("about"));
		podaciZahteva.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
				
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
				Document zalbaDocument = this.domParser.buildDocument(resource.getContent().toString());
				Node zalba = zalbeDocument.createElementNS(Namespaces.ZALBA, "Zalba");
				Node tipZalbe = zalbeDocument.createElementNS(Namespaces.ZALBA, "tipZalbe");
				tipZalbe.setTextContent(getTipZalbe(zalbaDocument) + "");
				
				zalba.appendChild(tipZalbe);
				zalba.appendChild(zalbeDocument.importNode(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				zalba.appendChild(zalbeDocument.importNode(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				zalba.appendChild(zalbeDocument.importNode(zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0), true));
				if (zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").getLength() > 0)
					zalba.appendChild(zalbeDocument.importNode(zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").item(0), true));
				
				Node reference = zalbeDocument.createElementNS(Namespaces.OSNOVA, "Reference");
				Utils.setReferences(zalbeDocument, reference, this.zalbaRDF.odgovori(Utils.getBroj(zalbaDocument)), "odgovori");
				Utils.setReferences(zalbeDocument, reference, this.zalbaRDF.resenja(Utils.getBroj(zalbaDocument)), "resenja");
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
