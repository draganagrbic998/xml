package com.example.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XQueryService;

import rs.ac.uns.ftn.examples.xmldb.util.AuthenticationUtilities;
import rs.ac.uns.ftn.examples.xmldb.util.AuthenticationUtilities.ConnectionProperties;
public class XQueryExample6 {
    
	private static final String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/examples/xmldb/bookstore";
	
	public static void main(String[] args) throws Exception {
		XQueryExample6.run(AuthenticationUtilities.loadProperties(), args);
	}

	/**
	 * conn XML DB connection properties
     * args[0] Should be the collection ID to access
     * args[1] Should be the xquery file path
     */
    public static void run(ConnectionProperties conn, String args[]) throws Exception {
        
    	System.out.println("[INFO] " + XQueryExample6.class.getSimpleName());
    	
    	// initialize collection and document identifiers
        String collectionId = null;
		String xqueryFilePath = null, xqueryExpression = null; 
        
        if (args.length == 2) {
        	
        	System.out.println("[INFO] Passing the arguments... ");
        	collectionId = args[0];
        	xqueryFilePath = args[1];
        	
        	
        } else {
        	
        	System.out.println("[INFO] Using defaults.");
        	collectionId = "/db/sample/library";
        	xqueryFilePath = "data/xquery/retrieve-flwor.xqy";
        	
        }

        System.out.println("\t- collection ID: " + collectionId);
        System.out.println("\t- xQuery file path: " + xqueryFilePath);
        
    	// initialize database driver
    	System.out.println("[INFO] Loading driver class: " + conn.driver);
        Class<?> cl = Class.forName(conn.driver);
        
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        
        DatabaseManager.registerDatabase(database);
        
        Collection col = null;
        
        try { 

        	// get the collection
        	System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DatabaseManager.getCollection(conn.uri + collectionId);
        	
            // get an instance of xquery service
            XQueryService xqueryService = (XQueryService) col.getService("XQueryService", "1.0");
            xqueryService.setProperty("indent", "yes");
            
            // make the service aware of namespaces
            xqueryService.setNamespace("b", TARGET_NAMESPACE);

            // read xquery 
            System.out.println("[INFO] Invoking XQuery service for: " + xqueryFilePath);
            xqueryExpression = readFile(xqueryFilePath, StandardCharsets.UTF_8);
            System.out.println(xqueryExpression);
            
            // compile and execute the expression
            CompiledExpression compiledXquery = xqueryService.compile(xqueryExpression);
            ResourceSet result = xqueryService.execute(compiledXquery);
            
            // handle the results
            System.out.println("[INFO] Handling the results... ");
            
            ResourceIterator i = result.getIterator();
            Resource res = null;
            
            while(i.hasMoreResources()) {
            
            	try {
                    res = i.nextResource();
                    System.out.println(res.getContent());
                    
                } finally {
                    
                	// don't forget to cleanup resources
                    try { 
                    	((EXistResource)res).freeResources(); 
                    } catch (XMLDBException xe) {
                    	xe.printStackTrace();
                    }
                }
            }
            
        } finally {
        	
            // don't forget to cleanup
            if(col != null) {
                try { 
                	col.close();
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }
    }
    
    /**
	 * Convenience method for reading file contents into a string.
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
    
}
