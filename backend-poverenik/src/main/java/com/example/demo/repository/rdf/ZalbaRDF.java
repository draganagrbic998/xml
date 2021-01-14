package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.fuseki.FusekiAuthentication;
import com.example.demo.fuseki.FusekiManager;
import com.example.demo.model.Pretraga;
import com.example.demo.model.ZalbaPretraga;

@Repository
public class ZalbaRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	@Autowired
	private FusekiAuthentication authUtilities;

	public static final String ZALBA_GRAPH = "/zalbe";
	private static final String ZALBA_AND_SEARCH = Constants.SPARQL_FOLDER + "zalba_and.rq";
	private static final String ZALBA_OR_SEARCH = Constants.SPARQL_FOLDER + "zalba_or.rq";

	@Override
	public void add(Model model) {
		this.fusekiManager.save(ZALBA_GRAPH, model);
	}

	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ZALBA_GRAPH, Namespaces.ZALBA + "/" + subject);
	}

	@Override
	public void update(String graphUri, Model model, String subject) {
		this.fusekiManager.update(graphUri, model, subject);
	}

	@Override
	public void delete(String graphUri, String subject) {
		this.fusekiManager.delete(graphUri, subject);
	}

	@Override
	public String search(Pretraga pretraga) {
		ZalbaPretraga zalbaPretraga = (ZalbaPretraga) pretraga;
		return this.fusekiManager.search(String.format(
				FusekiManager.readFile(zalbaPretraga.getOperacija().equals("and") ? ZALBA_AND_SEARCH : ZALBA_OR_SEARCH),
				this.authUtilities.getData() + ZALBA_GRAPH, Namespaces.PREDIKAT + "datum",
				Namespaces.PREDIKAT + "mesto", Namespaces.PREDIKAT + "izdatoU", Namespaces.PREDIKAT + "organVlasti",
				Namespaces.PREDIKAT + "tip", Namespaces.PREDIKAT + "stanje", zalbaPretraga.getDatum(),
				zalbaPretraga.getMesto(), zalbaPretraga.getMestoIzdavanja(), zalbaPretraga.getOrganVlasti(),
				zalbaPretraga.getTip(), zalbaPretraga.getStanje()));
	}

}
