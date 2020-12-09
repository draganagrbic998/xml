package com.example.demo.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.example.demo.common.TestFiles;
import com.example.demo.dom.KorisnikParser;
import com.example.demo.dom.OdlukaParser;
import com.example.demo.dom.OrganVlastiParser;
import com.example.demo.dom.Parser;
import com.example.demo.dom.ResenjeParser;
import com.example.demo.dom.ZahtevParser;
import com.example.demo.dom.ZalbaParser;
import com.example.demo.model.DocumentEntity;
import com.example.demo.model.resenje.Pasus;
import com.example.demo.model.resenje.PasusItem;
import com.example.demo.model.resenje.Resenje;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.SchemaValidator;

@RestController
@RequestMapping(value = "/dom", produces = MediaType.APPLICATION_JSON_VALUE)
public class DOMController {

	@Autowired
	private DOMParser domParser;

	@Autowired
	private SchemaValidator schemaValidator;
				
	public <T> void testParser(Parser<T> parser, String testFile) throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
	    Schema schema = this.schemaValidator.generateSchema(parser.getSchema());
		Document document = this.domParser.buildDocumentFromFile(testFile);
	    schema.newValidator().validate(new DOMSource(document));			
		T type = parser.parse(document.getDocumentElement());
		if (type instanceof DocumentEntity) {
			DocumentEntity documentType = (DocumentEntity) type;
			System.out.println("Broj ucitanog dokumenta je: " + documentType.getDocumentBroj());
			documentType.setDocumentBroj("0" + documentType.getDocumentBroj().substring(1));
			System.out.println("Broj izmenjenog dokumenta je: " + documentType.getDocumentBroj());
		}
		if (type instanceof Resenje) {
			Resenje resenje = (Resenje) type;
			for (Pasus p: resenje.getOdluka()) {
				for (PasusItem pi: p.getItems()) {
					if (!pi.getValue().trim().equals("")) {
						System.out.println(pi.getType() + ": " + pi.getValue());						
					}
				}
			}
		}
		Element typeElement = parser.parse(type, document);
		document.removeChild(document.getDocumentElement());
		document.appendChild(typeElement);
		schema.newValidator().validate(new DOMSource(document));		
		this.domParser.transformDocument(document, System.out);
		//this.domParser.transformDocument(document, new FileOutputStream(new File(testFile.replace(".xml", "_dom.xml"))));
	}
		
	@GetMapping(value = "/test_organ_vlasti")
	public void testOrganVlasti() throws ParserConfigurationException, SAXException, IOException, TransformerException, ParseException {
		this.testParser(this.organVlastiParser, TestFiles.ORGAN_VLASTI);
	}
	
	@GetMapping(value = "/test_korisnik")
	public void testKorisnik() throws ParserConfigurationException, SAXException, IOException, TransformerException, ParseException {
		this.testParser(this.korisnikParser, TestFiles.KORISNIK);
	}
	
	@GetMapping(value = "/test_zahtev_uvid")
	public void testZahtevUvid() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		this.testParser(this.zahtevParser, TestFiles.ZAHTEV_UVID);
	}
	
	@GetMapping(value = "/test_zahtev_dostava")
	public void testZahtevDostava() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		this.testParser(this.zahtevParser, TestFiles.ZAHTEV_DOSTAVA);
	}
	
	@GetMapping(value = "/test_obavestenje")
	public void testObavestenje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		this.testParser(this.odlukaParser, TestFiles.OBAVESTENJE);
	}
	
	@GetMapping(value = "/test_odgovor")
	public void testOdgovor() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		this.testParser(this.odlukaParser, TestFiles.ODGOVOR);
	}
	
	@GetMapping(value = "/test_odbijanje")
	public void testOdbijanje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		this.testParser(this.odlukaParser, TestFiles.ODBIJANJE);
	}
		
	@GetMapping(value = "/test_zalba_cutanje")
	public void testZalbaCutanje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		this.testParser(this.zalbaParser, TestFiles.ZALBA_CUTANJE);
	}
	
	@GetMapping(value = "/test_zalba_delimicnost")
	public void testZalbaDelimicnost() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		this.testParser(this.zalbaParser, TestFiles.ZALBA_DELIMICNOST);
	}

	@GetMapping(value = "/test_resenje")
	public void testResenje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		this.testParser(this.resenjeParser, TestFiles.RESENJE);
	}
	
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
	
	@Autowired
	private ResenjeParser resenjeParser;

}
