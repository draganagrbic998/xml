package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.example.demo.dom.DOMParser;
import com.example.demo.model.Adresa;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Obavestenje;
import com.example.demo.model.Odbijanje;
import com.example.demo.model.OrganVlasti;
import com.example.demo.model.Resenje;
import com.example.demo.model.Zahtev;
import com.example.demo.model.Zalba;
import com.example.demo.parser.KorisnikParser;
import com.example.demo.parser.ObavestenjeParser;
import com.example.demo.parser.OdbijanjeParser;
import com.example.demo.parser.OrganVlastiParser;
import com.example.demo.parser.ResenjeParser;
import com.example.demo.parser.ZahtevParser;
import com.example.demo.parser.ZalbaParser;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private OrganVlastiParser organVlastiParser;
	
	@Autowired
	private KorisnikParser korisnikParser;
	
	@Autowired
	private ZahtevParser zahtevParser;
	
	@Autowired
	private ObavestenjeParser obavestenjeParser;
	
	@Autowired
	private OdbijanjeParser odbijanjeParser;
	
	@Autowired
	private ZalbaParser zalbaParser;
	
	@Autowired
	private ResenjeParser resenjeParser;
	
	@GetMapping(value = "/test_organ_vlasti")
	public void testOrganVlasti() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/organ_vlasti1.xml");
		OrganVlasti organVlasti = this.organVlastiParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element organVlastiElement = this.organVlastiParser.parse(organVlasti, document);
		document.appendChild(organVlastiElement);
		this.domParser.transformDocument(document, System.out);
	}

	@GetMapping(value = "/test_korisnik")
	public void testKorisnik() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/korisnik1.xml");
		Korisnik korisnik = this.korisnikParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element korisnikElement = this.korisnikParser.parse(korisnik, document);
		document.appendChild(korisnikElement);
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_korisnik_jaxb")
	public void testKorisnikJaxb() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Korisnik.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Korisnik korisnik = (Korisnik) unmarshaller.unmarshal(new File("data/xml/korisnik1.xml"));
		JAXBContext context2 = JAXBContext.newInstance(Adresa.class);
		Unmarshaller unmarshaller2 = context2.createUnmarshaller();
		Adresa adresa = (Adresa) unmarshaller2.unmarshal(new File("data/xml/korisnik1.xml"));
		System.out.println(korisnik);
	}
	
	@GetMapping(value = "/test_zahtev")
	public void testZahtev() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/zahtev1.xml");
		Zahtev zahtev = this.zahtevParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element zahtevElement = this.zahtevParser.parse(zahtev, document);
		document.appendChild(zahtevElement);
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_obavestenje")
	public void testObavestenje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/obavestenje1.xml");
		Obavestenje obavestenje = this.obavestenjeParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element odlukaElement = this.obavestenjeParser.parse(obavestenje, document);
		document.appendChild(odlukaElement);
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_odbijanje")
	public void testOdbijanje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/odbijanje1.xml");
		Odbijanje odbijanje = this.odbijanjeParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element odlukaElement = this.odbijanjeParser.parse(odbijanje, document);
		document.appendChild(odlukaElement);
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_zalba")
	public void testZalba() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/zalba1.xml");
		Zalba zalba = this.zalbaParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element zalbaElement = this.zalbaParser.parse(zalba, document);
		document.appendChild(zalbaElement);
		this.domParser.transformDocument(document, System.out);
	}

	@GetMapping(value = "/test_resenje")
	public void testResenje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/resenje1.xml");
		Resenje resenje = this.resenjeParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element resenjeElement = this.resenjeParser.parse(resenje, document);
		document.appendChild(resenjeElement);
		this.domParser.transformDocument(document, System.out);
	}
}
