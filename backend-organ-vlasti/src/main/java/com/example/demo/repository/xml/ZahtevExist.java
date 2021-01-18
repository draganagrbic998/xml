package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class ZahtevExist implements ExistInterface {

	@Autowired
	private ExistManager existManager;
			
	public static final String ZAHTEV_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zahtevi";
	public static final String ZAHTEV_SCHEMA = Constants.XSD_FOLDER + "zahtev.xsd";
	
	@Override
	public void add(Document document) {
		this.existManager.save(ZAHTEV_COLLECTION, null, document, ZAHTEV_SCHEMA);
	}
	
	@Override
	public void update(String documentId, Document document) {
		this.existManager.save(ZAHTEV_COLLECTION, documentId, document, ZAHTEV_SCHEMA);
	}
	
	@Override
	public void delete(String documentId) {
		this.existManager.delete(ZAHTEV_COLLECTION, documentId);
	}
	
	@Override
	public Document load(String documentId) {
		return this.existManager.load(ZAHTEV_COLLECTION, documentId);
	}
	
	@Override
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ZAHTEV_COLLECTION, xpathExp);
	}
	
	@Override
	public String nextDocumentId() {
		return this.existManager.nextDocumentId(ZAHTEV_COLLECTION);
	}
	
}
