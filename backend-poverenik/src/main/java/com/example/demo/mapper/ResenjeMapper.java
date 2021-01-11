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
import com.example.demo.exist.ExistManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.repository.xml.ResenjeExist;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;

@Component
public class ResenjeMapper implements MapperInterface {

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private OdgovorExist odgovorExist;
	
	@Autowired
	private DOMParser domParser;

	@Autowired
	private KorisnikExist korisnikExist;

	@Autowired
	private ExistManager existManager;
	
	@Override
	public Document map(String xml) {
		Document document = this.domParser.buildDocument(xml);
		Element resenje = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "Resenje").item(0);
		String zalbaBroj = resenje.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
		Document zalbaDocument = this.zalbaExist.load(zalbaBroj);
		
		Node broj = document.createElementNS(Namespaces.OSNOVA, "broj");
		broj.setTextContent(this.existManager.nextDocumentId(ResenjeExist.RESENJE_COLLECTION));
		Node datum = document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0);
		datum.setTextContent(Constants.sdf.format(new Date()));
		resenje.insertBefore(broj, datum);
		
		Document odgovor = this.odgovorExist.load(zalbaBroj);
		if (odgovor != null) {
			Element resenjeOdgovor = document.createElementNS(Namespaces.RESENJE, "resenje:Odbrana");
			Element datumOdbrane = document.createElementNS(Namespaces.RESENJE, "resenje:datumOdbrane");
			datumOdbrane.setTextContent(odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
			resenjeOdgovor.appendChild(datumOdbrane);
			resenjeOdgovor.appendChild(document.importNode(odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
			resenje.insertBefore(resenjeOdgovor, document.getElementsByTagNameNS(Namespaces.RESENJE, "Odluka").item(0));
		}
					
		DocumentFragment documentFragment = document.createDocumentFragment();
		Element osoba = (Element) this.korisnikExist.load(this.korisnikService.currentUser().getOsoba().getMejl()).getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0);
		osoba.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).setTextContent(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent());
		documentFragment.appendChild(document.importNode(osoba, true));
		documentFragment.appendChild(document.importNode(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		Element podaciZahteva = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciZahteva").item(0);
		resenje.insertBefore(documentFragment, podaciZahteva);
		
		Element podaciZahtevaZalba = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		podaciZahteva.appendChild(document.importNode(podaciZahtevaZalba.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
		podaciZahteva.appendChild(document.importNode(podaciZahtevaZalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
		podaciZahteva.appendChild(document.importNode(podaciZahtevaZalba.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		podaciZahteva.setAttribute("href", Prefixes.ZAHTEV_PREFIX + podaciZahtevaZalba.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		
		Element podaciZalbe = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciZalbe").item(0);
		Node tipZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:tipZalbe");
		tipZalbe.setTextContent(ZalbaMapper.getTipZalbe(zalbaDocument) + "");
		podaciZalbe.insertBefore(tipZalbe, podaciZalbe.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0));
		Node datumZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:datumZalbe");
		datumZalbe.setTextContent(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		podaciZalbe.appendChild(datumZalbe);
		Node datumProsledjivanja = document.createElementNS(Namespaces.RESENJE, "resenje:datumProsledjivanja");
		datumProsledjivanja.setTextContent(zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").item(0).getTextContent());
		podaciZalbe.appendChild(datumProsledjivanja);
		
		if (zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").getLength() > 0) {
			Element podaciOdlukeZalba = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			Element podaciOdluke = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciOdluke").item(0);
			podaciOdluke.appendChild(document.importNode(podaciOdlukeZalba.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
			podaciOdluke.appendChild(document.importNode(podaciOdlukeZalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
			podaciOdluke.appendChild(document.importNode(podaciOdlukeZalba.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
			podaciOdluke.setAttribute("href", Prefixes.ODLUKA_PREFIX + podaciOdlukeZalba.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		}
		else {
			resenje.removeChild(resenje.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciOdluke").item(0));
		}
		
		resenje.setAttribute("about", Prefixes.RESENJE_PREFIX + broj.getTextContent());		
		return document;
	}

	@Override
	public String map(ResourceSet resources) {
		try {
			Document resenjaDocument = this.domParser.emptyDocument();
			Node resenja = resenjaDocument.createElementNS(Namespaces.RESENJE, "Resenja");
			resenjaDocument.appendChild(resenja);
			ResourceIterator it = resources.getIterator();
			
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
	
}
