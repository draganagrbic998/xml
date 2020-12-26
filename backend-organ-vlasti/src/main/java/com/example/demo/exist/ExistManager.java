package com.example.demo.exist;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Namespaces;
import com.example.demo.parser.DOMParser;

@Component
public class ExistManager {

	@Autowired
	private AuthenticationUtilities authUtilities;
	
	@Autowired
	private DOMParser domParser;
	
	@SuppressWarnings("deprecation")
	public void createConnection() throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {
		Class<?> cl = Class.forName(this.authUtilities.getDriver());
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");
		DatabaseManager.registerDatabase(database);
	}
	
	public void save(String collectionId, String documentId, Document document) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		Collection collection = null;
		XMLResource resource = null;
		try { 
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			if (documentId == null) {
				documentId = collection.createId().split("\\.")[0];
				((Element) document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0)).setTextContent(documentId);
			}
			resource = (XMLResource) collection.createResource(documentId, XMLResource.RESOURCE_TYPE);
			resource.setContent(this.domParser.buildXml(document));
			collection.storeResource(resource);
		}
		finally {
			collection.close();
			((EXistResource) resource).freeResources();
		}
	}
	
	
	
	public void save(String collectionId, String documentId, File file) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		Collection collection = null;
		XMLResource resource = null;
		try { 
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			if (documentId == null) {
				documentId = collection.createId();
			}
			resource = (XMLResource) collection.createResource(documentId, XMLResource.RESOURCE_TYPE);
			resource.setContent(file);
			collection.storeResource(resource);
		}
		finally {
			collection.close();
			((EXistResource) resource).freeResources();
		}
	}
	
	
	public Document load(String collectionId, String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		Collection collection = null;
		XMLResource resource = null;
		try {
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			collection.setProperty(OutputKeys.INDENT, "yes");
			resource = (XMLResource) collection.getResource(documentId);
		}
		finally {
			collection.close();			
		}
		return (Document) resource.getContentAsDOM();
	}
	
	private Collection getCollection(String collectionId, int pathSegmentOffset) throws XMLDBException {
		
		Collection collection = DatabaseManager.getCollection(this.authUtilities.getUri() + collectionId, this.authUtilities.getUser(), this.authUtilities.getPassword());
        
        if(collection == null) {
        
         	if(collectionId.startsWith("/")) {
                collectionId = collectionId.substring(1);
            }
        	String[] pathSegments = collectionId.split("/");
            
        	if(pathSegments.length > 0) {

        		StringBuilder path = new StringBuilder();
                for(int i = 0; i <= pathSegmentOffset; ++i) {
                    path.append("/" + pathSegments[i]);
                }
                Collection startCollection = DatabaseManager.getCollection(this.authUtilities.getUri() + path, this.authUtilities.getUser(), this.authUtilities.getPassword());
                
                if (startCollection == null) {
                	String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCollection = DatabaseManager.getCollection(this.authUtilities.getUri() + parentPath, this.authUtilities.getUser(), this.authUtilities.getPassword());
                    CollectionManagementService collectionService = (CollectionManagementService) parentCollection.getService("CollectionManagementService", "1.0");                    
                    collection = collectionService.createCollection(pathSegments[pathSegmentOffset]);
                    collection.close();
                    parentCollection.close();
                } 
                else {
                    startCollection.close();
                }
                
            }
        	
            return this.getCollection(collectionId, ++pathSegmentOffset);
        } 
        
        else {
            return collection;
        }
		
	}
	
}
