package com.example.demo.repository.rdf;

import java.util.List;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.fuseki.FusekiAuthentication;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class ZahtevRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	@Autowired
	private FusekiAuthentication authUtilities;

	public static final String ZAHTEV_GRAPH = "/zahtevi";
	public static final String ZAHTEV_SHAPE = Constants.SHAPE_FOLDER + "zahtev.ttl";
	public static final String ZAHTEV_AND_SEARCH = Constants.SPARQL_FOLDER + "zahtev_and.rq";
	public static final String ZAHTEV_OR_SEARCH = Constants.SPARQL_FOLDER + "zahtev_or.rq";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(ZAHTEV_GRAPH, document, ZAHTEV_SHAPE);
	}
	
	@Override
	public void update(String subject, Document document) {
		this.fusekiManager.update(ZAHTEV_GRAPH, Namespaces.ZAHTEV + "/" + subject, document, ZAHTEV_SHAPE);
	}

	@Override
	public void delete(String subject) {
		this.fusekiManager.delete(ZAHTEV_GRAPH, Namespaces.ZAHTEV + "/" + subject);
	}
	
	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ZAHTEV_GRAPH, Namespaces.ZAHTEV + "/" + subject);
	}
	
	public List<Integer> odluke(String broj) {
		return this.fusekiManager.search(
				String.format(Utils.readFile(FusekiManager.REFERENCE_QUERY), 
				this.authUtilities.getData() + OdlukaRDF.ODLUKA_GRAPH, 
				Namespaces.PREDIKAT + "zahtev", Namespaces.ZAHTEV + "/" + broj), Namespaces.ODLUKA + "/");
	}
	
	public List<Integer> zalbe(String broj) {
		return this.fusekiManager.search(
				String.format(Utils.readFile(FusekiManager.REFERENCE_QUERY), 
				this.authUtilities.getData() + ZalbaRDF.ZALBA_GRAPH, 
				Namespaces.PREDIKAT + "zahtev", Namespaces.ZAHTEV + "/" + broj), Namespaces.ZALBA + "/");
	}
	
	public List<Integer> resenja(String broj) {
		return this.fusekiManager.search(
				String.format(Utils.readFile(FusekiManager.REFERENCE_QUERY), 
				this.authUtilities.getData() + ResenjeRDF.RESENJE_GRAPH, 
				Namespaces.PREDIKAT + "zahtev", Namespaces.ZAHTEV + "/" + broj), Namespaces.RESENJE + "/");
	}

}
