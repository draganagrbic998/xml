package com.example.demo.constants;

import java.io.File;

public class Constants {
	
	public static final String BACKEND_URL = "http://localhost:8082";
	public static final String FRONTEND_URL = "http://localhost:4200";	//treba 4201
	public static final String POVERENIK = "poverenik";
	public static final String GRADJANIN = "gradjanin";
	
	public static final String DATA_FOLDER = "data";
	public static final String XSD_FOLDER = DATA_FOLDER + File.separatorChar + "xsd";
	public static final String XSL_FOLDER = DATA_FOLDER + File.separatorChar + "xsl";
	public static final String GEN_FOLDER = DATA_FOLDER + File.separatorChar + "gen";
	public static final String INIT_FOLDER = DATA_FOLDER + File.separatorChar + "init";
	public static final String FOP_CONF = DATA_FOLDER + File.separatorChar + "fop.conf";

	public static final String COLLECTIONS_PREFIX = "/db/database";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TEST_MESTO = "TEST MESTO";
	public static final String TEST_POTPIS = "TEST POTPIS";
	
}
