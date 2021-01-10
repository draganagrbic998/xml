package com.example.demo.repository.rdf;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public interface RDFInterface {
	
	public void add(Model model);
	public ResultSet retrieve(String subject);

}
