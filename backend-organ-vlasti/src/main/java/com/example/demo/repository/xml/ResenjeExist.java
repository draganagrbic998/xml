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
	public void delete(String documentId) {
		this.existManager.delete(RESENJE_COLLECTION, documentId);
	}
		
	@Override
	public ResourceSet findAll(String xpathExp) {
		return this.existManager.findAll(RESENJE_COLLECTION, xpathExp);
	}
	
	@Override
	public Document find(String documentId) {
		return this.existManager.find(RESENJE_COLLECTION, documentId);
	}

	@Override
	public String nextDocumentId() {
		return this.existManager.nextDocumentId(RESENJE_COLLECTION);
	}
	
}
