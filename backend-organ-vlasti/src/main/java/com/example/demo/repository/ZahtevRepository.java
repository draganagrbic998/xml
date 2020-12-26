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
public class ZahtevRepository {

	@Autowired
	private ExistManager existManager;
	
	private static final String ZAHTEVI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zahtevi";
	
	public void save(Document document) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, TransformerException {
		this.existManager.save(ZAHTEVI_COLLECTION, null, document);
	}
	
	public ResourceSet list(String xpathExp) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.retrieve(ZAHTEVI_COLLECTION, xpathExp);
	}
	
	public Document load(String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.load(ZAHTEVI_COLLECTION, documentId);
	}
	
	/*
	public XMLResource load(int documentIndex) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.load(collectionId, documentIndex);
	}*/
	
}
