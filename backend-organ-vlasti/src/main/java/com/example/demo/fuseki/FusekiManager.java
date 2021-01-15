package com.example.demo.fuseki;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.topbraid.jenax.util.JenaUtil;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.shacl.vocabulary.SH;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;

@Component
public class FusekiManager {

	@Autowired
	private FusekiAuthentication authUtilities;
	
	private static final String RETRIEVE_QUERY = Constants.SPARQL_FOLDER + "retrieve.rq";
	public static final String REFERENCE_QUERY = Constants.SPARQL_FOLDER + "reference.rq";	
	
	public void save(String graphUri, Model model, String shapePath) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		Model shapeModel = JenaUtil.createDefaultModel();
		shapeModel.read(shapePath);

		Resource reportResource = ValidationUtil.validateModel(model, shapeModel, true);
		boolean conforms = reportResource.getProperty(SH.conforms).getBoolean();
		
		if (conforms) {
			model.write(out, SparqlUtil.NTRIPLES);
			String sparql = SparqlUtil.insertData(this.authUtilities.getData() + graphUri, out.toString());
			UpdateRequest request = UpdateFactory.create(sparql);
	        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, this.authUtilities.getUpdate());
			processor.execute();
		}
		else {
			RDFDataMgr.write(System.out, reportResource.getModel(), RDFFormat.TURTLE);
		}
	}
	
	public ResultSet retrieve(String graphUri, String subject) {
		String sparql = String.format(readFile(RETRIEVE_QUERY), this.authUtilities.getData() + graphUri, subject);
		QueryExecution query = QueryExecutionFactory.sparqlService(this.authUtilities.getQuery(), sparql);
		ResultSet results = query.execSelect();
		return results;
	}
	
	public void update(String graphUri, Model model, String subject, String shapePath) {
		this.delete(graphUri, subject);
		this.save(graphUri, model, shapePath);
	}
	
	public void delete(String graphUri, String subject) {
		String sparql = SparqlUtil.deleteData(this.authUtilities.getData() + graphUri, subject);
		UpdateRequest request = UpdateFactory.create(sparql);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, this.authUtilities.getUpdate());
		processor.execute();
	}
	
	public List<Integer> search(String sparql, String prefix) {
		QueryExecution query = QueryExecutionFactory.sparqlService(this.authUtilities.getQuery(), sparql);
		ResultSet results = query.execSelect();
		List<Integer> brojevi = new ArrayList<>();
		while(results.hasNext()) {
			QuerySolution querySolution = results.next() ;
			Iterator<String> variableBindings = querySolution.varNames();
		    while (variableBindings.hasNext()) {
		    	String varName = variableBindings.next();
		    	RDFNode varValue = querySolution.get(varName);
		    	if (varName.equals("doc")) {
		    		int value = Integer.parseInt(varValue.toString().replace(prefix, ""));
		    		if (!brojevi.contains(value)) {
		    			brojevi.add(value);
		    		}
		    	}
		    }
		}
		/*
		String xpathExp = "(";
		for (int i = 0; i < brojevi.size(); ++i) {
			if (i == 0) {
				xpathExp += "broj = '" + brojevi.get(i) + "'";
			} else {
				xpathExp += " or broj = '" + brojevi.get(i) + "'";
			}
		}
		xpathExp += ")";
		return xpathExp;*/
		return brojevi;
	}
	
	public void dropAll() {
		UpdateRequest request = UpdateFactory.create() ;
        request.add(SparqlUtil.dropAll());
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, this.authUtilities.getUpdate());
        processor.execute();
	}
	
	public static String readFile(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
