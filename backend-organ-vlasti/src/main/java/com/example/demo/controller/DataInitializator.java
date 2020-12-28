package com.example.demo.controller;

import java.io.File;
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
import com.example.demo.repository.OrganVlastiRepository;

@RestController
@RequestMapping(value = "/init_data")
public class DataInitializator {
	
	private static final String ORGAN_VLASTI1 = Constants.INIT_FOLDER + File.separatorChar + "organ_vlasti1.xml";
	private static final String SLUZBENIK1 = Constants.INIT_FOLDER + File.separatorChar + "sluzbenik1.xml";

	@Autowired
	private ExistManager existManager;

	@Autowired
	private DOMParser domParser;
	
	@GetMapping
	public void initData() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, ParserConfigurationException, SAXException, IOException {
		this.existManager.save(OrganVlastiRepository.ORGAN_VLASTI_COLLECTION, "1", this.domParser.buildDocumentFromFile(ORGAN_VLASTI1));
		this.existManager.save(KorisnikRepository.KORISNICI_COLLECTION, "sluzbenik@gmail.com", this.domParser.buildDocumentFromFile(SLUZBENIK1));
	}
	
}
