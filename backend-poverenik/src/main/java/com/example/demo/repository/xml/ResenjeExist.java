package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.constants.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class ResenjeExist {

	@Autowired
	private ExistManager existManager;
	
	public static final String RESENJA_COLLECTION = Constants.COLLECTIONS_PREFIX + "/resenja";
	
	public void save(String documentId, Document document) {
		this.existManager.save(RESENJA_COLLECTION, documentId, document);
	}
	
	public ResourceSet list(String xpathExp) {
		return this.existManager.retrieve(RESENJA_COLLECTION, xpathExp);
	}
	
	public Document load(String documentId) {
		return this.existManager.load(RESENJA_COLLECTION, documentId);
	}
	
}
