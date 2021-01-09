package com.example.demo.service.zalba;

import java.io.StringReader;
import java.text.SimpleDateFormat;
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
import com.example.demo.enums.StatusZalbe;
import com.example.demo.enums.TipZalbe;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.service.KorisnikService;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.TipDokumenta;

@Component
public class ZalbaMapper {

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private DOMParser domParser;
		
	@Autowired
	private JAXBParser jaxbParser;
		
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private SOAPService soapService;
		
	public static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	public Document map(String xml) {
		Document document = this.domParser.buildDocument(xml);
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		Element korisnik = (Element) this.jaxbParser.marshal(this.korisnikService.currentUser()).getElementsByTagNameNS(Namespaces.OSNOVA, "Korisnik").item(0);
		Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(gradjanin);
		String brojZahteva;
		
		if (document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").getLength() > 0) {
			String brojOdluke = ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
			Document odlukaDocument = this.domParser.buildDocument(this.soapService.sendSOAPMessage(brojOdluke, TipDokumenta.odluka));
			Element odluka = (Element) odlukaDocument.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
			Node podaciOdluke = document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			podaciOdluke.appendChild(document.importNode(odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
			podaciOdluke.appendChild(document.importNode(odluka.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
			brojZahteva = odluka.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
			

		}
		else {
			brojZahteva = ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();		
		}
		
		Document zahtevDocument = this.domParser.buildDocument(this.soapService.sendSOAPMessage(brojZahteva, TipDokumenta.zahtev));
		Element zahtev = (Element) zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
		documentFragment.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		
		zalba.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		Node status = document.createElementNS(Namespaces.ZALBA, "zalba:status");
		status.setTextContent(StatusZalbe.cekanje + "");
		zalba.insertBefore(status, document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0));
		
		Node podaciZahteva = document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		((Element) podaciZahteva).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(brojZahteva);
		podaciZahteva.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
		podaciZahteva.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		
		return document;
	}
	
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
				Node statusNode = zalba.appendChild(zalbeDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0), true));
				StatusZalbe status = StatusZalbe.valueOf(statusNode.getTextContent());
				if (!status.equals(StatusZalbe.cekanje))
					zalba.appendChild(zalbeDocument.importNode(document.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").item(0), true));
				zalbe.appendChild(zalba);
			}

			return this.domParser.buildXml(zalbeDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public Model map(Document document) {
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		zalba.setAttribute("xmlns:xs", Namespaces.XS);
		zalba.setAttribute("xmlns:pred", Prefixes.PREDIKAT);
		zalba.setAttribute("about", Prefixes.ZALBA_PREFIX + zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent());
		
		zalba.setAttribute("rel", "pred:podneo");
		zalba.setAttribute("href", Prefixes.KORISNIK_PREFIX + this.korisnikService.currentUser().getOsoba().getMejl());
		
		Node tipZalbe = document.createElementNS(Namespaces.ZALBA, "tipZalbe");
		tipZalbe.setTextContent(getTipZalbe(document) + "");
		zalba.appendChild(tipZalbe);
		((Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "tipZalbe").item(0)).setAttribute("property", "pred:tip");
		((Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "tipZalbe").item(0)).setAttribute("datatype", "xs:string");
		
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("property", "pred:datum");
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("datatype", "xs:string");
		
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("property", "pred:mesto");
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("datatype", "xs:string");

		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(1)).setAttribute("property", "pred:izdatoU");
		((Element) zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(1)).setAttribute("datatype", "xs:string");
		
		Element brojZahteva = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
		brojZahteva.setAttribute("rel", "pred:zahtev");
		brojZahteva.setAttribute("href", Prefixes.ZAHTEV_PREFIX + brojZahteva.getTextContent());
		
		if (zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").getLength() > 0) {
			Element brojOdluke = (Element) ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0)).getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
			brojOdluke.setAttribute("rel", "pred:odluka");
			brojOdluke.setAttribute("href", Prefixes.ODLUKA_PREFIX + brojOdluke.getTextContent());			
		}
		
		String result = this.xslTransformer.generateMetadata(this.domParser.buildXml(document)).toString();
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", Prefixes.PREDIKAT);
		model.read(new StringReader(result), null);
		return model;
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
