package com.example.demo.common;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.exist.ExistManager;
import com.example.demo.fuseki.FusekiManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.repository.xml.ResenjeExist;
import com.example.demo.repository.xml.ZalbaExist;

@Component
public class DataInitializator {
	
	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private FusekiManager fusekiManager;
	
	@Autowired
	private DOMParser domParser;
	
	private static final String POVERENIK1 = Constants.INIT_FOLDER + "poverenik1.xml";
	private static final String GRADJANIN1 = Constants.INIT_FOLDER + "gradjanin1.xml";
	private static final String ZALBA_DELIMICNOST1 = Constants.INIT_FOLDER + "zalba_delimicnost1.xml";
	private static final String ZALBA_ODLUKA1 = Constants.INIT_FOLDER + "zalba_odluka1.xml";
	private static final String ZALBA_CUTANJE1 = Constants.INIT_FOLDER + "zalba_cutanje1.xml";
	private static final String ZALBE = Constants.INIT_FOLDER + "zalbe.nt";
	private static final String ODGOVOR1 = Constants.INIT_FOLDER + "odgovor1.xml";
	private static final String ODGOVOR2 = Constants.INIT_FOLDER + "odgovor2.xml";
	private static final String ODGOVORI = Constants.INIT_FOLDER + "odgovori.nt";
	private static final String RESENJA = Constants.INIT_FOLDER + "resenja.nt";
	private static final String RESENJE1 = Constants.INIT_FOLDER + "resenje1.xml";
	private static final String RESENJE2 = Constants.INIT_FOLDER + "resenje2.xml";
	private static final String RESENJE3 = Constants.INIT_FOLDER + "resenje3.xml";

	@EventListener(ContextRefreshedEvent.class)
	public void dataInit() {
		this.existManager.dropCollection(KorisnikExist.KORISNIK_COLLECTION);
		this.existManager.dropCollection(ZalbaExist.ZALBA_COLLECTION);
		this.existManager.dropCollection(ResenjeExist.RESENJE_COLLECTION);
		this.existManager.dropCollection(OdgovorExist.ODGOVOR_COLLECTION);
		
		
		this.existManager.save(KorisnikExist.KORISNIK_COLLECTION, "poverenik@gmail.com", this.domParser.buildDocumentFromFile(POVERENIK1), KorisnikExist.KORISNIK_SCHEMA);
		this.existManager.save(KorisnikExist.KORISNIK_COLLECTION, "draganaasd@gmail.com", this.domParser.buildDocumentFromFile(GRADJANIN1), KorisnikExist.KORISNIK_SCHEMA);

		this.existManager.save(ZalbaExist.ZALBA_COLLECTION, "1", this.domParser.buildDocumentFromFile(ZALBA_DELIMICNOST1), ZalbaExist.ZALBA_SCHEMA);
		this.existManager.save(ZalbaExist.ZALBA_COLLECTION, "2", this.domParser.buildDocumentFromFile(ZALBA_ODLUKA1), ZalbaExist.ZALBA_SCHEMA);
		this.existManager.save(ZalbaExist.ZALBA_COLLECTION, "3", this.domParser.buildDocumentFromFile(ZALBA_CUTANJE1), ZalbaExist.ZALBA_SCHEMA);

		this.existManager.save(OdgovorExist.ODGOVOR_COLLECTION, "1", this.domParser.buildDocumentFromFile(ODGOVOR1), OdgovorExist.ODGOVOR_SCHEMA);
		this.existManager.save(OdgovorExist.ODGOVOR_COLLECTION, "2", this.domParser.buildDocumentFromFile(ODGOVOR2), OdgovorExist.ODGOVOR_SCHEMA);

		this.existManager.save(ResenjeExist.RESENJE_COLLECTION, "1", this.domParser.buildDocumentFromFile(RESENJE1), ResenjeExist.RESENJE_SCHEMA);
		this.existManager.save(ResenjeExist.RESENJE_COLLECTION, "2", this.domParser.buildDocumentFromFile(RESENJE2), ResenjeExist.RESENJE_SCHEMA);
		this.existManager.save(ResenjeExist.RESENJE_COLLECTION, "3", this.domParser.buildDocumentFromFile(RESENJE3), ResenjeExist.RESENJE_SCHEMA);

		this.fusekiManager.dropAll();
		Model model = ModelFactory.createDefaultModel();
		model.read(ZALBE);
		this.fusekiManager.save(ZalbaRDF.GRAPH_URI, model);
		model.removeAll();
		model.read(ODGOVORI);
		this.fusekiManager.save(OdgovorRDF.GRAPH_URI, model);
		//dodaj rdfs za resenja

	}
	
}