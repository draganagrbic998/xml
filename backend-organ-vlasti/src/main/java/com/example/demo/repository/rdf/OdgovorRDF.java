package com.example.demo.repository.rdf;

import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.fuseki.FusekiManager;

@Component
public class OdgovorRDF {

	@Autowired
	private FusekiManager fusekiManager;

	public static final String GRAPH_URI = "/odgovori";

	public void save(Model model) {
		this.fusekiManager.save(GRAPH_URI, model, null);
	}

}
