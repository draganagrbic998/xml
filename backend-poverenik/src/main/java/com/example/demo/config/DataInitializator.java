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
import com.example.demo.repository.ResenjeRepository;
import com.example.demo.repository.ZalbaRepository;

@Component
public class DataInitializator {
	
	private static final String POVERENIK1 = Constants.INIT_FOLDER + File.separatorChar + "poverenik1.xml";

	@Autowired
	private ExistManager existManager;

	@Autowired
	private DOMParser domParser;

	@EventListener(ContextRefreshedEvent.class)
	public void dataInit() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, ParserConfigurationException, SAXException, IOException {
		this.existManager.dropCollection(KorisnikRepository.KORISNICI_COLLECTION);
		this.existManager.dropCollection(ZalbaRepository.ZALBE_COLLECTION);
		this.existManager.dropCollection(ResenjeRepository.RESENJA_COLLECTION);
		this.existManager.save(KorisnikRepository.KORISNICI_COLLECTION, "poverenik@gmail.com", this.domParser.buildDocumentFromFile(POVERENIK1));
	}
	
}
