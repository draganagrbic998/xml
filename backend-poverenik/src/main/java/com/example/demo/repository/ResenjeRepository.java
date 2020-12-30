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
public class ResenjeRepository {

	@Autowired
	private ExistManager existManager;
	
	public static final String RESENJA_COLLECTION = Constants.COLLECTIONS_PREFIX + "/resenja";
	
	public void save(Document document, String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, TransformerException {
		this.existManager.save(RESENJA_COLLECTION, documentId, document);
	}
	
	public ResourceSet list(String xpathExp) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.retrieve(RESENJA_COLLECTION, xpathExp);
	}
	
	public Document load(String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.load(RESENJA_COLLECTION, documentId);
	}
	
}
