package com.example.demo.ws.utils;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.ws.odgovor.OdgovorPortImpl;

@Configuration
public class EndpointConfig {

	@Autowired
	private Bus bus;
	
	@Autowired
	private OdgovorPortImpl copi;
		
	@Bean
	public Endpoint createZalbaEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, copi);
		endpoint.publish("/createOdgovor");
		return endpoint;
	}
	
}
