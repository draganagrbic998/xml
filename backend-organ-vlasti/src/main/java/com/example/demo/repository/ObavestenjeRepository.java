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
public class ObavestenjeRepository {

	@Autowired
	private ExistManager existManager;
	
	private static final String OBAVESTENJA_COLLECTION = Constants.COLLECTIONS_PREFIX + "/obavestenja";
	
	public void save(Document document) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, TransformerException {
		this.existManager.save(OBAVESTENJA_COLLECTION, null, document);
	}

	
}
