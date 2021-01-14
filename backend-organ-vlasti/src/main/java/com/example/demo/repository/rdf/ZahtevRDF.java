package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.fuseki.FusekiAuthentication;
import com.example.demo.fuseki.FusekiManager;
import com.example.demo.model.ZahtevSearch;

@Repository
public class ZahtevRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	@Autowired
	private FusekiAuthentication authUtilities;

	public static final String ZAHTEV_GRAPH = "/zahtevi";
	private static final String ZAHTEV_SEARCH = Constants.SPARQL_FOLDER + "zahtev.rq";

	@Override
	public void add(Model model) {
		this.fusekiManager.save(ZAHTEV_GRAPH, model);
	}

	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ZAHTEV_GRAPH, Namespaces.ZAHTEV + "/" + subject);
	}

	@Override
	public void update(String graphUri, Model model, String subject) {
		this.fusekiManager.update(graphUri, model, subject);
	}

	@Override
	public void delete(String graphUri, String subject) {
		this.fusekiManager.delete(graphUri, subject);
	}

	// dodaj interfejs za pretragu
	public String search(ZahtevSearch search) {
		return this.fusekiManager.search(String.format(FusekiManager.readFile(ZAHTEV_SEARCH),
				this.authUtilities.getData() + ZAHTEV_GRAPH, Namespaces.PREDIKAT + "datum",
				Namespaces.PREDIKAT + "mesto", Namespaces.PREDIKAT + "tip", Namespaces.PREDIKAT + "status",
				search.getDatum(), search.getMesto(), search.getTip(), search.getStanje()));
	}

}
