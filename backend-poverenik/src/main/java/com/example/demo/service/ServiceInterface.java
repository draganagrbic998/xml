package com.example.demo.service;

import org.w3c.dom.Document;

public interface ServiceInterface {
	
	public void add(String xml);
	public void update(String documentId, Document document);
	public void delete(String documentId);
	public Document load(String documentId);
	public String retrieve();
	public String regularSearch(String xml);
	public String advancedSearch(String xml);

}
