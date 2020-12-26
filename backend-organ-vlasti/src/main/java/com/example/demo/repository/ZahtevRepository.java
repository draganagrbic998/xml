package com.example.demo.repository;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.database.ExistManager;

@Repository
public class ZahtevRepository {

	@Autowired
	private ExistManager existManager;
	
	private static final String ZAHTEVI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/zahtevi";
	
	public void save(String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, TransformerException {
		this.existManager.save(ZAHTEVI_COLLECTION, null, xml);
	}
	
	/*
	public XMLResource load(int documentIndex) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.load(collectionId, documentIndex);
	}*/
	
}
