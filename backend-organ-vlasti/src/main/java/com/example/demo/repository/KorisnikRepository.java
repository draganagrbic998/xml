package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.constants.Constants;
import com.example.demo.database.ExistManager;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.JAXBParser;

@Repository
public class KorisnikRepository {

	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	public static final String collectionId = Constants.COLLECTIONS_PREFIX + "/korisnici";
	
	public Korisnik findByEmail(String email) {
		try {
			return (Korisnik) this.jaxbParser.unmarshal(this.existManager.load(collectionId, email + ".xml"), Korisnik.class);
		}
		catch(Exception e) {
			return null;
		}
	}
		
}
