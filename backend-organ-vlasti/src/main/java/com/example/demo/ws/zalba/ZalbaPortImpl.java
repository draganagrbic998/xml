package com.example.demo.ws.zalba;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.ZalbaService;

@javax.jws.WebService(serviceName = "ZalbaService", portName = "ZalbaPort", targetNamespace = "http://demo.example.com/ws/zalba", wsdlLocation = "classpath:wsdl/Zalba.wsdl", endpointInterface = "com.example.demo.ws.zalba.Zalba")
@Component
public class ZalbaPortImpl implements Zalba {

	private static final Logger LOG = Logger.getLogger(ZalbaPortImpl.class.getName());

	@Autowired
	private ZalbaService zalbaService;
	
	public void createZalba(java.lang.String createZalba) {
		LOG.info("Executing operation createZalba");
		try {
			this.zalbaService.add(createZalba);
		} 
		catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
