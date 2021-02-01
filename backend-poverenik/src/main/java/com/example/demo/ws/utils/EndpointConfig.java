package com.example.demo.ws.utils;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.ws.izvestaj.IzvestajPortImpl;
import com.example.demo.ws.odgovor.OdgovorPortImpl;
import com.example.demo.ws.zalba.ZalbaPortImpl;

@Configuration
public class EndpointConfig {

	@Autowired
	private Bus bus;

	@Autowired
	private OdgovorPortImpl opi;

	@Autowired
	private IzvestajPortImpl ipi;
	
	@Autowired
	private ZalbaPortImpl zpi;

	@Bean
	public Endpoint odgovorEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(this.bus, this.opi);
		endpoint.publish("/odgovor");
		return endpoint;
	}

	@Bean
	public Endpoint izvestajEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(this.bus, this.ipi);
		endpoint.publish("/izvestaj");
		return endpoint;
	}
	
	@Bean
	public Endpoint zalbaEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(this.bus, this.zpi);
		endpoint.publish("/zalba");
		return endpoint;
	}
}
