package com.example.demo.common;

import java.io.File;

import com.ibm.icu.text.SimpleDateFormat;

public class Constants {

	public static final String BACKEND_URL = "http://localhost:8081";
	public static final String FRONTEND_URL = "http://localhost:4200";
	public static final String SLUZBENIK = "sluzbenik";
	public static final String GRADJANIN = "gradjanin";

	public static final String DATA_FOLDER = "data" + File.separatorChar;
	public static final String XSD_FOLDER = DATA_FOLDER + "xsd" + File.separatorChar;
	public static final String XSL_FOLDER = DATA_FOLDER + "xsl" + File.separatorChar;
	public static final String INIT_FOLDER = DATA_FOLDER + "init" + File.separatorChar;
	public static final String GEN_FOLDER = DATA_FOLDER + "gen" + File.separatorChar;
	public static final String SPARQL_FOLDER = DATA_FOLDER + "sparql" + File.separatorChar;
	public static final String RDFS_FOLDER = DATA_FOLDER + "rdfs" + File.separatorChar;

	public static final String COLLECTIONS_PREFIX = "/db/database";
	public static final String GRDDL_XSL = DATA_FOLDER + "grddl.xsl";
	public static final String FOP_CONF = DATA_FOLDER + "fop.conf";
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyy.");

	public static final String IZVESTAJ_STUB = "<izvestaj:Izvestaj xmlns=\"" 
			+ Namespaces.OSNOVA
			+ "\" xmlns:izvestaj=\"" 
			+ Namespaces.IZVESTAJ 
			+ "\" xmlns:pred=\""
			+ Prefixes.PREDIKAT
			+ "\" xmlns:xs=\""
			+ Namespaces.XS
			+ "\" about=\"\" rel=\"pred:podneo\" href=\"\">"
			+ "<broj></broj>"
			+ "<datum property=\"pred:datum\" datatype=\"xs:string\"></datum>"
			+ "<izvestaj:godina property=\"pred:godina\" datatype=\"xs:string\"></izvestaj:godina>"
			+ "</izvestaj:Izvestaj>";
}
