package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class ResenjeRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	public static final String RESENJE_GRAPH = "/resenja";
	public static final String RESENJE_SHAPE = Constants.SHAPE_FOLDER + "resenje.ttl";
	public static final String RESENJE_AND_SEARCH = Constants.SPARQL_FOLDER + "resenje_and.rq";
	public static final String RESENJE_OR_SEARCH = Constants.SPARQL_FOLDER + "resenje_or.rq";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(RESENJE_GRAPH, document, RESENJE_SHAPE);
	}

	@Override
	public void update(String documentId, Document document) {
		this.fusekiManager.update(RESENJE_GRAPH, Namespaces.RESENJE + "/" + documentId, document, RESENJE_SHAPE);
	}

	@Override
	public void delete(String documentId) {
		this.fusekiManager.delete(RESENJE_GRAPH, Namespaces.RESENJE + "/" + documentId);
	}

	@Override
	public ResultSet retrieve(String documentId) {
		return this.fusekiManager.retrieve(RESENJE_GRAPH, Namespaces.RESENJE + "/" + documentId);
	}

}
