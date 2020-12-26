package com.example.demo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.exist.ExistManager;
import com.example.demo.repository.KorisnikRepository;

@RestController
@RequestMapping(value = "/init_data")
public class DataInitializator {
	
	private static final String DATA_FOLDER = "data/";
	private static final String KORISNIK1 = DATA_FOLDER + "draganaasd@gmail.com.xml";

	@Autowired
	private ExistManager existManager;

	@GetMapping
	public void initData() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		File file = new File(KORISNIK1);
		this.existManager.save(KorisnikRepository.KORISNICI_COLLECTION, KORISNIK1, file);
		
	}
	
}
