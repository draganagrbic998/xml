package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class IzvestajRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	public static final String IZVESTAJ_GRAPH = "/izvestaji";
	public static final String IZVESTAJ_SHAPE = Constants.SHAPE_FOLDER + "izvestaj.ttl";
	public static final String IZVESTAJ_AND_SEARCH = Constants.SPARQL_FOLDER + "izvestaj_and.rq";
	public static final String IZVESTAJ_OR_SEARCH = Constants.SPARQL_FOLDER + "izvestaj_or.rq";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(IZVESTAJ_GRAPH, document, IZVESTAJ_SHAPE);
	}

	@Override
	public void update(String documentId, Document document) {
		this.fusekiManager.update(IZVESTAJ_GRAPH, Namespaces.IZVESTAJ + "/" + documentId, document, IZVESTAJ_SHAPE);
	}

	@Override
	public void delete(String documentId) {
		this.fusekiManager.delete(IZVESTAJ_GRAPH, Namespaces.IZVESTAJ + "/" + documentId);
	}

	@Override
	public ResultSet retrieve(String documentId) {
		return this.fusekiManager.retrieve(IZVESTAJ_GRAPH, Namespaces.IZVESTAJ + "/" + documentId);
	}
	
	@Override
	public String search(String xml) {
		return Utils.getReferences(this.fusekiManager.search(IZVESTAJ_GRAPH, xml));
	}


}
