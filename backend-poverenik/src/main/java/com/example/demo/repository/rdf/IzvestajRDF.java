package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.Prefixes;
import com.example.demo.fuseki.FusekiManager;

@Component
public class IzvestajRDF {
	@Autowired
	private FusekiManager fusekiManager;

	public static final String GRAPH_URI = "/izvestaji";

	public void save(Model model) {
		this.fusekiManager.save(GRAPH_URI, model);
	}

	public ResultSet retrieve(String broj) {
		return this.fusekiManager.retrieve(GRAPH_URI, Prefixes.IZVESTAJ_PREFIX + broj);
	}
}
