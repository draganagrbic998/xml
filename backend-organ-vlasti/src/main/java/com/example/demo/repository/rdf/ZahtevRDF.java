package com.example.demo.repository.rdf;

import java.util.List;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class ZahtevRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	public static final String ZAHTEV_GRAPH = "/zahtevi";
	public static final String ZAHTEV_SHAPE = Constants.SHAPE_FOLDER + "zahtev.ttl";
	public static final String ZAHTEV_AND_SEARCH = Constants.SPARQL_FOLDER + "zahtev_and.rq";
	public static final String ZAHTEV_OR_SEARCH = Constants.SPARQL_FOLDER + "zahtev_or.rq";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(ZAHTEV_GRAPH, document, ZAHTEV_SHAPE);
	}
	
	@Override
	public void update(String documentId, Document document) {
		this.fusekiManager.update(ZAHTEV_GRAPH, Namespaces.ZAHTEV + "/" + documentId, document, ZAHTEV_SHAPE);
	}

	@Override
	public void delete(String documentId) {
		this.fusekiManager.delete(ZAHTEV_GRAPH, Namespaces.ZAHTEV + "/" + documentId);
	}
	
	@Override
	public ResultSet findAll(String documentId) {
		return this.fusekiManager.findAll(ZAHTEV_GRAPH, Namespaces.ZAHTEV + "/" + documentId);
	}
	
	@Override
	public String search(String xml) {
		return Utils.getReferences(this.fusekiManager.search(ZAHTEV_GRAPH, xml));
	}

	public List<String> odluke(String documentId) {
		return this.fusekiManager.references(OdlukaRDF.ODLUKA_GRAPH, Namespaces.PREDIKAT + "zahtev", Namespaces.ZAHTEV + "/" + documentId);
	}
	
	public List<String> zalbe(String documentId) {
		return this.fusekiManager.references(ZalbaRDF.ZALBA_GRAPH, Namespaces.PREDIKAT + "zahtev", Namespaces.ZAHTEV + "/" + documentId);
	}
	
	public List<String> resenja(String documentId) {
		return this.fusekiManager.references(ResenjeRDF.RESENJE_GRAPH, Namespaces.PREDIKAT + "zahtev", Namespaces.ZAHTEV + "/" + documentId);
	}
	
}
