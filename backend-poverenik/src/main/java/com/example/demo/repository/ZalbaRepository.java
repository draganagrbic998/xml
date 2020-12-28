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
public class ZalbaRepository {

	@Autowired
	private ExistManager existManager;
	
	private static final String ZALBE_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zalbe";
	
	public void save(Document document, String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, TransformerException {
		this.existManager.save(ZALBE_COLLECTION, documentId, document);
	}
	
	public ResourceSet retrieve(String xpathExp) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.retrieve(ZALBE_COLLECTION, xpathExp);
	}
	
	public Document load(String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.load(ZALBE_COLLECTION, documentId);
	}
	
}
