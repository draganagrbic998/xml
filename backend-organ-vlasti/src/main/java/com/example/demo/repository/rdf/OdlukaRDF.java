package com.example.demo.repository.rdf;

import java.io.IOException;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.fuseki.FusekiManager;
import com.example.demo.fuseki.Prefixes;

@Component
public class OdlukaRDF {

	@Autowired
	private FusekiManager fusekiManager;
	
	public static final String GRAPH_URI = "/odluke";
	
	public void save(Model model) {
		this.fusekiManager.save(GRAPH_URI, model);
	}
	
	public ResultSet retrieve(String broj) throws IOException {
		return this.fusekiManager.retrieve(GRAPH_URI, Prefixes.ODLUKA_PREFIX + broj);
	}
	
}