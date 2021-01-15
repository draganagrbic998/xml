package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.w3c.dom.Document;

import com.example.demo.model.Pretraga;

public interface RDFInterface {
	
	public void add(Document document);
	public void delete(String subject);
	public void update(String subject, Document document);
	public ResultSet retrieve(String subject);
	public String search(Pretraga pretraga);

}
