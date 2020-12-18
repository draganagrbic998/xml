package com.example.demo.database;

import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

@Component
public class ExistManager {

	@Autowired
	private AuthenticationUtilities authUtilities;
	
	@SuppressWarnings("deprecation")
	public void createConnection() throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {
		Class<?> cl = Class.forName(this.authUtilities.getDriver());
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");
		DatabaseManager.registerDatabase(database);
	}
	
	public void save(String collectionId, String documentId, OutputStream out) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		Collection collection = null;
		XMLResource resource = null;
		try { 
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			if (documentId == null) {
				String[] array = collection.listResources();
				if (array.length == 0) {
					documentId = "1";
				}
				else {
					documentId = (Integer.parseInt(array[array.length - 1]) + 1	) + "";				
				}
			}
			resource = (XMLResource) collection.createResource(documentId, XMLResource.RESOURCE_TYPE);
			resource.setContent(out);
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
	
	
	public XMLResource load(String collectionId, String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
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
		return resource;
	}
	
	public XMLResource load(String collectionId, int documentIndex) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		Collection collection = null;
		XMLResource resource = null;
		try {
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			collection.setProperty(OutputKeys.INDENT, "yes");
			String documentId = collection.listResources()[documentIndex-1];
			resource = (XMLResource) collection.getResource(documentId);
		}
		finally {
			collection.close();			
		}
		return resource;
	}

	
	private Collection getCollection(String collectionId, int pathSegmentOffset) throws XMLDBException {
		
		Collection collection = DatabaseManager.getCollection(this.authUtilities.getUri() + collectionId, this.authUtilities.getUser(), this.authUtilities.getPassword());
        
        if(collection == null) {
        
         	if(collectionId.startsWith("/")) {
                collectionId = collectionId.substring(1);
            }
        	String pathSegments[] = collectionId.split("/");
            
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
