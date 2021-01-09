package com.example.demo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.exist.ExistManager;
import com.example.demo.fuseki.FusekiManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.repository.xml.OrganVlastiExist;
import com.example.demo.repository.xml.ResenjeExist;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.repository.xml.ZalbaExist;

@Component
public class DataInitializator {
	
	private static final String ORGAN_VLASTI1 = Constants.INIT_FOLDER + "organ_vlasti1.xml";
	private static final String SLUZBENIK1 = Constants.INIT_FOLDER + "sluzbenik1.xml";
	private static final String GRADJANIN1 = Constants.INIT_FOLDER + "gradjanin1.xml";
	private static final String ZAHTEV_UVID1 = Constants.INIT_FOLDER + "zahtev_uvid1.xml";
	private static final String ZAHTEV_KOPIJA1 = Constants.INIT_FOLDER + "zahtev_kopija1.xml";
	private static final String ZAHTEV_OBAVESTENJE1 = Constants.INIT_FOLDER + "zahtev_obavestenje1.xml";
	private static final String OBAVESTENJE1 = Constants.INIT_FOLDER + "obavestenje1.xml";
	private static final String ODBIJANJE1 = Constants.INIT_FOLDER + "odbijanje1.xml";

	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private FusekiManager fusekiManager;
	
	@Autowired
	private DOMParser domParser;

	@EventListener(ContextRefreshedEvent.class)
	public void dataInit() {
		this.existManager.dropCollection(OrganVlastiExist.ORGAN_VLASTI_COLLECTION);
		this.existManager.dropCollection(KorisnikExist.KORISNIK_COLLECTION);
		this.existManager.dropCollection(ZahtevExist.ZAHTEV_COLLECTION);
		this.existManager.dropCollection(OdlukaExist.ODLUKA_COLLECTION);
		this.existManager.dropCollection(ZalbaExist.ZALBA_COLLECTION);
		this.existManager.dropCollection(ResenjeExist.RESENJA_COLLECTION);
		
		this.existManager.save(OrganVlastiExist.ORGAN_VLASTI_COLLECTION, "1", this.domParser.buildDocumentFromFile(ORGAN_VLASTI1));
		
		this.existManager.save(KorisnikExist.KORISNIK_COLLECTION, "sluzbenik@gmail.com", this.domParser.buildDocumentFromFile(SLUZBENIK1));
		this.existManager.save(KorisnikExist.KORISNIK_COLLECTION, "draganaasd@gmail.com", this.domParser.buildDocumentFromFile(GRADJANIN1));
		
		this.existManager.save(ZahtevExist.ZAHTEV_COLLECTION, "1", this.domParser.buildDocumentFromFile(ZAHTEV_UVID1));
		this.existManager.save(ZahtevExist.ZAHTEV_COLLECTION, "2", this.domParser.buildDocumentFromFile(ZAHTEV_KOPIJA1));
		this.existManager.save(ZahtevExist.ZAHTEV_COLLECTION, "3", this.domParser.buildDocumentFromFile(ZAHTEV_OBAVESTENJE1));
		this.existManager.save(OdlukaExist.ODLUKA_COLLECTION, "1", this.domParser.buildDocumentFromFile(OBAVESTENJE1));
		this.existManager.save(OdlukaExist.ODLUKA_COLLECTION, "2", this.domParser.buildDocumentFromFile(ODBIJANJE1));

		
		this.fusekiManager.dropAll();
	}
	
}
