package com.example.demo.service;

import java.io.IOException;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.model.Korisnik;
import com.example.demo.model.OrganVlasti;
import com.example.demo.model.Zahtev;
import com.example.demo.model.ZahtevDostava;
import com.example.demo.model.ZahtevUvid;
import com.example.demo.model.enums.TipDostave;
import com.example.demo.model.enums.TipUvida;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.OrganVlastiRepository;
import com.example.demo.repository.ZahtevRepository;

@Service
public class ZahtevService {
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private OrganVlastiRepository organVlastiRepository;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private KorisnikService korisnikService;
	
	public void save(String xml) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException {		
		Korisnik korisnik = this.korisnikService.currentUser();
		OrganVlasti organVlasti = this.organVlastiRepository.load();
		Document document = this.domParser.buildDocument(xml);
		Zahtev zahtev;
		if (document.getElementsByTagName("tipUvida").getLength() > 0) {
			TipUvida tipUvida = TipUvida.valueOf(document.getElementsByTagName("tipUvida").item(0).getTextContent());
			zahtev = new ZahtevUvid(tipUvida);
		}
		else {
			TipDostave tipDostave = TipDostave.valueOf(document.getElementsByTagName("tipDostave").item(0).getTextContent());
			String opisDostave = document.getElementsByTagName("opisDostave").item(0).getTextContent();
			zahtev = new ZahtevDostava(tipDostave, opisDostave);
		}
		zahtev.setGradjanin(korisnik.getGradjanin());
		zahtev.setOrganVlasti(organVlasti);
		zahtev.setDatum(new Date());
		zahtev.setDetalji(document.getElementsByTagName("detalji").item(0).getTextContent());
		zahtev.setKontakt(korisnik.getEmail());
		zahtev.setPotpis("asd");
		zahtev.setOdgovoreno(false);
		this.zahtevRepository.save(this.jaxbParser.marshal(zahtev, Zahtev.class));
	}
	
}
