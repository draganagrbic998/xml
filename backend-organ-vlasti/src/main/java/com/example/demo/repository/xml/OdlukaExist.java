package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class OdlukaExist implements ExistInterface {

	@Autowired
	private ExistManager existManager;
		
	public static final String ODLUKA_COLLECTION = Constants.COLLECTIONS_PREFIX + "/odluke";
	public static final String ODLUKA_SCHEMA = Constants.XSD_FOLDER + "odluka.xsd";
	
	@Override
	public void add(Document document) {
		this.existManager.save(ODLUKA_COLLECTION, null, document, ODLUKA_SCHEMA);
	}
	
	@Override
	public void update(String documentId, Document document) {
		this.existManager.save(ODLUKA_COLLECTION, documentId, document, ODLUKA_SCHEMA);
	}
	
	@Override
	public void delete(String documentId) {
		this.existManager.delete(ODLUKA_COLLECTION, documentId);
	}
		
	@Override
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ODLUKA_COLLECTION, xpathExp);
	}
	
	@Override
	public Document load(String documentId) {
		return this.existManager.load(ODLUKA_COLLECTION, documentId);
	}

	@Override
	public String nextDocumentId() {
		return this.existManager.nextDocumentId(ODLUKA_COLLECTION);
	}
	
}
