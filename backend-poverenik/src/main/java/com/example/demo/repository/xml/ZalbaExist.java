package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class ZalbaExist implements ExistInterface {

	@Autowired
	private ExistManager existManager;
	
	public static final String ZALBA_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zalbe";
	public static final String ZALBA_SCHEMA = Constants.XSD_FOLDER + "zalba.xsd";
	
	@Override
	public void add(Document document) {
		this.existManager.save(ZALBA_COLLECTION, null, document, ZALBA_SCHEMA);
	}
	
	@Override
	public void update(String documentId, Document document) {
		this.existManager.save(ZALBA_COLLECTION, documentId, document, ZALBA_SCHEMA);		
	}
	
	@Override
	public void delete(String documentId) {
		this.existManager.delete(ZALBA_COLLECTION, documentId);
	}
	
	@Override
	public Document load(String documentId) {
		return this.existManager.load(ZALBA_COLLECTION, documentId);
	}
	
	@Override
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ZALBA_COLLECTION, xpathExp);
	}
	
	@Override
	public String nextDocumentId() {
		return this.existManager.nextDocumentId(ZALBA_COLLECTION);
	}
		
}
