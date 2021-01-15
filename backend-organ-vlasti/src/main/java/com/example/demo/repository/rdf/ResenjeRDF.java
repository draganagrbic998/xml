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

	@Override
	public void add(Document document) {
		this.fusekiManager.add(RESENJE_GRAPH, document, RESENJE_SHAPE);
	}
	
	@Override
	public void update(String subject, Document document) {
		this.fusekiManager.update(RESENJE_GRAPH, Namespaces.RESENJE + "/" + subject, document, RESENJE_SHAPE);
	}

	@Override
	public void delete(String subject) {
		this.fusekiManager.delete(RESENJE_GRAPH, Namespaces.RESENJE + "/" + subject);
	}
	
	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(RESENJE_GRAPH, Namespaces.RESENJE + "/" + subject);
	}

}
