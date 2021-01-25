package com.example.demo.repository.rdf;

import java.util.List;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class OdlukaRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;
		
	public static final String ODLUKA_GRAPH = "/odluke";
	public static final String ODLUKA_SHAPE = Constants.SHAPE_FOLDER + "odluka.ttl";
	public static final String ODLUKA_AND_SEARCH = Constants.SPARQL_FOLDER + "odluka_and.rq";
	public static final String ODLUKA_OR_SEARCH = Constants.SPARQL_FOLDER + "odluka_or.rq";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(ODLUKA_GRAPH, document, ODLUKA_SHAPE);
	}
	
	@Override
	public void update(String documentId, Document document) {
		this.fusekiManager.update(ODLUKA_GRAPH, Namespaces.ODLUKA + "/" + documentId, document, ODLUKA_SHAPE);
	}

	@Override
	public void delete(String documentId) {
		this.fusekiManager.delete(ODLUKA_GRAPH, Namespaces.ODLUKA + "/" + documentId);
	}
	
	@Override
	public ResultSet retrieve(String documentId) {
		return this.fusekiManager.retrieve(ODLUKA_GRAPH, Namespaces.ODLUKA + "/" + documentId);
	}
	
	public List<String> zalbe(String documentId) {
		return this.fusekiManager.referenceSparql(ZalbaRDF.ZALBA_GRAPH, Namespaces.PREDIKAT + "odluka", Namespaces.ODLUKA + "/" + documentId);
	}
	
	public List<String> resenja(String documentId) {
		return this.fusekiManager.referenceSparql(ResenjeRDF.RESENJE_GRAPH, Namespaces.PREDIKAT + "odluka", Namespaces.ODLUKA + "/" + documentId);
	}
	
}
