package com.example.demo.rdf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtilitiesRdf {
	
	@Value("${conn.endpoint}")
	private String endpoint;
	
	@Value("${conn.dataset}")
	private String dataset;
	
	@Value("${conn.endpoint}/${conn.dataset}/${conn.query}")
	private String queryEndpoint;
	
	@Value("${conn.endpoint}/${conn.dataset}/${conn.update}")
	private String updateEndpoint;
	
	@Value("${conn.endpoint}/${conn.dataset}/${conn.data}")
	private String dataEndpoint;
	
	public AuthenticationUtilitiesRdf() {
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

	public String getQueryEndpoint() {
		return queryEndpoint;
	}

	public void setQueryEndpoint(String queryEndpoint) {
		this.queryEndpoint = queryEndpoint;
	}

	public String getUpdateEndpoint() {
		return updateEndpoint;
	}

	public void setUpdateEndpoint(String updateEndpoint) {
		this.updateEndpoint = updateEndpoint;
	}

	public String getDataEndpoint() {
		return dataEndpoint;
	}

	public void setDataEndpoint(String dataEndpoint) {
		this.dataEndpoint = dataEndpoint;
	}
	
}
