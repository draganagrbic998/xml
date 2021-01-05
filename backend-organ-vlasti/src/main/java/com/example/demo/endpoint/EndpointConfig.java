package com.example.demo.endpoint;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.ws.zalba.CreateZalbaPortImpl;

@Configuration
public class EndpointConfig {

	@Autowired
	private Bus bus;

	@Bean
	public Endpoint helloMessageEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, new CreateZalbaPortImpl());
		endpoint.publish("/createZalba");
		return endpoint;
	}

}
