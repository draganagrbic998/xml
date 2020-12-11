package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.database.ExistManager;

@Repository
public class ZahtevRepository {

	@Autowired
	private ExistManager existManager;
	
	private final String collectionId = Constants.COLLECTIONS_PREFIX + "/zahtevi";
	
	public void save(String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		int documentId = this.existManager.collectionSize(this.collectionId) + 1;
		System.out.println(xml);
		System.out.println(documentId);
		this.existManager.save(this.collectionId, documentId + ".xml", xml);
	}
	
}
