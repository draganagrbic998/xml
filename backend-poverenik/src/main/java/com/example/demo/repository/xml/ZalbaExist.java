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

	public static final String ZALBA_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zalbe";
	public static final String ZALBA_SCHEMA = Constants.XSD_FOLDER + "zalba.xsd";
	
	public void save(String documentId, Document document) {
		this.existManager.save(ZALBA_COLLECTION, documentId, document, ZALBA_SCHEMA);
	}
	
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ZALBA_COLLECTION, xpathExp);
	}
	
	public Document load(String documentId) {
		return this.existManager.load(ZALBA_COLLECTION, documentId);
	}
	
}
