package com.example.demo.fuseki;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FusekiAuthentication {
	
	@Value("${fuseki.endpoint}")
	private String endpoint;
	
	@Value("${fuseki.dataset}")
	private String dataset;
	
	@Value("${fuseki.endpoint}/${fuseki.dataset}/${fuseki.query}")
	private String query;
	
	@Value("${fuseki.endpoint}/${fuseki.dataset}/${fuseki.update}")
	private String update;
	
	@Value("${fuseki.endpoint}/${fuseki.dataset}/${fuseki.data}")
	private String data;

	public FusekiAuthentication() {
		super();
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
