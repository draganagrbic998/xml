package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class OdgovorExist {

	@Autowired
	private ExistManager existManager;

	public static final String ODGOVOR_COLLECTION = Constants.COLLECTIONS_PREFIX + "/odgovori";
	public static final String ODGOVOR_SCHEMA = Constants.XSD_FOLDER + "odgovor.xsd";

	public void save(String documentId, Document document) {
		this.existManager.save(ODGOVOR_COLLECTION, documentId, document, ODGOVOR_SCHEMA);
	}
	
	public Document load(String documentId) {
		return this.existManager.load(ODGOVOR_COLLECTION, documentId);
	}


}
