package com.example.demo.repository;

import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.database.ExistManager;

@Repository
public class ZahtevRepository {

	@Autowired
	private ExistManager existManager;
	
	private static final String collectionId = Constants.COLLECTIONS_PREFIX + "/zahtevi";
	
	public void save(OutputStream out) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		this.existManager.save(collectionId, null, out);
	}
	
	public XMLResource load(int documentIndex) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.load(collectionId, documentIndex);
	}
	
}
