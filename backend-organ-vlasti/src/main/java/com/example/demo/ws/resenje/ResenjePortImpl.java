package com.example.demo.ws.resenje;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.ResenjeService;

@javax.jws.WebService(serviceName = "ResenjeService", portName = "ResenjePort", targetNamespace = "http://demo.example.com/ws/resenje", wsdlLocation = "classpath:wsdl/Resenje.wsdl", endpointInterface = "com.example.demo.ws.resenje.Resenje")
@Component
public class ResenjePortImpl implements Resenje {

	private static final Logger LOG = Logger.getLogger(ResenjePortImpl.class.getName());

	@Autowired
	private ResenjeService resenjeService;

	public void createResenje(java.lang.String createResenje) {
		LOG.info("Executing operation createResenje");
		try {
			this.resenjeService.add(createResenje);
		} 
		catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
