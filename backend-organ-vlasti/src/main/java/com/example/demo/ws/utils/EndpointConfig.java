package com.example.demo.ws.utils;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.ws.odluka.OdlukaPortImpl;
import com.example.demo.ws.resenje.ResenjePortImpl;
import com.example.demo.ws.zahtev.ZahtevPortImpl;
import com.example.demo.ws.zalba.ZalbaPortImpl;

@Configuration
public class EndpointConfig {

	@Autowired
	private Bus bus;
	
	@Autowired
	private ZalbaPortImpl zlpi;
	
	@Autowired
	private ResenjePortImpl rpi;
	
	@Autowired
	private ZahtevPortImpl zhpi;
	
	@Autowired
	private OdlukaPortImpl opi;
	
	@Bean
	public Endpoint createZalbaEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, zlpi);
		endpoint.publish("/zalba");
		return endpoint;
	}
	
	@Bean
	public Endpoint createResenjeEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, rpi);
		endpoint.publish("/resenje");
		return endpoint;
	}
	
	@Bean
	public Endpoint getZahtevEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, zhpi);
		endpoint.publish("/zahtev");
		return endpoint;
	}

	@Bean
	public Endpoint getOdlukaEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, opi);
		endpoint.publish("/odluka");
		return endpoint;
	}
	
}
