package com.example.demo.repository.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.exist.ExistManager;

@Repository
public class OrganVlastiExist {

	@Autowired
	private ExistManager existManager;
		
	public static final String ORGAN_VLASTI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/organ_vlasti";

	public Document load() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		return this.existManager.load(ORGAN_VLASTI_COLLECTION, "1");
	}
	
}
