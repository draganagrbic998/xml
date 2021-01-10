package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.Prefixes;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class ZahtevRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;
	
	public static final String ZAHTEV_GRAPH = "/zahtevi";

	@Override
	public void add(Model model) {
		this.fusekiManager.save(ZAHTEV_GRAPH, model);
	}

	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ZAHTEV_GRAPH, Prefixes.ZAHTEV_PREFIX + subject);
	}
	
}
