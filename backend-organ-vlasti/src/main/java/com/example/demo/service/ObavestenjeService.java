package com.example.demo.service;

import java.io.IOException;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Namespaces;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.ObavestenjeRepository;
import com.example.demo.repository.ZahtevRepository;
import com.ibm.icu.text.SimpleDateFormat;

@Service
public class ObavestenjeService {

	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private ObavestenjeRepository obavestenjeRepository;

	@Autowired
	private DOMParser domParser;
	
	public String defaultMesto() {
		//izmeni ovo na ono sto on kaze
		return "TEST MESTO";
	}
	
	public String testPotpis() {
		//izmeni ovo na ono sto on kaze
		return "TEST POTPIS";
	}

	public void save(String brojZahteva, String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		Document document = this.domParser.buildDocument(xml);
		Element detalji = (Element) document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0);
		
		detalji.removeAttribute("xml:space");
		NodeList bolds = document.getElementsByTagNameNS(Namespaces.OSNOVA, "bold");
		for (int i = 0; i < bolds.getLength(); ++i) {
			Element bold = (Element) bolds.item(i);
			bold.removeAttribute("xml:space");
		}
		NodeList italics = document.getElementsByTagNameNS(Namespaces.OSNOVA, "italic");
		for (int i = 0; i < italics.getLength(); ++i) {
			Element italic = (Element) italics.item(i);
			italic.removeAttribute("xml:space");
		}
		
		Element obavestenje = (Element) document.getElementsByTagNameNS(Namespaces.DOKUMENT, "Obavestenje").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		
		datum.setTextContent(sdf.format(new Date()));
		Node mesto = document.createElementNS(Namespaces.OSNOVA, "mesto");
		mesto.setTextContent(this.defaultMesto());
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(mesto);

		obavestenje.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.DOKUMENT, "Zahtev").item(0));
		Element zahtev = (Element) this.zahtevRepository.load(brojZahteva).getElementsByTagNameNS(Namespaces.DOKUMENT, "Zahtev").item(0);
		Element obavestenjeZahtev = (Element) obavestenje.getElementsByTagNameNS(Namespaces.DOKUMENT, "Zahtev").item(0);
		DocumentFragment documentFragment2 = document.createDocumentFragment();
		
		
		documentFragment2.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
		documentFragment2.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0), true));
		documentFragment2.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "Gradjanin").item(0), true));
		documentFragment2.appendChild(document.importNode(zahtev.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		obavestenjeZahtev.insertBefore(documentFragment2, detalji);
		
		Node potpis = document.createElementNS(Namespaces.OSNOVA, "potpis");
		potpis.setTextContent(this.testPotpis());
		obavestenje.appendChild(potpis);
		this.obavestenjeRepository.save(document);
	
	}
	
}
