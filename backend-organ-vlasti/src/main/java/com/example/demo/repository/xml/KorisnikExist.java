package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class KorisnikExist implements ExistInterface {

	@Autowired
	private ExistManager existManager;
			
	public static final String KORISNIK_COLLECTION = Constants.COLLECTIONS_PREFIX + "/korisnici";
	public static final String KORISNIK_SCHEMA = Constants.XSD_FOLDER + "osnova.xsd";
	
	@Override
	public void add(Document document) {
		this.existManager.save(KORISNIK_COLLECTION, null, document, KORISNIK_SCHEMA);
	}
	
	@Override
	public void update(String documentId, Document document) {
		this.existManager.save(KORISNIK_COLLECTION, documentId, document, KORISNIK_SCHEMA);		
	}
	
	@Override
	public void delete(String documentId) {
		this.existManager.delete(KORISNIK_COLLECTION, documentId);
	}
		
	@Override
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(KORISNIK_COLLECTION, xpathExp);
	}
	
	@Override
	public Document load(String documentId) {
		return this.existManager.load(KORISNIK_COLLECTION, documentId);
	}

	@Override
	public String nextDocumentId() {
		return this.existManager.nextDocumentId(KORISNIK_COLLECTION);
	}
	
}
