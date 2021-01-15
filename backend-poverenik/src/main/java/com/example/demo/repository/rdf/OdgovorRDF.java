package com.example.demo.repository.rdf;

import java.util.List;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Namespaces;
import com.example.demo.fuseki.FusekiAuthentication;
import com.example.demo.fuseki.FusekiManager;
import com.example.demo.model.Pretraga;

@Repository
public class OdgovorRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	@Autowired
	private FusekiAuthentication authUtilities;

	public static final String ODGOVOR_GRAPH = "/odgovori";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(ODGOVOR_GRAPH, document);
	}

	@Override
	public void update(String subject, Document document) {
		this.fusekiManager.update(ODGOVOR_GRAPH, Namespaces.ODGOVOR + "/" + subject, document);
	}

	@Override
	public void delete(String subject) {
		this.fusekiManager.delete(ODGOVOR_GRAPH, Namespaces.ODGOVOR + "/" + subject);
	}
	
	@Override
	public ResultSet retrieve(String subject) {
		return this.fusekiManager.retrieve(ODGOVOR_GRAPH, Namespaces.ODGOVOR + "/" + subject);
	}

	@Override
	public String search(Pretraga pretraga) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Integer> resenja(String broj) {
		return this.fusekiManager.search(
				String.format(FusekiManager.readFile(FusekiManager.REFERENCE_QUERY), 
				this.authUtilities.getData() + ResenjeRDF.RESENJE_GRAPH, 
				Namespaces.PREDIKAT + "odgovor", Namespaces.ODGOVOR + "/" + broj), Namespaces.RESENJE + "/");
	}

}
