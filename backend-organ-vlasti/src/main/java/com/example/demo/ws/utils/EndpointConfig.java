package com.example.demo.ws.utils;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.ws.resenje.CreateResenjePortImpl;
import com.example.demo.ws.zalba.CreateZalbaPortImpl;

@Configuration
public class EndpointConfig {

	@Autowired
	private Bus bus;
	
	@Autowired
	private CreateZalbaPortImpl czpi;
	
	@Autowired
	private CreateResenjePortImpl crpi;
	
	@Bean
	public Endpoint createZalbaEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, czpi);
		endpoint.publish("/createZalba");
		return endpoint;
	}

	@Bean
	public Endpoint createResenjeEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, crpi);
		endpoint.publish("/createResenje");
		return endpoint;
	}
	
}
