package com.example.demo.repository.rdf;

import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.fuseki.FusekiManager;

@Component
public class KorisnikRDF {

	@Autowired
	private FusekiManager fusekiManager;
	
	public static final String GRAPH_URI = "/korisnici";
	
	public void save(Model model) {
		this.fusekiManager.save(GRAPH_URI, model);
	}
	
}