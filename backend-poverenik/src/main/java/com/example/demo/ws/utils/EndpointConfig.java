package com.example.demo.ws.utils;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.ws.izvestaj.IzvestajPortImpl;
import com.example.demo.ws.odgovor.OdgovorPortImpl;

@Configuration
public class EndpointConfig {

	@Autowired
	private Bus bus;

	@Autowired
	private OdgovorPortImpl opi;

	@Autowired
	private IzvestajPortImpl ipi;

	@Bean
	public Endpoint createOdgovorEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, opi);
		endpoint.publish("/odgovor");
		return endpoint;
	}

	@Bean
	public Endpoint createIzvestajEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, ipi);
		endpoint.publish("/izvestaj");
		return endpoint;
	}
}
