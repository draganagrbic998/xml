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
import com.example.demo.model.OrganVlasti;
import com.example.demo.model.enums.StatusZahteva;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.ZahtevRepository;
import com.ibm.icu.text.SimpleDateFormat;

@Service
public class ZahtevService {
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private KorisnikService korisnikService;
	
	public OrganVlasti defaultOrganVlasti() {
		//izmeni ovo kasnije tako da ucita podatke iz nekog xml fajla
		OrganVlasti organVlasti = new OrganVlasti();
		organVlasti.setNaziv("TEST NAZIV");
		organVlasti.setSediste("TEST SEDISTE");
		return organVlasti;
	}
	
	public String defaultMesto() {
		//izmeni ovo na ono sto on kaze
		return "TEST MESTO";
	}
	
	public String testPotpis() {
		//izmeni ovo na ono sto on kaze
		return "TEST POTPIS";
	}
	
	public void save(String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		Document document = this.domParser.buildDocument(xml);
		
		((Element) document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0)).removeAttribute("xml:space");
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
		
		Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.DOKUMENT, "Zahtev").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		
		datum.setTextContent(sdf.format(new Date()));
		Node mesto = document.createElementNS(Namespaces.OSNOVA, "mesto");
		mesto.setTextContent(this.defaultMesto());
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(mesto);
		
		Node gradjanin = document.importNode(this.jaxbParser.marshal(this.korisnikService.currentUser().getGradjanin()).getFirstChild(), true);
		Node organVlasti = document.importNode(this.jaxbParser.marshal(this.defaultOrganVlasti()).getFirstChild(), true);
		
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(organVlasti);
		
		zahtev.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		DocumentFragment documentFragment2 = document.createDocumentFragment();
		Node potpis = document.createElementNS(Namespaces.OSNOVA, "potpis");
		potpis.setTextContent(this.testPotpis());
		documentFragment2.appendChild(potpis);
		Node status = document.createElementNS(Namespaces.OSNOVA, "status");
		status.setTextContent(StatusZahteva.cekanje + "");
		documentFragment2.appendChild(status);
		zahtev.insertBefore(documentFragment2, document.getElementsByTagNameNS(Namespaces.DOKUMENT, "tipZahteva").item(0));
		this.zahtevRepository.save(document);
	}
	
}
