package com.example.demo.exist;

import java.util.Arrays;

import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.parser.SchemaValidator;

@Component
public class ExistManager {

	@Autowired
	private ExistAuthentication authUtilities;
	
	@Autowired
	private SchemaValidator schemaValidator;
	
	@SuppressWarnings("deprecation")
	public void createConnection() {
		try {
			Class<?> cl = Class.forName(this.authUtilities.getDriver());
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");
			DatabaseManager.registerDatabase(database);			
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public String nextDocumentId(String collectionId) {
		Collection collection = null;
		try { 
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			String[] array = collection.listResources();
			return (Arrays.asList(array).stream().mapToInt(str -> Integer.parseInt(str)).max().orElse(0) + 1) + "";
		}
		catch(Exception e) {
			throw new MyException(e);
		}
		finally {
			try {
				collection.close();
			}
			catch(Exception e) {
				throw new MyException(e);
			}
		}
	}
	
	public String save(String collectionId, String documentId, Document document, String schemaPath) {
		Collection collection = null;
		XMLResource resource = null;
		try { 
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			this.schemaValidator.validate(document, schemaPath);
			if (documentId == null) {
				documentId = this.nextDocumentId(collectionId);
			}
			resource = (XMLResource) collection.createResource(documentId, XMLResource.RESOURCE_TYPE);
			resource.setContentAsDOM(document);
			collection.storeResource(resource);
			return documentId;
		}
		catch(Exception e) {
			throw new MyException(e);
		}
		finally {
			try {
				collection.close();
				((EXistResource) resource).freeResources();
			}
			catch(Exception e) {
				throw new MyException(e);
			}
		}
	}
	
	public Document load(String collectionId, String documentId) {
		Collection collection = null;
		try {
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			collection.setProperty(OutputKeys.INDENT, "yes");
			XMLResource resource = (XMLResource) collection.getResource(documentId);
			return (Document) resource.getContentAsDOM();
		}
		catch(Exception e) {
			throw new MyException(e);
		}
		finally {
			try {
				collection.close();			
			}
			catch(Exception e) {
				throw new MyException(e);
			}
		}
	}
	
	public ResourceSet retrieve(String collectionId, String xpathExp) {
		Collection collection = null;
		try {
			this.createConnection();
			collection = this.getCollection(collectionId, 0);
			XPathQueryService xpathService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
			xpathService.setProperty(OutputKeys.INDENT, "yes");
			xpathService.setNamespace("", Namespaces.OSNOVA);
			xpathService.setNamespace("zahtev", Namespaces.ZAHTEV);
			xpathService.setNamespace("odluka", Namespaces.ODLUKA);
			xpathService.setNamespace("zalba", Namespaces.ZALBA);
			xpathService.setNamespace("resenje", Namespaces.RESENJE);
			xpathService.setNamespace("odgovor", Namespaces.ODGOVOR);
			xpathService.setNamespace("izvestaj", Namespaces.IZVESTAJ);
			return xpathService.query(xpathExp);
		} 
		catch(Exception e) {
			throw new MyException(e);
		}
		finally {
			try {
				collection.close();			
			}
			catch(Exception e) {
				throw new MyException(e);
			}
		}
	}
	
	private Collection getCollection(String collectionId, int pathSegmentOffset) {
		
		try {
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
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public void dropCollection(String collectionId) {
		this.createConnection();
		Collection collection = null;
		try {
			collection = this.getCollection(collectionId, 0);
			for (String documentId: collection.listResources()) {
				collection.removeResource(collection.getResource(documentId));
			}
		}
		catch(Exception e) {
			throw new MyException(e);
		}
		finally {
			try {
				collection.close();
			}
			catch(Exception e) {
				throw new MyException(e);
			}
		}
	}
	
	public void delete(String collectionId, String documentId) {
		this.createConnection();
		Collection collection = null;
		try {
			collection = this.getCollection(collectionId, 0);
			collection.removeResource(collection.getResource(documentId));
		}
		catch(Exception e) {
			throw new MyException(e);
		}
		finally {
			try {
				collection.close();
			}
			catch(Exception e) {
				throw new MyException(e);
			}
		}
	}
	
}
