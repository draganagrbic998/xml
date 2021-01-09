package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class ResenjeExist {

	@Autowired
	private ExistManager existManager;
	
	public static final String RESENJE_COLLECTION = Constants.COLLECTIONS_PREFIX + "/resenja";
	public static final String RESENJE_SCHEMA = Constants.XSD_FOLDER + "resenje.xsd";
	
	public void save(String documentId, Document document) {
		this.existManager.save(RESENJE_COLLECTION, documentId, document, RESENJE_SCHEMA);
	}
	
}
