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
public class OdlukaRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;
	
	@Autowired
	private FusekiAuthentication authUtilities;
	
	public static final String ODLUKA_GRAPH = "/odluke";

	@Override
	public void add(Model model) {
		this.fusekiManager.save(ODLUKA_GRAPH, model, Constants.ODLUKA_SHAPE);
	}

	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ODLUKA_GRAPH, Namespaces.ODLUKA + "/" + subject);
	}

	@Override
	public void update(String graphUri, Model model, String subject) {
		this.fusekiManager.update(graphUri, model, subject, Constants.ODLUKA_SHAPE);
	}

	@Override
	public void delete(String graphUri, String subject) {
		this.fusekiManager.delete(graphUri, subject);
	}
	
	public List<Integer> zalbe(String broj) {
		return this.fusekiManager.search(
				String.format(FusekiManager.readFile(FusekiManager.REFERENCE_QUERY), 
				this.authUtilities.getData() + ZalbaRDF.ZALBA_GRAPH, 
				Namespaces.PREDIKAT + "odluka", Namespaces.ODLUKA + "/" + broj), Namespaces.ZALBA + "/");
	}
	
	public List<Integer> resenja(String broj) {
		return this.fusekiManager.search(
				String.format(FusekiManager.readFile(FusekiManager.REFERENCE_QUERY), 
				this.authUtilities.getData() + ResenjeRDF.RESENJE_GRAPH, 
				Namespaces.PREDIKAT + "odluka", Namespaces.ODLUKA + "/" + broj), Namespaces.RESENJE + "/");
	}
	
}
