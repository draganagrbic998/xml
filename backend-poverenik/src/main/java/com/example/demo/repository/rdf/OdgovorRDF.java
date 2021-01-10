package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.Prefixes;
import com.example.demo.fuseki.FusekiManager;

@Component
public class OdgovorRDF {

	@Autowired
	private FusekiManager fusekiManager;

	public static final String ODGOVOR_GRAPH = "/odgovori";

	public void save(Model model) {
		this.fusekiManager.save(ODGOVOR_GRAPH, model);
	}

	public ResultSet retrieve(String broj) {
		return this.fusekiManager.retrieve(ODGOVOR_GRAPH, Prefixes.ODGOVOR_PREFIX + broj);
	}

}
