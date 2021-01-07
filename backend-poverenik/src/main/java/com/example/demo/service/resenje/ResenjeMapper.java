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
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;

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

	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

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
			String brojZalbe = resenje.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
			Element zalba = (Element) this.zalbaExist.load(brojZalbe).getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
			DocumentFragment documentFragment = document.createDocumentFragment();
			
			Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
			datum.setTextContent(sdf.format(new Date()));
			documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
			documentFragment.appendChild(datum);
			resenje.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0));
			resenje.appendChild(document.importNode(this.jaxbParser.marshal(this.korisnikService.currentUser().getOsoba()).getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
			resenje.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
			
			Node podaciZahteva = document.createElementNS(Namespaces.RESENJE, "resenje:PodaciZahteva");
			podaciZahteva.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0), true));
			Node datumZahteva = document.createElementNS(Namespaces.RESENJE, "resenje:datumZahteva");
			datumZahteva.setTextContent(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "datumZahteva").item(0).getTextContent());
			podaciZahteva.appendChild(datumZahteva);
			Node datumZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:datumZalbe");
			datumZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
			podaciZahteva.appendChild(datumZalbe);
			podaciZahteva.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
			resenje.appendChild(podaciZahteva);
			//dodaj logiku za preuzimanje podataka obavestenje/odluke...
			return document;
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
