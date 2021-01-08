package com.example.demo.common;

import java.io.File;

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
	
	private static final String ORGAN_VLASTI1 = Constants.INIT_FOLDER + File.separatorChar + "organ_vlasti1.xml";
	private static final String SLUZBENIK1 = Constants.INIT_FOLDER + File.separatorChar + "sluzbenik1.xml";
	private static final String GRADJANIN1 = Constants.INIT_FOLDER + File.separatorChar + "gradjanin1.xml";

	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private FusekiManager fusekiManager;
	
	@Autowired
	private DOMParser domParser;

	@EventListener(ContextRefreshedEvent.class)
	public void dataInit() {
		this.existManager.dropCollection(OrganVlastiExist.ORGAN_VLASTI_COLLECTION);
		this.existManager.dropCollection(KorisnikExist.KORISNICI_COLLECTION);
		this.existManager.dropCollection(ZahtevExist.ZAHTEVI_COLLECTION);
		this.existManager.dropCollection(OdlukaExist.ODLUKE_COLLECTION);
		this.existManager.dropCollection(ZalbaExist.ZALBE_COLLECTION);
		this.existManager.dropCollection(ResenjeExist.RESENJA_COLLECTION);
		this.existManager.save(OrganVlastiExist.ORGAN_VLASTI_COLLECTION, "1", this.domParser.buildDocumentFromFile(ORGAN_VLASTI1));
		this.existManager.save(KorisnikExist.KORISNICI_COLLECTION, "sluzbenik@gmail.com", this.domParser.buildDocumentFromFile(SLUZBENIK1));
		this.existManager.save(KorisnikExist.KORISNICI_COLLECTION, "draganaasd@gmail.com", this.domParser.buildDocumentFromFile(GRADJANIN1));
		
		this.fusekiManager.dropAll();
	}
	
}
