package com.example.demo.repository.rdf;

import java.util.List;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.fuseki.FusekiAuthentication;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class OdgovorRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	@Autowired
	private FusekiAuthentication authUtilities;

	public static final String ODGOVOR_GRAPH = "/odgovori";
	public static final String ODGOVOR_SHAPE = Constants.SHAPE_FOLDER + "odgovor.ttl";
	public static final String ODGOVOR_AND_SEARCH = Constants.SPARQL_FOLDER + "odgovor_and.rq";
	public static final String ODGOVOR_OR_SEARCH = Constants.SPARQL_FOLDER + "odgovor_or.rq";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(ODGOVOR_GRAPH, document, ODGOVOR_SHAPE);
	}

	@Override
	public void update(String subject, Document document) {
		this.fusekiManager.update(ODGOVOR_GRAPH, Namespaces.ODGOVOR + "/" + subject, document, ODGOVOR_SHAPE);
	}

	@Override
	public void delete(String subject) {
		this.fusekiManager.delete(ODGOVOR_GRAPH, Namespaces.ODGOVOR + "/" + subject);
	}
	
	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ODGOVOR_GRAPH, Namespaces.ODGOVOR + "/" + subject);
	}
	
	public List<Integer> resenja(String broj) {
		return this.fusekiManager.search(
				String.format(Utils.readFile(FusekiManager.REFERENCE_QUERY), 
				this.authUtilities.getData() + ResenjeRDF.RESENJE_GRAPH, 
				Namespaces.PREDIKAT + "odgovor", Namespaces.ODGOVOR + "/" + broj), Namespaces.RESENJE + "/");
	}

}
