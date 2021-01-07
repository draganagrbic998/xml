package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.constants.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class OdgovorExist {

	@Autowired
	private ExistManager existManager;
	
	public static final String ODGOVORI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/odgovori";
	
	public void save(String documentId, Document document) {
		this.existManager.save(ODGOVORI_COLLECTION, documentId, document);
	}
	
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ODGOVORI_COLLECTION, xpathExp);
	}
	
	public Document load(String documentId) {
		return this.existManager.load(ODGOVORI_COLLECTION, documentId);
	}
	
}
