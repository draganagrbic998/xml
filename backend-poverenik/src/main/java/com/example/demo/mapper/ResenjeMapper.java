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
import com.example.demo.enums.StatusResenja;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.OdgovorService;
import com.example.demo.service.ResenjeService;
import com.example.demo.service.ZalbaService;

@Component
public class ResenjeMapper implements MapperInterface {
	
	public static final String STUB_FILE = Constants.STUB_FOLDER + "resenje.xml";

	@Autowired
	private DOMParser domParser;

	@Autowired
	private ResenjeService resenjeService;

	@Autowired
	private ZalbaService zalbaService;
	
	@Autowired
	private OdgovorService odgovorService;

	@Autowired
	private KorisnikService korisnikService;

	@Override
	public Document map(String xml) {
		Document dto = this.domParser.buildDocument(xml);
		String brojZalbe = dto.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
		Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
		Element resenje = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "Resenje").item(0);
		Document zalbaDocument = this.zalbaService.load(brojZalbe);
		Element zalba = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();

		resenje.setAttribute("about", Namespaces.RESENJE + "/" + this.resenjeService.nextDocumentId());
		resenje.setAttribute("href", zalba.getAttribute("href"));
		resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(Constants.sdf.format(new Date()));
		resenje.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0).setTextContent(
				dto.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0).getTextContent());
		
		Document odgovorDocument = this.odgovorService.load(brojZalbe);
		if (odgovorDocument != null) {
			Element odgovor = (Element) odgovorDocument.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0);
			Element datumOdbrane = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "datumOdbrane").item(0);
			datumOdbrane.setAttribute("href", odgovor.getAttribute("about"));
			datumOdbrane.setTextContent(odgovorDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
			document.getElementsByTagNameNS(Namespaces.RESENJE, "Odbrana").item(0).appendChild(
				document.importNode(odgovorDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		}
		else {
			resenje.removeChild(document.getElementsByTagNameNS(Namespaces.RESENJE, "Odbrana").item(0));
		}

		if (dto.getElementsByTagNameNS(Namespaces.RESENJE, "Odluka").getLength() > 0) {
			documentFragment.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.RESENJE, "Odluka").item(0), true));
		}
		documentFragment.appendChild(document.importNode(this.korisnikService.loadUser(this.korisnikService.currentUser().getMejl()).getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		documentFragment.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		
		Node podaciZahteva = document.importNode((Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0), true);
		documentFragment.appendChild(podaciZahteva);
		document.renameNode(podaciZahteva, Namespaces.RESENJE, "resenje:PodaciZahteva");
		resenje.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciZalbe").item(0));

		document.getElementsByTagNameNS(Namespaces.RESENJE, "tipZalbe").item(0).setTextContent(ZalbaMapper.getTipZalbe(zalbaDocument) + "");
		Element datumZalbe = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "datumZalbe").item(0);
		datumZalbe.setAttribute("href", zalba.getAttribute("about"));
		datumZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		if (zalba.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").getLength() > 0) {
			Node datumProsledjivanja = document.createElementNS(Namespaces.RESENJE, "resenje:datumProsledjivanja");
			datumProsledjivanja.setTextContent(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").item(0).getTextContent());
			document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciZalbe").item(0).appendChild(datumProsledjivanja);
		}
		if (zalba.getElementsByTagNameNS(Namespaces.ZALBA, "datumOtkazivanja").getLength() > 0) {
			Node datumOtkazivanja = document.createElementNS(Namespaces.RESENJE, "resenje:datumOtkazivanja");
			datumOtkazivanja.setTextContent(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "datumOtkazivanja").item(0).getTextContent());
			document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciZalbe").item(0).appendChild(datumOtkazivanja);
		}
		
		if (zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").getLength() > 0) {
			Node podaciOdluke = document.importNode((Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0), true);
			resenje.appendChild(podaciOdluke);
			document.renameNode(podaciOdluke, Namespaces.RESENJE, "resenje:PodaciOdluke");
		}
				
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
				Document resenjeDocument = this.domParser.buildDocument(resource.getContent().toString());
				Node resenje = resenjaDocument.createElementNS(Namespaces.RESENJE, "Resenje");
				
				Node broj = resenjaDocument.createElementNS(Namespaces.OSNOVA, "broj");
				broj.setTextContent(Utils.getBroj(resenjeDocument));
				resenje.appendChild(broj);
				resenje.appendChild(resenjaDocument.importNode(resenjeDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				resenje.appendChild(resenjaDocument.importNode(resenjeDocument.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0), true));
				resenja.appendChild(resenje);
			}
			
			return this.domParser.buildXml(resenjaDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public static StatusResenja getStatusResenja(Document document) {
		try {
			return StatusResenja.valueOf(document.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0).getTextContent());
		}
		catch(Exception e) {
			return StatusResenja.ponisteno;
		}
	}
}
