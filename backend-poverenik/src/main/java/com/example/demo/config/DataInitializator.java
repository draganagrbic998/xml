package com.example.demo.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.constants.Constants;
import com.example.demo.exception.MyException;
import com.example.demo.exist.ExistManager;
import com.example.demo.fuseki.FusekiManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.repository.xml.ResenjeExist;
import com.example.demo.repository.xml.ZalbaExist;

@Component
public class DataInitializator {
	
	private static final String POVERENIK1 = Constants.INIT_FOLDER + File.separatorChar + "poverenik1.xml";
	private static final String GRADJANIN1 = Constants.INIT_FOLDER + File.separatorChar + "gradjanin1.xml";

	@Autowired
	private DOMParser domParser;

	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private FusekiManager fusekiManager;

	@EventListener(ContextRefreshedEvent.class)
	public void dataInit() {
		try {
			this.existManager.dropCollection(KorisnikExist.KORISNICI_COLLECTION);
			this.existManager.dropCollection(ZalbaExist.ZALBE_COLLECTION);
			this.existManager.dropCollection(ResenjeExist.RESENJA_COLLECTION);
			this.existManager.dropCollection(OdgovorExist.ODGOVOR_COLLECTION);
			this.existManager.save(KorisnikExist.KORISNICI_COLLECTION, "poverenik@gmail.com", this.domParser.buildDocumentFromFile(POVERENIK1));
			this.existManager.save(KorisnikExist.KORISNICI_COLLECTION, "draganaasd@gmail.com", this.domParser.buildDocumentFromFile(GRADJANIN1));

			this.fusekiManager.dropAll();
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
