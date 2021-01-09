package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class ZahtevExist {

	@Autowired
	private ExistManager existManager;
			
	public static final String ZAHTEV_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zahtevi";
	public static final String ZAHTEV_SCHEMA = Constants.XSD_FOLDER + "zahtev.xsd";
	
	public void save(String documentId, Document document) {
		this.existManager.save(ZAHTEV_COLLECTION, documentId, document, ZAHTEV_SCHEMA);
	}

	public Document load(String documentId) {
		return this.existManager.load(ZAHTEV_COLLECTION, documentId);
	}
	
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ZAHTEV_COLLECTION, xpathExp);
	}
	
}
