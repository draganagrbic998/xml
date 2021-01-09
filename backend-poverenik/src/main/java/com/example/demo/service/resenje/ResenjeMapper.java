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

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.zalba.ZalbaMapper;

@Component
public class ResenjeMapper {
		
	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private OdgovorExist odgovorExist;
		
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
		Document document = this.domParser.buildDocument(xml);
		Element resenje = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "Resenje").item(0);
		String zalbaBroj = resenje.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
		resenje.removeChild(document.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0));
		Document zalbaDocument = this.zalbaExist.load(zalbaBroj);
		Element zalba = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
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
		resenje.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		
		Node podaciZahteva = zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		podaciZahteva.setPrefix("resenje");
		resenje.appendChild(document.importNode(podaciZahteva, true));
		
		Node podaciZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:PodaciZalbe");
		Node tipZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:tipZalbe");
		tipZalbe.setTextContent(ZalbaMapper.getTipZalbe(zalbaDocument) + "");
		podaciZalbe.appendChild(tipZalbe);
		Node brojZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:brojZalbe");
		brojZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		podaciZalbe.appendChild(brojZalbe);
		Node datumZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:datumZalbe");
		datumZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		podaciZalbe.appendChild(datumZalbe);
		Node datumProsledjivanja = document.createElementNS(Namespaces.RESENJE, "resenje:datumProsledjivanja");
		datumProsledjivanja.setTextContent(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").item(0).getTextContent());
		podaciZalbe.appendChild(datumProsledjivanja);
		resenje.appendChild(podaciZalbe);
		
		if (zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").getLength() > 0) {
			Node podaciOdluke = zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			podaciOdluke.setPrefix("resenje");
			resenje.appendChild(document.importNode(podaciOdluke, true));
		}
		
		return document;
	}
	
	public String map(Document document) {
		return this.domParser.buildXml(document);
	}
	
}
