package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.constants.Constants;
import com.example.demo.database.ExistManager;
import com.example.demo.model.OrganVlasti;
import com.example.demo.parser.JAXBParser;

@Repository
public class OrganVlastiRepository {

	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	private final String collectionId = Constants.COLLECTIONS_PREFIX + "/organ_vlasti";
	private final String documentId = "1";

	public OrganVlasti load() {
		try {
			return (OrganVlasti) this.jaxbParser.unmarshal(this.existManager.load(this.collectionId, this.documentId), OrganVlasti.class);
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
