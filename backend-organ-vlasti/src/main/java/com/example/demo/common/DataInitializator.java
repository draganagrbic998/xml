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
import com.example.demo.repository.rdf.OdlukaRDF;
import com.example.demo.repository.rdf.ZahtevRDF;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.OdgovorExist;
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
	private static final String ZAHTEVI = Constants.INIT_FOLDER + "zahtevi.nt";
	
	private static final String OBAVESTENJE1 = Constants.INIT_FOLDER + "obavestenje1.xml";
	private static final String ODBIJANJE1 = Constants.INIT_FOLDER + "odbijanje1.xml";
	private static final String ODLUKE = Constants.INIT_FOLDER + "odluke.nt";
	
	private static final String ZALBA_DELIMICNOST1 = Constants.INIT_FOLDER + "zalba_delimicnost1.xml";
	private static final String ZALBA_ODLUKA1 = Constants.INIT_FOLDER + "zalba_odluka1.xml";
	private static final String ZALBA_CUTANJE1 = Constants.INIT_FOLDER + "zalba_cutanje1.xml";
	private static final String ZALBE = Constants.INIT_FOLDER + "zalbe.nt";
	
	private static final String RESENJE1 = Constants.INIT_FOLDER + "resenje1.xml";
	private static final String RESENJE2 = Constants.INIT_FOLDER + "resenje2.xml";
	private static final String RESENJE3 = Constants.INIT_FOLDER + "resenje3.xml";
	//private static final String RESENJA = Constants.INIT_FOLDER + "resenja.nt";

	private static final String ODGOVOR1 = Constants.INIT_FOLDER + "odgovor1.xml";
	private static final String ODGOVOR2 = Constants.INIT_FOLDER + "odgovor2.xml";
	private static final String ODGOVORI = Constants.INIT_FOLDER + "odgovori.nt";

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
		this.existManager.dropCollection(ResenjeExist.RESENJE_COLLECTION);
		this.existManager.dropCollection(OdgovorExist.ODGOVOR_COLLECTION);
		
		
		this.existManager.save(OrganVlastiExist.ORGAN_VLASTI_COLLECTION, "1", this.domParser.buildDocumentFromFile(ORGAN_VLASTI1), KorisnikExist.KORISNIK_SCHEMA);
		this.existManager.save(KorisnikExist.KORISNIK_COLLECTION, "sluzbenik@gmail.com", this.domParser.buildDocumentFromFile(SLUZBENIK1), KorisnikExist.KORISNIK_SCHEMA);
		this.existManager.save(KorisnikExist.KORISNIK_COLLECTION, "draganaasd@gmail.com", this.domParser.buildDocumentFromFile(GRADJANIN1), KorisnikExist.KORISNIK_SCHEMA);
		
		this.existManager.save(ZahtevExist.ZAHTEV_COLLECTION, "1", this.domParser.buildDocumentFromFile(ZAHTEV_UVID1), ZahtevExist.ZAHTEV_SCHEMA);
		this.existManager.save(ZahtevExist.ZAHTEV_COLLECTION, "2", this.domParser.buildDocumentFromFile(ZAHTEV_KOPIJA1), ZahtevExist.ZAHTEV_SCHEMA);
		this.existManager.save(ZahtevExist.ZAHTEV_COLLECTION, "3", this.domParser.buildDocumentFromFile(ZAHTEV_OBAVESTENJE1), ZahtevExist.ZAHTEV_SCHEMA);
		
		this.existManager.save(OdlukaExist.ODLUKA_COLLECTION, "1", this.domParser.buildDocumentFromFile(OBAVESTENJE1), OdlukaExist.ODLUKA_SCHEMA);
		this.existManager.save(OdlukaExist.ODLUKA_COLLECTION, "2", this.domParser.buildDocumentFromFile(ODBIJANJE1), OdlukaExist.ODLUKA_SCHEMA);
		
		this.existManager.save(ZalbaExist.ZALBA_COLLECTION, "1", this.domParser.buildDocumentFromFile(ZALBA_DELIMICNOST1), ZalbaExist.ZALBA_SCHEMA);
		this.existManager.save(ZalbaExist.ZALBA_COLLECTION, "2", this.domParser.buildDocumentFromFile(ZALBA_ODLUKA1), ZalbaExist.ZALBA_SCHEMA);
		this.existManager.save(ZalbaExist.ZALBA_COLLECTION, "3", this.domParser.buildDocumentFromFile(ZALBA_CUTANJE1), ZalbaExist.ZALBA_SCHEMA);

		this.existManager.save(ResenjeExist.RESENJE_COLLECTION, "1", this.domParser.buildDocumentFromFile(RESENJE1), ResenjeExist.RESENJE_SCHEMA);
		this.existManager.save(ResenjeExist.RESENJE_COLLECTION, "2", this.domParser.buildDocumentFromFile(RESENJE2), ResenjeExist.RESENJE_SCHEMA);
		this.existManager.save(ResenjeExist.RESENJE_COLLECTION, "3", this.domParser.buildDocumentFromFile(RESENJE3), ResenjeExist.RESENJE_SCHEMA);

		this.existManager.save(OdgovorExist.ODGOVOR_COLLECTION, "1", this.domParser.buildDocumentFromFile(ODGOVOR1), OdgovorExist.ODGOVOR_SCHEMA);
		this.existManager.save(OdgovorExist.ODGOVOR_COLLECTION, "2", this.domParser.buildDocumentFromFile(ODGOVOR2), OdgovorExist.ODGOVOR_SCHEMA);

		try {
			this.fusekiManager.dropAll();
		}
		catch(Exception e) {
			;
		}
		Model model = ModelFactory.createDefaultModel();
		model.read(ZAHTEVI);
		this.fusekiManager.save(ZahtevRDF.ZAHTEV_GRAPH, model);
		model.removeAll();
		model.read(ODLUKE);
		this.fusekiManager.save(OdlukaRDF.ODLUKA_GRAPH, model);
		model.removeAll();
		model.read(ZALBE);
		this.fusekiManager.save(ZalbaRDF.ZALBA_GRAPH, model);
		model.removeAll();
		model.read(ODGOVORI);
		this.fusekiManager.save(OdgovorRDF.ODGOVOR_GRAPH, model);
		//dodaj rdfs za resenje
		
	}
	
}
