package com.example.demo.common;

import java.io.File;

public class Constants {
	
	public static final String BACKEND_URL = "http://localhost:8082";
	public static final String FRONTEND_URL = "http://localhost:4201";
	public static final String POVERENIK = "poverenik";
	public static final String GRADJANIN = "gradjanin";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String COLLECTIONS_PREFIX = "/db/database";

	public static final String DATA_FOLDER = "data" + File.separatorChar;
	public static final String XSD_FOLDER = DATA_FOLDER + "xsd" + File.separatorChar;
	public static final String XSL_FOLDER = DATA_FOLDER + "xsl" + File.separatorChar;
	public static final String INIT_FOLDER = DATA_FOLDER + "init" + File.separatorChar;
	public static final String GEN_FOLDER = DATA_FOLDER + "gen" + File.separatorChar;
	public static final String SPARQL_FOLDER = DATA_FOLDER + "sparql" + File.separatorChar;
	
	public static final String GRDDL_XSL = DATA_FOLDER + "grddl.xsl";
	public static final String FOP_CONF = DATA_FOLDER + "fop.conf";
		
}
