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
public class OdlukaRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;
	
	@Autowired
	private FusekiAuthentication authUtilities;
	
	public static final String ODLUKA_GRAPH = "/odluke";
	public static final String ODLUKA_SHAPE = Constants.SHAPE_FOLDER + "odluka.ttl";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(ODLUKA_GRAPH, document, ODLUKA_SHAPE);
	}
	
	@Override
	public void update(String subject, Document document) {
		this.fusekiManager.update(ODLUKA_GRAPH, Namespaces.ODLUKA + "/" + subject, document, ODLUKA_SHAPE);
	}

	@Override
	public void delete(String subject) {
		this.fusekiManager.delete(ODLUKA_GRAPH, Namespaces.ODLUKA + "/" + subject);
	}
	
	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ODLUKA_GRAPH, Namespaces.ODLUKA + "/" + subject);
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
