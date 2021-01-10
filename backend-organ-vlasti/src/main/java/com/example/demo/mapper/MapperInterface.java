package com.example.demo.mapper;

import org.apache.jena.rdf.model.Model;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

public interface MapperInterface {
	
	public Document map(String xml);
	public String map(ResourceSet resources);
	public Model map(Document document);

}
