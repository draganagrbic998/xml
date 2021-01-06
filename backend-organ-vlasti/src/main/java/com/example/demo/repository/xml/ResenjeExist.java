package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.constants.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class ResenjeExist {

	@Autowired
	private ExistManager existManager;
	
	public static final String RESENJA_COLLECTION = Constants.COLLECTIONS_PREFIX + "/resenja";
	
	public void save(String documentId, Document document) {
		this.existManager.save(RESENJA_COLLECTION, documentId, document);
	}
	
}
