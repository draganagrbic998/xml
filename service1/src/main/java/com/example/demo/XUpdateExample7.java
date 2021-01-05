package com.example.demo;

import static rs.ac.uns.ftn.examples.xmldb.api.template.XUpdateTemplate.APPEND;
import static rs.ac.uns.ftn.examples.xmldb.api.template.XUpdateTemplate.INSERT_AFTER;
import static rs.ac.uns.ftn.examples.xmldb.api.template.XUpdateTemplate.INSERT_BEFORE;
import static rs.ac.uns.ftn.examples.xmldb.api.template.XUpdateTemplate.REMOVE;
import static rs.ac.uns.ftn.examples.xmldb.api.template.XUpdateTemplate.UPDATE;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;

import rs.ac.uns.ftn.examples.xmldb.util.AuthenticationUtilities;
import rs.ac.uns.ftn.examples.xmldb.util.AuthenticationUtilities.ConnectionProperties;
public class XUpdateExample7 {
    
	public static void main(String[] args) throws Exception {
		XUpdateExample7.run(AuthenticationUtilities.loadProperties(), args);
	}

	/**
	 * conn XML DB connection properties
     * args[0] Should be the collection ID to access
     * args[1] Should be the document ID
     */
    public static void run(ConnectionProperties conn, String args[]) throws Exception {
        
    	System.out.println("[INFO] " + XUpdateExample7.class.getSimpleName());
    	
    	// initialize collection and document identifiers
    	String collectionId = null;
    	String documentId = null;
        
        if (args.length == 2) {
        	
        	System.out.println("[INFO] Passing the arguments... ");
        	collectionId = args[0];
        	documentId = args[1];
        	
        	
        } else {
        	
        	System.out.println("[INFO] Using defaults.");
        	collectionId = "/db/sample/library";
        	documentId = "1.xml";
        	
        }

        System.out.println("\t- collection ID: " + collectionId);
        
    	// initialize database driver
    	System.out.println("[INFO] Loading driver class: " + conn.driver);
        Class<?> cl = Class.forName(conn.driver);
        
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        
        DatabaseManager.registerDatabase(database);
        
        Collection col = null;
        
        try { 

        	// defining xpath context
        	String contextXPath1 = "/bookstore/book[1]";
        	String contextXPath2 = "/bookstore/book[3]";
    		String contextXPath3 = "/bookstore";
    		String contextXPath4 = "/bookstore/book[1]/@category";
    		
    		String xmlFragment = 
    				"<book category=\"TEST\">\r\n" + 
    				"	<title lang=\"en\">Test book</title>\r\n" + 
    				"	<author>Test Author</author>\r\n" + 
    				"	<year>2016</year>\r\n" + 
    				"	<price>59.99</price>\r\n" + 
    				"</book>";
    		
    		String patch = 
    				"<title lang=\"en\">AngularJS in Action</title>\r\n" + 
    				"<author>Brian Ford</author>\r\n" + 
    				"<author>Lukas Ruebbelke</author>\r\n" + 
    				"<year>2014</year>\r\n" + 
    				"<price>39.60</price>\r\n";
        	
        	// get the collection
        	System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DatabaseManager.getCollection(conn.uri + collectionId, conn.user, conn.password);
            col.setProperty("indent", "yes");
        	
            // get an instance of xupdate query service
            System.out.println("[INFO] Fetching XUpdate service for the collection.");
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");

            // compile and execute xupdate expressions
            System.out.println("[INFO] Inserting fragments after " + contextXPath1 + " node.");
            long mods = xupdateService.updateResource(documentId, String.format(INSERT_AFTER, contextXPath1, xmlFragment));
            System.out.println("[INFO] " + mods + " modifications processed.");

            System.out.println("[INFO] Inserting fragments before " + contextXPath1 + " node.");
            mods = xupdateService.updateResource(documentId, String.format(INSERT_BEFORE, contextXPath1, xmlFragment));
            System.out.println("[INFO] " + mods + " modifications processed.");
            
            System.out.println("[INFO] Appending fragments as last child of " + contextXPath3 + " node.");
            mods = xupdateService.updateResource(documentId, String.format(APPEND, contextXPath3, xmlFragment));
            System.out.println("[INFO] " + mods + " modifications processed.");
            
            System.out.println("[INFO] Updating " + contextXPath1 + " node.");
            mods = xupdateService.updateResource(documentId, String.format(UPDATE, contextXPath1, patch));
            System.out.println("[INFO] " + mods + " modifications processed.");
            
            System.out.println("[INFO] Updating " + contextXPath4 + " node.");
            mods = xupdateService.updateResource(documentId, String.format(UPDATE, contextXPath4, "WEB*"));
            System.out.println("[INFO] " + mods + " modifications processed.");
            
            System.out.println("[INFO] Removing " + contextXPath2 + " node.");
            mods = xupdateService.updateResource(documentId, String.format(REMOVE, contextXPath2));
            System.out.println("[INFO] " + mods + " modifications processed.");
            
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
    
}
