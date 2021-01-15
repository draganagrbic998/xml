package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.w3c.dom.Document;

public interface RDFInterface {
	
	public void add(Document document);
	public void update(String subject, Document document);
	public void delete(String subject);
	public ResultSet retrieve(String subject);

}
