package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class OrganVlastiExist implements ExistInterface {

	@Autowired
	private ExistManager existManager;
		
	public static final String ORGAN_VLASTI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/organ_vlasti";
	public static final String ORGAN_VLASTI_SCHEMA = Constants.XSD_FOLDER + "osnova.xsd";

	@Override
	public void add(Document document) {
		this.existManager.save(ORGAN_VLASTI_COLLECTION, null, document, ORGAN_VLASTI_SCHEMA);
	}

	@Override
	public void update(String documentId, Document document) {
		this.existManager.save(ORGAN_VLASTI_COLLECTION, documentId, document, ORGAN_VLASTI_SCHEMA);		
	}

	@Override
	public Document load(String documentId) {
		return this.existManager.load(ORGAN_VLASTI_COLLECTION, documentId);
	}

	@Override
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ORGAN_VLASTI_COLLECTION, xpathExp);
	}
	
}
