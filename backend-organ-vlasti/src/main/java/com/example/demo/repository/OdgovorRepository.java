package com.example.demo.repository;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class OdgovorRepository {

	@Autowired
	private ExistManager existManager;
	
	public static final String ODGOVORI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/odgovori";
	
	public String save(Document document, String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, TransformerException {
		return this.existManager.save(ODGOVORI_COLLECTION, documentId, document);
	}

	public Document load(String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.load(ODGOVORI_COLLECTION, documentId);
	}
	
	public ResourceSet list(String xpathExp) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.retrieve(ODGOVORI_COLLECTION, xpathExp);
	}
	
}
