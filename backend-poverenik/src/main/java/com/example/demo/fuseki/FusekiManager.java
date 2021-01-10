package com.example.demo.fuseki;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;

@Component
public class FusekiManager {

	@Autowired
	private FusekiAuthentication authUtilities;
	
	private static final String QUERY1_PATH = Constants.SPARQL_FOLDER + "query1.rq";
	
	public void save(String graphUri, Model model) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);
		String sparql = SparqlUtil.insertData(this.authUtilities.getData() + graphUri, out.toString());
		UpdateRequest request = UpdateFactory.create(sparql);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, this.authUtilities.getUpdate());
		processor.execute();
	}
	
	public ResultSet retrieve(String graphUri, String subject) {
		String sparql = String.format(this.readFile(QUERY1_PATH), this.authUtilities.getData() + graphUri, subject);
		QueryExecution query = QueryExecutionFactory.sparqlService(this.authUtilities.getQuery(), sparql);
		ResultSet results = query.execSelect();
		return results;
	}
	
	public void dropAll() {
		UpdateRequest request = UpdateFactory.create() ;
        request.add(SparqlUtil.dropAll());
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, this.authUtilities.getUpdate());
        processor.execute();
	}
	
	private String readFile(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
