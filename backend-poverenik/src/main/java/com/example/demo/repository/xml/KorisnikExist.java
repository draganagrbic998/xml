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
		
	public static final String KORISNICI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/korisnici";
	
	public void save(String documentId, Document document) {
		this.existManager.save(KORISNICI_COLLECTION, documentId, document);
	}
	
	public Document load(String documentId) {
		return this.existManager.load(KORISNICI_COLLECTION, documentId);
	}
		
}
