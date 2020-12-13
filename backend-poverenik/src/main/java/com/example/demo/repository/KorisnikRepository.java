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
	
	private final String collectionId = Constants.COLLECTIONS_PREFIX + "/korisnici";
	
	public Korisnik findByEmail(String email) {
		try {
			return (Korisnik) this.jaxbParser.unmarshal(this.existManager.load(this.collectionId, email), Korisnik.class);
		}
		catch(Exception e) {
			return null;
		}
	}
		
}
