package com.example.demo.repository;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

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
	
	public static final String KORISNICI_COLLECTION = Constants.COLLECTIONS_PREFIX + "/korisnici";
	
	public Korisnik findByEmail(String email) {
		try {
			return (Korisnik) this.jaxbParser.unmarshal(this.existManager.load(KORISNICI_COLLECTION, email), Korisnik.class);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public void save(Korisnik korisnik) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException {
		this.existManager.save(KORISNICI_COLLECTION, korisnik.getEmail(), this.jaxbParser.marshal(korisnik, Korisnik.class));
	}
		
}
