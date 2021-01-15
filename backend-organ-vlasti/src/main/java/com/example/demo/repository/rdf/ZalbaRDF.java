package com.example.demo.repository.rdf;

import java.util.List;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

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
	private static final String ZALBA_SHAPE = Constants.SHAPE_FOLDER + "zalba.ttl";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(ZALBA_GRAPH, document, ZALBA_SHAPE);
	}
	
	@Override
	public void update(String subject, Document document) {
		this.fusekiManager.update(ZALBA_GRAPH, Namespaces.ZALBA + "/" + subject, document, ZALBA_SHAPE);
	}

	@Override
	public void delete(String subject) {
		this.fusekiManager.delete(ZALBA_GRAPH, Namespaces.ZALBA + "/" + subject);
	}
	
	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ZALBA_GRAPH, Namespaces.ZALBA + "/" + subject);
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
