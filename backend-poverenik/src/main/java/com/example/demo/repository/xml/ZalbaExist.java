package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class ZalbaExist {

	@Autowired
	private ExistManager existManager;
	
	public static final String ZALBE_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zalbe";
	
	public void save(String documentId, Document document) {
		this.existManager.save(ZALBE_COLLECTION, documentId, document);
	}
	
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ZALBE_COLLECTION, xpathExp);
	}
	
	public Document load(String documentId) {
		return this.existManager.load(ZALBE_COLLECTION, documentId);
	}
	
}
