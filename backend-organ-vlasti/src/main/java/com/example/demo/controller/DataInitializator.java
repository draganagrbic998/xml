package com.example.demo.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.exist.ExistManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.KorisnikRepository;

@RestController
@RequestMapping(value = "/init_data")
public class DataInitializator {
	
	private static final String KORISNIK1 = Constants.INIT_FOLDER + "/draganaasd@gmail.com.xml";

	@Autowired
	private ExistManager existManager;

	@Autowired
	private DOMParser domParser;
	
	@GetMapping
	public void initData() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, ParserConfigurationException, SAXException, IOException {
		this.existManager.save(KorisnikRepository.KORISNICI_COLLECTION, KORISNIK1, this.domParser.buildDocumentFromFile(KORISNIK1));
	}
	
}
