package com.example.demo.common;

import java.io.File;

public class Constants {
	
	public static final String BACKEND_URL = "http://localhost:8081";
	public static final String FRONTEND_URL = "http://localhost:4200";
	public static final String SLUZBENIK = "sluzbenik";
	public static final String GRADJANIN = "gradjanin";
	
	public static final String DATA_FOLDER = "data";
	public static final String XSD_FOLDER = DATA_FOLDER + File.separatorChar + "xsd" + File.separatorChar;
	public static final String XSL_FOLDER = DATA_FOLDER + File.separatorChar + "xsl";
	public static final String INIT_FOLDER = DATA_FOLDER + File.separatorChar + "init" + File.separatorChar;
	public static final String GEN_FOLDER = DATA_FOLDER + File.separatorChar + "gen";
	public static final String SPARQL_FOLDER = DATA_FOLDER + File.separatorChar + "sparql";
	public static final String GRDDL_XSL = DATA_FOLDER + File.separatorChar + "grddl.xsl";
	public static final String FOP_CONF = DATA_FOLDER + File.separatorChar + "fop.conf";
	public static final String COLLECTIONS_PREFIX = "/db/database";
	public static final String RDFS_FOLDER = DATA_FOLDER + "/rdfs/";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
}
