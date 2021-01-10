package com.example.demo.mapper;

import java.io.StringReader;
import java.util.Date;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
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
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.xml.OdgovorExist;
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
	private JAXBParser jaxbParser;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Override
	public Document map(String xml) {
		Document document = this.domParser.buildDocument(xml);
		Element resenje = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "Resenje").item(0);
		String zalbaBroj = resenje.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
		resenje.removeChild(document.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0));
		Document zalbaDocument = this.zalbaExist.load(zalbaBroj);
		DocumentFragment documentFragment = document.createDocumentFragment();
		
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(Constants.sdf.format(new Date()));
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		resenje.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0));
		
		Document odgovor = this.odgovorExist.load(zalbaBroj);
		if (odgovor != null) {
			Element resenjeOdgovor = document.createElementNS(Namespaces.RESENJE, "Odbrana");
			Element datumOdbrane = document.createElementNS(Namespaces.RESENJE, "datumOdbrane");
			datumOdbrane.setTextContent(odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
			resenjeOdgovor.appendChild(datumOdbrane);
			resenjeOdgovor.appendChild(document.importNode(odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
			resenje.insertBefore(resenjeOdgovor, document.getElementsByTagNameNS(Namespaces.RESENJE, "Odluka").item(0));
		}
					
		resenje.appendChild(document.importNode(this.jaxbParser.marshal(this.korisnikService.currentUser().getOsoba()).getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		resenje.appendChild(document.importNode(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		
		Element podaciZahteva = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		Element podaciZahtevaResenje = document.createElementNS(Namespaces.RESENJE, "PodaciZahteva");
		podaciZahtevaResenje.appendChild(document.importNode(podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
		podaciZahtevaResenje.appendChild(document.importNode(podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
		podaciZahtevaResenje.appendChild(document.importNode(podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		resenje.appendChild(document.importNode(podaciZahtevaResenje, true));
		
		Node podaciZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:PodaciZalbe");
		Node tipZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:tipZalbe");
		tipZalbe.setTextContent(ZalbaMapper.getTipZalbe(zalbaDocument) + "");
		podaciZalbe.appendChild(tipZalbe);
		Node brojZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:brojZalbe");
		brojZalbe.setTextContent(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		podaciZalbe.appendChild(brojZalbe);
		Node datumZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:datumZalbe");
		datumZalbe.setTextContent(zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		podaciZalbe.appendChild(datumZalbe);
		Node datumProsledjivanja = document.createElementNS(Namespaces.RESENJE, "resenje:datumProsledjivanja");
		datumProsledjivanja.setTextContent(zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").item(0).getTextContent());
		podaciZalbe.appendChild(datumProsledjivanja);
		resenje.appendChild(podaciZalbe);
		
		if (zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").getLength() > 0) {
			Element podaciOdluke = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			Element podaciOdlukeResenje = document.createElementNS(Namespaces.RESENJE, "PodaciOdluke");
			podaciOdlukeResenje.appendChild(document.importNode(podaciOdluke.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
			podaciOdlukeResenje.appendChild(document.importNode(podaciOdluke.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
			podaciOdlukeResenje.appendChild(document.importNode(podaciOdluke.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
			resenje.appendChild(document.importNode(podaciOdlukeResenje, true));
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

	@Override
	public Model map(Document document) {
		Element resenje = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "Resenje").item(0);
		resenje.setAttribute("xmlns:xs", Namespaces.XS);
		resenje.setAttribute("xmlns:pred", Prefixes.PREDIKAT);
		resenje.setAttribute("about", Prefixes.RESENJE_PREFIX + resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		resenje.setAttribute("rel", "pred:izdao");
		resenje.setAttribute("href", Prefixes.KORISNIK_PREFIX + this.korisnikService.currentUser().getOsoba().getMejl());
		
		((Element) resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("property", "pred:datum");
		((Element) resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("datatype", "xs:string");
		((Element) resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("property", "pred:mesto");
		((Element) resenje.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("datatype", "xs:string");
		
		Element brojZahteva = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciZahteva").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
		brojZahteva.setAttribute("rel", "pred:zahtev");
		brojZahteva.setAttribute("href", Prefixes.ZAHTEV_PREFIX + brojZahteva.getTextContent());
		Element brojZalbe = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciZalbe").item(0)).getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0);
		brojZalbe.setAttribute("rel", "pred:zalba");
		brojZalbe.setAttribute("href", Prefixes.ZALBA_PREFIX + brojZalbe.getTextContent());
		
		if (resenje.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciOdluke").getLength() > 0) {
			Element brojOdluke = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "PodaciOdluke").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
			brojOdluke.setAttribute("rel", "pred:odluka");
			brojOdluke.setAttribute("href", Prefixes.ODLUKA_PREFIX + brojOdluke.getTextContent());			
		}
		
		String result = this.xslTransformer.generateMetadata(this.domParser.buildXml(document)).toString();
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", Prefixes.PREDIKAT);
		model.read(new StringReader(result), null);
		return model;
	}
	
}
