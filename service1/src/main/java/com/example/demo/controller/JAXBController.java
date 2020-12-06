package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.example.demo.common.Schemas;
import com.example.demo.common.TestFiles;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Odluka;
import com.example.demo.model.OrganVlasti;
import com.example.demo.model.Zahtev;
import com.example.demo.model.Zalba;
import com.example.demo.parser.SchemaValidator;

@RestController
@RequestMapping(value = "/jaxb", produces = MediaType.APPLICATION_JSON_VALUE)
public class JAXBController {
	
	@Autowired
	private SchemaValidator schemaValidator;
	
	public void testParser(Class<?> className, String schemaFile, String testFile) throws JAXBException, SAXException {
		Schema schema = this.schemaValidator.generateSchema(schemaFile);
		JAXBContext context = JAXBContext.newInstance(className);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(schema);
		Object obj = unmarshaller.unmarshal(new File(testFile));		
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setSchema(schema);
		marshaller.marshal(obj, System.out);
	}

	@GetMapping(value = "/test_organ_vlasti")
	public void testOrganVlasti() throws ParserConfigurationException, SAXException, IOException, TransformerException, ParseException, JAXBException {
		this.testParser(OrganVlasti.class, Schemas.ORGAN_VLASTI, TestFiles.ORGAN_VLASTI);
	}
	
	@GetMapping(value = "/test_korisnik")
	public void testKorisnik() throws ParserConfigurationException, SAXException, IOException, TransformerException, ParseException, JAXBException {
		this.testParser(Korisnik.class, Schemas.KORISNIK, TestFiles.KORISNIK);
	}
	
	@GetMapping(value = "/test_zahtev_uvid")
	public void testZahtevObavestenje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException, JAXBException {
		this.testParser(Zahtev.class, Schemas.ZAHTEV, TestFiles.ZAHTEV_UVID);
	}
	
	@GetMapping(value = "/test_zahtev_dostava")
	public void testZahtevOdgovor() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException, JAXBException {
		this.testParser(Zahtev.class, Schemas.ZAHTEV, TestFiles.ZAHTEV_DOSTAVA);
	}
	
	@GetMapping(value = "/test_obavestenje")
	public void testOdlukaObavestenje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException, JAXBException {
		this.testParser(Odluka.class, Schemas.ODLUKA, TestFiles.OBAVESTENJE);
	}
	
	@GetMapping(value = "/test_odgovor")
	public void testOdlukaOdgovor() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException, JAXBException {
		this.testParser(Odluka.class, Schemas.ODLUKA, TestFiles.ODGOVOR);
	}
	
	@GetMapping(value = "/test_odbijanje")
	public void testOdlukaOdbijanje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException, JAXBException {
		this.testParser(Odluka.class, Schemas.ODLUKA, TestFiles.ODBIJANJE);
	}
		
	@GetMapping(value = "/test_zalba_cutanje")
	public void testZalbaCutanje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException, JAXBException {
		this.testParser(Zalba.class, Schemas.ZALBA, TestFiles.ZALBA_CUTANJE);
	}
	
	@GetMapping(value = "/test_zalba_delimicnost")
	public void testZalbaDelimicnost() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException, JAXBException {
		this.testParser(Zalba.class, Schemas.ZALBA, TestFiles.ZALBA_DELIMICNOST);
	}
	
}
