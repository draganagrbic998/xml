package com.example.demo.repository.rdf;

import java.util.List;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.fuseki.FusekiAuthentication;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class ZalbaRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;
	
	@Autowired
	private FusekiAuthentication authUtilities;

	public static final String ZALBA_GRAPH = "/zalbe";

	@Override
	public void add(Model model) {
		this.fusekiManager.save(ZALBA_GRAPH, model, Constants.ZALBA_SHAPE);
	}

	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ZALBA_GRAPH, Namespaces.ZALBA + "/" + subject);
	}

	@Override
	public void update(String graphUri, Model model, String subject) {
		this.fusekiManager.update(graphUri, model, subject, Constants.ZALBA_SHAPE);
	}

	@Override
	public void delete(String graphUri, String subject) {
		this.fusekiManager.delete(graphUri, subject);
	}
	
	public List<Integer> odgovori(String broj) {
		return this.fusekiManager.search(
				String.format(FusekiManager.readFile(FusekiManager.REFERENCE_QUERY), 
				this.authUtilities.getData() + OdgovorRDF.ODGOVOR_GRAPH, 
				Namespaces.PREDIKAT + "zalba", Namespaces.ZALBA + "/" + broj), Namespaces.ODGOVOR + "/");
	}
	
	public List<Integer> resenja(String broj) {
		return this.fusekiManager.search(
				String.format(FusekiManager.readFile(FusekiManager.REFERENCE_QUERY), 
				this.authUtilities.getData() + ResenjeRDF.RESENJE_GRAPH, 
				Namespaces.PREDIKAT + "zalba", Namespaces.ZALBA + "/" + broj), Namespaces.RESENJE + "/");
	}

}
