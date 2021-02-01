package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class IzvestajExist implements ExistInterface {

	@Autowired
	private ExistManager existManager;

	public static final String IZVESTAJ_COLLECTION = Constants.COLLECTIONS_PREFIX + "/izvestaji";
	public static final String IZVESTAJ_SCHEMA = Constants.XSD_FOLDER + "izvestaj.xsd";

	@Override
	public void add(Document document) {
		this.existManager.save(IZVESTAJ_COLLECTION, null, document, IZVESTAJ_SCHEMA);
	}

	@Override
	public void update(String documentId, Document document) {
		this.existManager.save(IZVESTAJ_COLLECTION, documentId, document, IZVESTAJ_SCHEMA);
	}
	
	@Override
	public void delete(String documentId) {
		this.existManager.delete(IZVESTAJ_COLLECTION, documentId);
	}

	@Override
	public ResourceSet findAll(String xpathExp) {
		return this.existManager.findAll(IZVESTAJ_COLLECTION, xpathExp);
	}

	@Override
	public Document find(String documentId) {
		return this.existManager.find(IZVESTAJ_COLLECTION, documentId);
	}

	@Override
	public String nextDocumentId() {
		return this.existManager.nextDocumentId(IZVESTAJ_COLLECTION);
	}

}
