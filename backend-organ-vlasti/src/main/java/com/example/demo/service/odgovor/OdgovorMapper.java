package com.example.demo.service.odgovor;

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

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Prefixes;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.ibm.icu.text.SimpleDateFormat;

@Component
public class OdgovorMapper {

	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		
	public Document map(String xml) {
		Document document = this.domParser.buildDocument(xml);
		Element odgovor = (Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0);
		String broj = odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		Element zalba = (Element) this.zalbaExist.load(broj).getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		Element osoba = (Element) document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true);
		Element organVlasti = (Element) document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);		
		osoba.getElementsByTagNameNS(Namespaces.OSNOVA, "potpis").item(0).setTextContent(this.korisnikService.currentUser().getOsoba().getPotpis());
		documentFragment.appendChild(datum);
		documentFragment.appendChild(osoba);
		documentFragment.appendChild(organVlasti);
		odgovor.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));	

		Node datumZalbe = document.createElementNS(Namespaces.ODGOVOR, "odgovor:datumZalbe");
		datumZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());	
		odgovor.appendChild(datumZalbe);
		return document;
	}
	
	public Model map(Document document) {
		Element odgovor = (Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0);
		String broj = odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		
		odgovor.setAttribute("xmlns:xs", Namespaces.XS);
		odgovor.setAttribute("xmlns:pred", Prefixes.PREDIKAT);
		odgovor.setAttribute("about", Prefixes.ODGOVOR_PREFIX + broj);
		
		odgovor.setAttribute("rel", "pred:zalba");
		odgovor.setAttribute("href", Prefixes.ZALBA_PREFIX + broj);
		
		((Element) odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("property", "pred:datum");
		((Element) odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0)).setAttribute("datatype", "xs:string");
		
		((Element) odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("property", "pred:izdatoU");
		((Element) odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0)).setAttribute("datatype", "xs:string");

		String result = this.xslTransformer.generateMetadata(this.domParser.buildXml(document)).toString();
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", Prefixes.PREDIKAT);
		model.read(new StringReader(result), null);
		return model;

	}
	
}
