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
		
	public static final String ZAHTEVI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zahtevi";
	
	public void save(String documentId, Document document) {
		this.existManager.save(ZAHTEVI_COLLECTION, documentId, document);
	}

	public Document load(String documentId) {
		return this.existManager.load(ZAHTEVI_COLLECTION, documentId);
	}
	
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ZAHTEVI_COLLECTION, xpathExp);
	}
	
}
