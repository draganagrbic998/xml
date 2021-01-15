package com.example.demo.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.exist.ExistManager;
import com.example.demo.fuseki.FusekiManager;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.rdf.ResenjeRDF;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.IzvestajExist;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.repository.xml.ResenjeExist;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.ws.utils.SOAPActions;
import com.example.demo.ws.utils.SOAPService;

@Component
public class DataInitializator {

	private static final String POVERENIK1 = Constants.INIT_FOLDER + "poverenik1.xml";
	private static final String GRADJANIN1 = Constants.INIT_FOLDER + "gradjanin1.xml";

	private static final String ZALBA_DELIMICNOST1 = Constants.INIT_FOLDER + "zalba_delimicnost1.xml";
	private static final String ZALBA_ODLUKA1 = Constants.INIT_FOLDER + "zalba_odluka1.xml";
	private static final String ZALBA_CUTANJE1 = Constants.INIT_FOLDER + "zalba_cutanje1.xml";

	private static final String ODGOVOR1 = Constants.INIT_FOLDER + "odgovor1.xml";
	private static final String ODGOVOR2 = Constants.INIT_FOLDER + "odgovor2.xml";

	private static final String RESENJE1 = Constants.INIT_FOLDER + "resenje1.xml";
	private static final String RESENJE2 = Constants.INIT_FOLDER + "resenje2.xml";
	private static final String RESENJE3 = Constants.INIT_FOLDER + "resenje3.xml";

	@Autowired
	private ExistManager existManager;

	@Autowired
	private FusekiManager fusekiManager;

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private XSLTransformer xslTransformer;

	@Autowired
	private SOAPService soap;
	
	@EventListener(ContextRefreshedEvent.class)
	public void dataInit() throws UnsupportedEncodingException, IOException {
		
		this.existManager.dropCollection(KorisnikExist.KORISNIK_COLLECTION);
		this.existManager.dropCollection(ZalbaExist.ZALBA_COLLECTION);
		this.existManager.dropCollection(OdgovorExist.ODGOVOR_COLLECTION);
		this.existManager.dropCollection(ResenjeExist.RESENJE_COLLECTION);
		this.existManager.dropCollection(IzvestajExist.IZVESTAJ_COLLECTION);
		this.fusekiManager.dropAll();
		
		this.existManager.save(KorisnikExist.KORISNIK_COLLECTION, "poverenik.javni.znacaj@gmail.com", this.domParser.buildDocumentFromFile(POVERENIK1), KorisnikExist.KORISNIK_SCHEMA);
		this.existManager.save(KorisnikExist.KORISNIK_COLLECTION, "draganaasd@gmail.com", this.domParser.buildDocumentFromFile(GRADJANIN1), KorisnikExist.KORISNIK_SCHEMA);

		/*
		this.existManager.save(ZalbaExist.ZALBA_COLLECTION, "1", this.domParser.buildDocumentFromFile(ZALBA_DELIMICNOST1), ZalbaExist.ZALBA_SCHEMA);
		this.existManager.save(ZalbaExist.ZALBA_COLLECTION, "2", this.domParser.buildDocumentFromFile(ZALBA_ODLUKA1), ZalbaExist.ZALBA_SCHEMA);
		this.existManager.save(ZalbaExist.ZALBA_COLLECTION, "3", this.domParser.buildDocumentFromFile(ZALBA_CUTANJE1), ZalbaExist.ZALBA_SCHEMA);

		this.existManager.save(OdgovorExist.ODGOVOR_COLLECTION, "1", this.domParser.buildDocumentFromFile(ODGOVOR1), OdgovorExist.ODGOVOR_SCHEMA);
		this.existManager.save(OdgovorExist.ODGOVOR_COLLECTION, "2", this.domParser.buildDocumentFromFile(ODGOVOR2), OdgovorExist.ODGOVOR_SCHEMA);

	    this.existManager.save(ResenjeExist.RESENJE_COLLECTION, "1", this.domParser.buildDocumentFromFile(RESENJE1), ResenjeExist.RESENJE_SCHEMA);
	    this.existManager.save(ResenjeExist.RESENJE_COLLECTION, "2", this.domParser.buildDocumentFromFile(RESENJE2), ResenjeExist.RESENJE_SCHEMA);
	    this.existManager.save(ResenjeExist.RESENJE_COLLECTION, "3", this.domParser.buildDocumentFromFile(RESENJE3), ResenjeExist.RESENJE_SCHEMA);
		
		Model model = ModelFactory.createDefaultModel();
		model.add(this.xslTransformer.generateMetadata(this.domParser.buildDocumentFromFile(ZALBA_DELIMICNOST1)));
		model.add(this.xslTransformer.generateMetadata(this.domParser.buildDocumentFromFile(ZALBA_ODLUKA1)));
		model.add(this.xslTransformer.generateMetadata(this.domParser.buildDocumentFromFile(ZALBA_CUTANJE1)));
		this.fusekiManager.save(ZalbaRDF.ZALBA_GRAPH, model);
		
		model.removeAll();
		model.add(this.xslTransformer.generateMetadata(this.domParser.buildDocumentFromFile(ODGOVOR1)));
		model.add(this.xslTransformer.generateMetadata(this.domParser.buildDocumentFromFile(ODGOVOR2)));
		this.fusekiManager.save(OdgovorRDF.ODGOVOR_GRAPH, model);
		
		model.removeAll();
		model.add(this.xslTransformer.generateMetadata(this.domParser.buildDocumentFromFile(RESENJE1)));
		model.add(this.xslTransformer.generateMetadata(this.domParser.buildDocumentFromFile(RESENJE2)));
		model.add(this.xslTransformer.generateMetadata(this.domParser.buildDocumentFromFile(RESENJE3)));
		this.fusekiManager.save(ResenjeRDF.RESENJE_GRAPH, model);
*/
		/*
		String temp = soap.sendSOAPMessage(this.domParser.buildDocument(String.format("<pretraga><broj>%s</broj></pretraga>", 1)), SOAPDocument.zahtev_pdf);
		byte[] decodedString = Base64.getDecoder().decode(temp);
		Path file = Paths.get(Constants.GEN_FOLDER + "1.pdf");
		Files.write(file, decodedString);*/
	}

}
