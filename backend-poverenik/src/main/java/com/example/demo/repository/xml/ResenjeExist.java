package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class ResenjeExist implements ExistInterface {

	@Autowired
	private ExistManager existManager;
	
	public static final String RESENJE_COLLECTION = Constants.COLLECTIONS_PREFIX + "/resenja";
	public static final String RESENJE_SCHEMA = Constants.XSD_FOLDER + "resenje.xsd";
	
	@Override
	public void add(Document document) {
		this.existManager.save(RESENJE_COLLECTION, null, document, RESENJE_SCHEMA);
	}
	
	@Override
	public void update(String documentId, Document document) {
		this.existManager.save(RESENJE_COLLECTION, documentId, document, RESENJE_SCHEMA);
	}
	
	@Override
	public Document load(String documentId) {
		return this.existManager.load(RESENJE_COLLECTION, documentId);
	}
	
	@Override
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(RESENJE_COLLECTION, xpathExp);
	}
	
}
