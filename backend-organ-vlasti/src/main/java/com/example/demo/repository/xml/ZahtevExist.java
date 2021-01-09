package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.exist.ExistManager;
import com.example.demo.parser.SchemaValidator;

@Repository
public class ZahtevExist {

	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private SchemaValidator schemaValidator;
		
	public static final String ZAHTEV_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zahtevi";
	public static final String ZAHTEV_SCHEMA = Constants.XSD_FOLDER + "zahtev.xsd";
	
	public void save(String documentId, Document document) {
		if (documentId == null) {
			documentId = this.existManager.getDocumentId(ZAHTEV_COLLECTION);
			document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(documentId);			
		}
		this.schemaValidator.validate(document, ZAHTEV_SCHEMA);
		this.existManager.save(ZAHTEV_COLLECTION, documentId, document);
	}

	public Document load(String documentId) {
		return this.existManager.load(ZAHTEV_COLLECTION, documentId);
	}
	
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ZAHTEV_COLLECTION, xpathExp);
	}
	
}
