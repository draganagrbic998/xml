package com.example.demo.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Zalba;
import com.example.demo.model.ZalbaCutanje;
import com.example.demo.model.ZalbaOdbijanje;
import com.example.demo.model.enums.TipCutanja;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.ZalbaRepository;

@Service
public class ZalbaService {
	
	@Autowired
	private ZalbaRepository zalbaRepository;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private JAXBParser jaxbParser;
		
	@Autowired
	private DOMParser domParser;
	
	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	public void save(String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, ParserConfigurationException, SAXException, IOException, DOMException, ParseException {		
		Korisnik korisnik = this.korisnikService.currentUser();
		Document document = this.domParser.buildDocument(xml);
		Zalba zalba;
		if (document.getElementsByTagName("tipCutanja").getLength() > 0) {
			TipCutanja tipCutanja = TipCutanja.valueOf(document.getElementsByTagName("tipCutanja").item(0).getTextContent());
			zalba = new ZalbaCutanje(tipCutanja);
		}
		else {
			String brojOdluke = document.getElementsByTagName("brojOdluke").item(0).getTextContent();
			Date datumOdluke = this.format.parse(document.getElementsByTagName("datumOdluke").item(0).getTextContent());
			zalba = new ZalbaOdbijanje(brojOdluke, datumOdluke);
		}
		zalba.setGradjanin(korisnik.getGradjanin());
		zalba.setDatum(new Date());
		zalba.setDetalji(document.getElementsByTagName("detalji").item(0).getTextContent());
		zalba.setKontakt(korisnik.getEmail());
		zalba.setPotpis(Constants.SIGNATURE);
		zalba.setOdgovoreno(false);
		zalba.setOrganVlasti(document.getElementsByTagName("organVlasti").item(0).getTextContent());
		zalba.setDatumZahteva(this.format.parse(document.getElementsByTagName("datumZahteva").item(0).getTextContent()));
		zalba.setKopijaZahteva(document.getElementsByTagName("kopijaZahteva").item(0).getTextContent());
		if (document.getElementsByTagName("kopijaOdluke").getLength() > 0) {
			zalba.setKopijaOdluke(document.getElementsByTagName("kopijaOdluke").item(0).getTextContent());
		}
		
		this.zalbaRepository.save(this.jaxbParser.marshal(zalba, Zalba.class));		
	}
	
}
