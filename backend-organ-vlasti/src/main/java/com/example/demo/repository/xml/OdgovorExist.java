package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class OdgovorExist implements ExistInterface {

	@Autowired
	private ExistManager existManager;

	public static final String ODGOVOR_COLLECTION = Constants.COLLECTIONS_PREFIX + "/odgovori";
	public static final String ODGOVOR_SCHEMA = Constants.XSD_FOLDER + "odgovor.xsd";
	
	@Override
	public void add(Document document) {
		this.existManager.save(ODGOVOR_COLLECTION, null, document, ODGOVOR_SCHEMA);
	}
	
	@Override
	public void update(String documentId, Document document) {
		this.existManager.save(ODGOVOR_COLLECTION, documentId, document, ODGOVOR_SCHEMA);		
	}
	
	@Override
	public void delete(String documentId) {
		this.existManager.delete(ODGOVOR_COLLECTION, documentId);
	}
		
	@Override
	public ResourceSet findAll(String xpathExp) {
		return this.existManager.findAll(ODGOVOR_COLLECTION, xpathExp);
	}
	
	@Override
	public Document find(String documentId) {
		return this.existManager.find(ODGOVOR_COLLECTION, documentId);
	}

	@Override
	public String nextDocumentId() {
		return this.existManager.nextDocumentId(ODGOVOR_COLLECTION);
	}

}
