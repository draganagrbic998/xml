package com.example.demo.service;

import org.w3c.dom.Document;

public interface ServiceInterface {
	
	public void add(String xml);
	public void update(String documentId, Document document);
	public String retrieve();
	public Document load(String documentId);

}