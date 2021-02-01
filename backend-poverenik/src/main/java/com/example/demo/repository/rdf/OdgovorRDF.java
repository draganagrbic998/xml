package com.example.demo.repository.rdf;

import java.util.List;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class OdgovorRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	public static final String ODGOVOR_GRAPH = "/odgovori";
	public static final String ODGOVOR_SHAPE = Constants.SHAPE_FOLDER + "odgovor.ttl";
	public static final String ODGOVOR_AND_SEARCH = Constants.SPARQL_FOLDER + "odgovor_and.rq";
	public static final String ODGOVOR_OR_SEARCH = Constants.SPARQL_FOLDER + "odgovor_or.rq";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(ODGOVOR_GRAPH, document, ODGOVOR_SHAPE);
	}

	@Override
	public void update(String documentId, Document document) {
		this.fusekiManager.update(ODGOVOR_GRAPH, Namespaces.ODGOVOR + "/" + documentId, document, ODGOVOR_SHAPE);
	}

	@Override
	public void delete(String documentId) {
		this.fusekiManager.delete(ODGOVOR_GRAPH, Namespaces.ODGOVOR + "/" + documentId);
	}

	@Override
	public ResultSet findAll(String documentId) {
		return this.fusekiManager.findAll(ODGOVOR_GRAPH, Namespaces.ODGOVOR + "/" + documentId);
	}
	
	@Override
	public String search(String xml) {
		return Utils.getReferences(this.fusekiManager.search(ODGOVOR_GRAPH, xml));
	}

	public List<String> resenja(String documentId) {
		return this.fusekiManager.references(ResenjeRDF.RESENJE_GRAPH, Namespaces.PREDIKAT + "odgovor", Namespaces.ODGOVOR + "/" + documentId);
	}

}
