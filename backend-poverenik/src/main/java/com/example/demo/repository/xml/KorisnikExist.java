package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class KorisnikExist {

	@Autowired
	private ExistManager existManager;
		
	public static final String KORISNIK_COLLECTION = Constants.COLLECTIONS_PREFIX + "/korisnici";
	public static final String KORISNIK_SCHEMA = Constants.XSD_FOLDER + "osnova.xsd";
	
	public void save(String documentId, Document document) {
		this.existManager.save(KORISNIK_COLLECTION, documentId, document, KORISNIK_SCHEMA);
	}
	
	public Document load(String documentId) {
		return this.existManager.load(KORISNIK_COLLECTION, documentId);
	}
		
}
