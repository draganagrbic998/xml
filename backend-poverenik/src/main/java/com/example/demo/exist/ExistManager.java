package com.example.demo.exist;

import java.util.Arrays;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import com.example.demo.constants.Namespaces;

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
	
	public String save(String collectionId, String documentId, Document document) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		Collection collection = null;
		XMLResource resource = null;
		try { 
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			if (documentId == null) {
				String[] array = collection.listResources();
				documentId = (Arrays.asList(array).stream().mapToInt(str -> Integer.parseInt(str)).max().orElse(0) + 1) + "";
				((Element) document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0)).setTextContent(documentId);
			}
			resource = (XMLResource) collection.createResource(documentId, XMLResource.RESOURCE_TYPE);
			resource.setContentAsDOM(document);
			collection.storeResource(resource);
			return documentId;
		}
		finally {
			collection.close();
			((EXistResource) resource).freeResources();
		}
	}
	
	public Document load(String collectionId, String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		Collection collection = null;
		try {
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			collection.setProperty(OutputKeys.INDENT, "yes");
			XMLResource resource = (XMLResource) collection.getResource(documentId);
			return (Document) resource.getContentAsDOM();
		}
		finally {
			collection.close();			
		}
	}
	
	public ResourceSet retrieve(String collectionId, String xpathExp) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Collection collection = null;
		try {
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			XPathQueryService xpathService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
			xpathService.setProperty("indent", "yes");
			xpathService.setNamespace("", Namespaces.OSNOVA);
			xpathService.setNamespace("zalba", Namespaces.ZALBA);
			xpathService.setNamespace("resenje", Namespaces.RESENJE);
			return xpathService.query(xpathExp);
		} 
		finally {
			collection.close();			
		}
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
	
	public void dropCollection(String collectionId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		this.createConnection();
		Collection collection = null;
		try {
			collection = this.getCollection(collectionId, 0);
			for (String documentId: collection.listResources()) {
				collection.removeResource(collection.getResource(documentId));
			}
		}
		finally {
			collection.close();
		}
	}
	
}
