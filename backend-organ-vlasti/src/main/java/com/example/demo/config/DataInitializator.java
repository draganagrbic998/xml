package com.example.demo.config;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.exist.ExistManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.OdgovorRepository;
import com.example.demo.repository.OrganVlastiRepository;
import com.example.demo.repository.ZahtevRepository;

@Component
public class DataInitializator {
	
	private static final String ORGAN_VLASTI1 = Constants.INIT_FOLDER + File.separatorChar + "organ_vlasti1.xml";
	private static final String SLUZBENIK1 = Constants.INIT_FOLDER + File.separatorChar + "sluzbenik1.xml";

	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private DOMParser domParser;

	@EventListener(ContextRefreshedEvent.class)
	public void dataInit() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, ParserConfigurationException, SAXException, IOException {
		this.existManager.dropCollection(OrganVlastiRepository.ORGAN_VLASTI_COLLECTION);
		this.existManager.dropCollection(KorisnikRepository.KORISNICI_COLLECTION);
		this.existManager.dropCollection(ZahtevRepository.ZAHTEVI_COLLECTION);
		this.existManager.dropCollection(OdgovorRepository.ODGOVORI_COLLECTION);
		this.existManager.save(OrganVlastiRepository.ORGAN_VLASTI_COLLECTION, "1", this.domParser.buildDocumentFromFile(ORGAN_VLASTI1));
		this.existManager.save(KorisnikRepository.KORISNICI_COLLECTION, "sluzbenik@gmail.com", this.domParser.buildDocumentFromFile(SLUZBENIK1));
	}
	
}
