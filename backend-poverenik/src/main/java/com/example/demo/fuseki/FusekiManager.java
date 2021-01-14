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
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;

@Component
public class FusekiManager {

	@Autowired
	private FusekiAuthentication authUtilities;
	
	private static final String RETRIEVE_QUERY = Constants.SPARQL_FOLDER + "retrieve.rq";

	public void save(String graphUri, Model model) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);
		String sparql = SparqlUtil.insertData(this.authUtilities.getData() + graphUri, out.toString());
		UpdateRequest request = UpdateFactory.create(sparql);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, this.authUtilities.getUpdate());
		processor.execute();
	}
	
	public ResultSet retrieve(String graphUri, String subject) {
		String sparql = String.format(readFile(RETRIEVE_QUERY), this.authUtilities.getData() + graphUri, subject);
		QueryExecution query = QueryExecutionFactory.sparqlService(this.authUtilities.getQuery(), sparql);
		ResultSet results = query.execSelect();
		return results;
	}
	
	public void update(String graphUri, Model model, String subject) {
		this.delete(graphUri, subject);
		this.save(graphUri, model);
	}
	
	public void delete(String graphUri, String subject) {
		String sparql = SparqlUtil.deleteData(this.authUtilities.getData() + graphUri, subject);
		UpdateRequest request = UpdateFactory.create(sparql);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, this.authUtilities.getUpdate());
		processor.execute();
	}
	
	public void dropAll() {
		UpdateRequest request = UpdateFactory.create() ;
        request.add(SparqlUtil.dropAll());
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, this.authUtilities.getUpdate());
        processor.execute();
	}
	
	public String search(String sparql) {
		QueryExecution query = QueryExecutionFactory.sparqlService(this.authUtilities.getQuery(), sparql);
		ResultSet results = query.execSelect();
		List<Integer> nums = new ArrayList<>();
		while(results.hasNext()) {
			QuerySolution querySolution = results.next() ;
			Iterator<String> variableBindings = querySolution.varNames();
		    while (variableBindings.hasNext()) {
		    	String varName = variableBindings.next();
		    	RDFNode varValue = querySolution.get(varName);
		    	if (varName.equals("doc")) {
		    		int value = Integer.parseInt(varValue.toString().replace(Namespaces.ZALBA + "/", ""));
		    		if (!nums.contains(value)) {
		    			nums.add(value);
		    		}
		    	}
		    }
		}
		String xpathExp = "(";
		for (int i = 0; i < nums.size(); ++i) {
			if (i == 0) {
				xpathExp += "broj = '" + nums.get(i) + "'";
			} else {
				xpathExp += " or broj = '" + nums.get(i) + "'";
			}
		}
		xpathExp += ")";
		return xpathExp;
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
