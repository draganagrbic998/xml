package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class IzvestajExist {

	@Autowired
	private ExistManager existManager;

	public static final String IZVESTAJI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/izvestaji";
	
	public void save(String documentId, Document document) {
		//this.existManager.save(IZVESTAJI_COLLECTION, documentId, document);
	}
	
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(IZVESTAJI_COLLECTION, xpathExp);
	}
	
	public Document load(String documentId) {
		try {
			return this.existManager.load(IZVESTAJI_COLLECTION, documentId);
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
