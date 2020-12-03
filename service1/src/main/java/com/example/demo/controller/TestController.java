package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

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

import jaxb.NSPrefixMapper;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
	
	private static String LOKACIJA_SEMA = "data/xsd/";//"file:/C:/Users/petar/Desktop/projekti/schema/";
	
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
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "organ_vlasti.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));
		
		this.domParser.transformDocument(document, System.out);
	}

	@GetMapping(value = "/test_organ_vlasti_jaxb")
	public void testOrganVlastiJaxb() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(OrganVlasti.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();	
		OrganVlasti organVlasti = (OrganVlasti) unmarshaller.unmarshal(new File("data/xml/organ_vlasti1.xml"));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://github.com/draganagrbic998/xml/organ_vlasti " + LOKACIJA_SEMA + "organ_vlasti.xsd");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "organ_vlasti.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		marshaller.setSchema(schema);
		
		marshaller.marshal(organVlasti, System.out);
	}
	
	@GetMapping(value = "/test_korisnik")
	public void testKorisnik() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/korisnik1.xml");
		Korisnik korisnik = this.korisnikParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element korisnikElement = this.korisnikParser.parse(korisnik, document);
		document.appendChild(korisnikElement);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "korisnik.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));
		
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_korisnik_jaxb")
	public void testKorisnikJaxb() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Korisnik.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Korisnik korisnik = (Korisnik) unmarshaller.unmarshal(new File("data/xml/korisnik1.xml"));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://github.com/draganagrbic998/xml/korisnik " + LOKACIJA_SEMA + "korisnik.xsd");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "korisnik.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		marshaller.setSchema(schema);
		
		marshaller.marshal(korisnik, System.out);
	}
	
	@GetMapping(value = "/test_zahtev")
	public void testZahtev() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/zahtev1.xml");
		Zahtev zahtev = this.zahtevParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element zahtevElement = this.zahtevParser.parse(zahtev, document);
		document.appendChild(zahtevElement);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "zahtev.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));

		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_zahtev_jaxb")
	public void testZahtevJaxb() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Zahtev.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("data/xml/zahtev1.xml"));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://github.com/draganagrbic998/xml/zahtev " + LOKACIJA_SEMA + "zahtev.xsd");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "zahtev.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		marshaller.setSchema(schema);
		
		marshaller.marshal(zahtev, System.out);
	}
	
	@GetMapping(value = "/test_obavestenje")
	public void testObavestenje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/obavestenje1.xml");
		Obavestenje obavestenje = this.obavestenjeParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element odlukaElement = this.obavestenjeParser.parse(obavestenje, document);
		document.appendChild(odlukaElement);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "obavestenje.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));
		
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_obavestenje_jaxb")
	public void testObavestenjeJaxb() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Obavestenje.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Obavestenje obavestenje = (Obavestenje) unmarshaller.unmarshal(new File("data/xml/obavestenje1.xml"));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://github.com/draganagrbic998/xml/obavestenje " + LOKACIJA_SEMA + "obavestenje.xsd");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "obavestenje.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		marshaller.setSchema(schema);
		
		marshaller.marshal(obavestenje, System.out);
	}
	
	@GetMapping(value = "/test_odbijanje")
	public void testOdbijanje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/odbijanje1.xml");
		Odbijanje odbijanje = this.odbijanjeParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element odlukaElement = this.odbijanjeParser.parse(odbijanje, document);
		document.appendChild(odlukaElement);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "odbijanje.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));
		
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_odbijanje_jaxb")
	public void testOdbijanjeJaxb() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Odbijanje.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Odbijanje odbijanje = (Odbijanje) unmarshaller.unmarshal(new File("data/xml/odbijanje1.xml"));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://github.com/draganagrbic998/xml/odbijanje " + LOKACIJA_SEMA + "odbijanje.xsd");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "odbijanje.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		marshaller.setSchema(schema);
		
		marshaller.marshal(odbijanje, System.out);
	}
	
	@GetMapping(value = "/test_zalba")
	public void testZalba() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/zalba1.xml");
		Zalba zalba = this.zalbaParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element zalbaElement = this.zalbaParser.parse(zalba, document);
		document.appendChild(zalbaElement);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "zalba.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));
		
		this.domParser.transformDocument(document, System.out);
	}

	@GetMapping(value = "/test_zalba_jaxb")
	public void testZalbaJaxb() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Zalba.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Zalba zalba = (Zalba) unmarshaller.unmarshal(new File("data/xml/zalba1.xml"));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://github.com/draganagrbic998/xml/zalba " + LOKACIJA_SEMA + "zalba.xsd");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "zalba.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		marshaller.setSchema(schema);
		
		marshaller.marshal(zalba, System.out);
	}
	
	@GetMapping(value = "/test_resenje")
	public void testResenje() throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
		Document document = this.domParser.buildDocumentFromFile("data/xml/resenje1.xml");
		Resenje resenje = this.resenjeParser.parse(document.getDocumentElement());
		document.removeChild(document.getDocumentElement());
		Element resenjeElement = this.resenjeParser.parse(resenje, document);
		document.appendChild(resenjeElement);
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "resenje.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));
		
		this.domParser.transformDocument(document, System.out);
	}
	
	@GetMapping(value = "/test_resenje_jaxb")
	public void testResenjeJaxb() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Resenje.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Resenje resenje = (Resenje) unmarshaller.unmarshal(new File("data/xml/resenje1.xml"));
		Marshaller marshaller = context.createMarshaller();
		
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File(LOKACIJA_SEMA + "resenje.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		marshaller.setSchema(schema);
		
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://github.com/draganagrbic998/xml/resenje " + LOKACIJA_SEMA + "resenje.xsd");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(resenje, System.out);
	}
}
