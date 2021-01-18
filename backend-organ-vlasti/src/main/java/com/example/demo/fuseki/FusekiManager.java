package com.example.demo.fuseki;

import java.io.ByteArrayOutputStream;
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

import org.w3c.dom.Document;

import org.topbraid.jenax.util.JenaUtil;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.shacl.vocabulary.SH;

import com.example.demo.common.Constants;
import com.example.demo.common.Utils;
import com.example.demo.parser.XSLTransformer;

@Component
public class FusekiManager {

	@Autowired
	private FusekiAuthentication authUtilities;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	public static final String RETRIEVE_QUERY = Constants.SPARQL_FOLDER + "retrieve.rq";
	public static final String REFERENCE_QUERY = Constants.SPARQL_FOLDER + "reference.rq";	
	
	public void add(String graphUri, Document document, String shapePath) {
		Model model = this.xslTransformer.generateMetadata(document);
		Model shapeModel = JenaUtil.createDefaultModel();
		shapeModel.read(shapePath);
		Resource reportResource = ValidationUtil.validateModel(model, shapeModel, true);
		boolean conforms = reportResource.getProperty(SH.conforms).getBoolean();
		
		if (conforms) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
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
	
	public void update(String graphUri, String subject, Document document, String shapePath) {
		this.delete(graphUri, subject);
		this.add(graphUri, document, shapePath);
	}

	public void delete(String graphUri, String subject) {
		String sparql = SparqlUtil.deleteData(this.authUtilities.getData() + graphUri, subject);
		UpdateRequest request = UpdateFactory.create(sparql);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, this.authUtilities.getUpdate());
		processor.execute();
	}
	
	public ResultSet retrieve(String graphUri, String subject) {
		String sparql = String.format(Utils.readFile(RETRIEVE_QUERY), this.authUtilities.getData() + graphUri, subject);
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
		return brojevi;
	}
			
}
