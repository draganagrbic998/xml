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
public class OdlukaExist {

	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private SchemaValidator schemaValidator;
	
	public static final String ODLUKA_COLLECTION = Constants.COLLECTIONS_PREFIX + "/odluke";
	public static final String ODLUKA_SCHEMA = Constants.XSD_FOLDER + "odluka.xsd";
	
	public String save(String documentId, Document document) {
		if (documentId == null) {
			documentId = this.existManager.getDocumentId(ODLUKA_COLLECTION);
			document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(documentId);
		}
		this.schemaValidator.validate(document, ODLUKA_SCHEMA);
		return this.existManager.save(ODLUKA_COLLECTION, documentId, document);
	}

	public Document load(String documentId) {
		return this.existManager.load(ODLUKA_COLLECTION, documentId);
	}
	
	public ResourceSet retrieve(String xpathExp) {
		return this.existManager.retrieve(ODLUKA_COLLECTION, xpathExp);
	}
	
}
