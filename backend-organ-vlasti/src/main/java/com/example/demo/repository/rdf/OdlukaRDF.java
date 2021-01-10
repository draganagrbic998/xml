package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.Prefixes;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class OdlukaRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;
	
	public static final String ODLUKA_GRAPH = "/odluke";

	@Override
	public void add(Model model) {
		this.fusekiManager.save(ODLUKA_GRAPH, model);
	}

	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ODLUKA_GRAPH, Prefixes.ODLUKA_PREFIX + subject);
	}
	
}
