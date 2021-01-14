package com.example.demo.repository.xml;

import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

public interface ExistInterface {
	
	public void add(Document document);
	public void update(String documentId, Document document);
	public void delete(String documentId);
	public Document load(String documentId);
	public ResourceSet retrieve(String xpathExp);

}
