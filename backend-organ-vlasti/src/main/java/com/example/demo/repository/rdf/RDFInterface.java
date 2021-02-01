package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.w3c.dom.Document;

public interface RDFInterface {

	public void add(Document document);
	public void update(String documentId, Document document);
	public void delete(String documentId);
	public ResultSet findAll(String documentId);
	public String search(String xml);

}
