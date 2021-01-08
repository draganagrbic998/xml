package com.example.demo.service.resenje;

import java.text.SimpleDateFormat;
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

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.ws.utils.SOAPService;

@Component
public class ResenjeMapper {
		
	@Autowired
	private ZalbaExist zalbaExist;
		
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private DOMParser domParser;

	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private OdgovorExist odgovorExist;

	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	@Autowired
	private SOAPService soapService;

	public String map(ResourceSet resouces) {
		try {
			Document resenjaDocument = this.domParser.emptyDocument();
			Node resenja = resenjaDocument.createElementNS(Namespaces.RESENJE, "Resenja");
			resenjaDocument.appendChild(resenja);
			ResourceIterator it = resouces.getIterator();
			
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
	
	public Document map(String xml) {
		try {
			Document document = this.domParser.buildDocument(xml);
			Element resenje = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "Resenje").item(0);
			String zalbaBroj = resenje.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
			resenje.removeChild(document.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0));

			Element zalba = (Element) this.zalbaExist.load(zalbaBroj).getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
			DocumentFragment documentFragment = document.createDocumentFragment();
			
			Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
			datum.setTextContent(sdf.format(new Date()));
			documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
			documentFragment.appendChild(datum);
			resenje.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0));
			Document odgovor = this.odgovorExist.load(zalbaBroj);
			if (odgovor != null) {
				resenje.appendChild(document.importNode(odgovor.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0), true));
			}
						
			resenje.appendChild(document.importNode(this.jaxbParser.marshal(this.korisnikService.currentUser().getOsoba()).getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
			Node organVlasti = document.createElementNS(Namespaces.RESENJE, "organVlasti");
			organVlasti.setTextContent(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "organVlasti").item(0).getTextContent());
			resenje.appendChild(organVlasti);
			
			Node podaciZahteva = document.createElementNS(Namespaces.RESENJE, "resenje:PodaciZahteva");
			podaciZahteva.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0), true));
			Node brojZahteva = document.createElementNS(Namespaces.RESENJE, "resenje:brojZahteva");
			brojZahteva.setTextContent(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "brojZahteva").item(0).getTextContent());
			podaciZahteva.appendChild(brojZahteva);
			Node datumZahteva = document.createElementNS(Namespaces.RESENJE, "resenje:datumZahteva");
			datumZahteva.setTextContent(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "datumZahteva").item(0).getTextContent());
			podaciZahteva.appendChild(datumZahteva);
			resenje.appendChild(podaciZahteva);
			//dodaj detalje zahteva
			
			Node podaciZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:PodaciZalbe");
			Node brojZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:brojZalbe");
			brojZalbe.setTextContent(zalbaBroj);
			podaciZalbe.appendChild(brojZalbe);
			Node datumZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:datumZalbe");
			datumZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
			podaciZalbe.appendChild(datumZalbe);
			Node datumProsledjivanja = document.createElementNS(Namespaces.RESENJE, "resenje:datumProsledjivanja");
			datumProsledjivanja.setTextContent(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").item(0).getTextContent());
			podaciZalbe.appendChild(datumProsledjivanja);
			resenje.appendChild(podaciZalbe);
			
			if (zalba.getElementsByTagNameNS(Namespaces.ZALBA, "brojOdluke").getLength() > 0) {
				Node podaciOdluke = document.createElementNS(Namespaces.RESENJE, "resenje:PodaciOdluke");
				Node tipOdluke = document.createElementNS(Namespaces.RESENJE, "resenje:tipOdluke");
				tipOdluke.setTextContent(this.tipOdluke(((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0)).getAttributeNS(Namespaces.XSI, "type")));
				podaciOdluke.appendChild(tipOdluke);
				Node brojOdluke = document.createElementNS(Namespaces.RESENJE, "resenje:brojOdluke");
				brojOdluke.setTextContent(document.getElementsByTagNameNS(Namespaces.ZALBA, "brojOdluke").item(0).getTextContent());
				podaciOdluke.appendChild(brojOdluke);
				Node datumOdluke = document.createElementNS(Namespaces.RESENJE, "resenje:datumOdluke");
				resenje.setTextContent(document.getElementsByTagNameNS(Namespaces.ZALBA, "datumOdluke").item(0).getTextContent());
				podaciOdluke.appendChild(datumOdluke);
				//dodaj da dobavi detalje odluke;
				resenje.appendChild(podaciOdluke);
			}
			
			return document;
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public String map(Document document) {
		try {
			return this.domParser.buildXml(document);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	private String tipOdluke(String tip) {
		if (tip.contains("TObavestenje")) {
			return "obavestenje";
		}
		return "odbijanje";
	}
	
}
