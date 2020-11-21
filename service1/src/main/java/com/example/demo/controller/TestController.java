package com.example.demo.controller;

import java.io.IOException;
import java.text.ParseException;

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
import com.example.demo.model.Korisnik;
import com.example.demo.model.Odluka;
import com.example.demo.model.OrganVlasti;
import com.example.demo.model.Zahtev;
import com.example.demo.model.Zalba;
import com.example.demo.parser.KorisnikParser;
import com.example.demo.parser.OdlukaParser;
import com.example.demo.parser.OrganVlastiParser;
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
	private OdlukaParser odlukaParser;
	
	@Autowired
	private ZalbaParser zalbaParser;
	
	@GetMapping(value = "/test_organ_vlasti")
	public void testOrganVlasti() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/organ_vlasti1.xml");
		OrganVlasti organVlasti = this.organVlastiParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element organVlastiElement = this.organVlastiParser.parse("organ_vlasti", organVlasti, document);
		document.appendChild(organVlastiElement);
		this.domParser.transformDocument(document, System.out);
	}

	@GetMapping(value = "/test_korisnik")
	public void testKorisnik() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/korisnik1.xml");
		Korisnik korisnik = this.korisnikParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element korisnikElement = this.korisnikParser.parse("korisnik", korisnik, document);
		document.appendChild(korisnikElement);
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_zahtev")
	public void testZahtev() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/zahtev1.xml");
		Zahtev zahtev = this.zahtevParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element zahtevElement = this.zahtevParser.parse("zahtev", zahtev, document);
		document.appendChild(zahtevElement);
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_odluka")
	public void testOdluka() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/odluka1.xml");
		Odluka odluka = this.odlukaParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element odlukaElement = this.odlukaParser.parse("odluka", odluka, document);
		document.appendChild(odlukaElement);
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_zalba")
	public void testZalba() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/zalba1.xml");
		Zalba zalba = this.zalbaParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element zalbaElement = this.zalbaParser.parse("zalba", zalba, document);
		document.appendChild(zalbaElement);
		this.domParser.transformDocument(document, System.out);
	}
	
}
