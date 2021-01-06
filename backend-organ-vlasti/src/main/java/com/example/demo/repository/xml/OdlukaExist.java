package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.constants.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class OdlukaExist {

	@Autowired
	private ExistManager existManager;
	
	public static final String ODLUKE_COLLECTION = Constants.COLLECTIONS_PREFIX + "/odluke";
	
	public String save(String documentId, Document document) {
		return this.existManager.save(ODLUKE_COLLECTION, documentId, document);
	}

	public Document load(String documentId) {
		return this.existManager.load(ODLUKE_COLLECTION, documentId);
	}
	
	public ResourceSet list(String xpathExp) {
		return this.existManager.retrieve(ODLUKE_COLLECTION, xpathExp);
	}
	
}
