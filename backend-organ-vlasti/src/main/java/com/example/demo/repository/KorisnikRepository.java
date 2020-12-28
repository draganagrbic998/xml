package com.example.demo.repository;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class KorisnikRepository {

	@Autowired
	private ExistManager existManager;
		
	public static final String KORISNICI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/korisnici";
	
	public void save(Document document, String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, TransformerException {
		this.existManager.save(KORISNICI_COLLECTION, documentId, document);
	}
	
	public Document load(String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.load(KORISNICI_COLLECTION, documentId);
	}
		
}
