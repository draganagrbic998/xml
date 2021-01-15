package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Namespaces;
import com.example.demo.fuseki.FusekiManager;
import com.example.demo.model.Pretraga;

@Repository
public class IzvestajRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	public static final String IZVESTAJ_GRAPH = "/izvestaji";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(IZVESTAJ_GRAPH, document);
	}

	@Override
	public void update(String subject, Document document) {
		this.fusekiManager.update(IZVESTAJ_GRAPH, Namespaces.IZVESTAJ + "/" + subject, document);
	}

	@Override
	public void delete(String subject) {
		this.fusekiManager.delete(IZVESTAJ_GRAPH, Namespaces.IZVESTAJ + "/" + subject);
	}

	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(IZVESTAJ_GRAPH, Namespaces.IZVESTAJ + "/" + subject);
	}

	@Override
	public String search(Pretraga pretraga) {
		// TODO Auto-generated method stub
		return null;
	}

}
