package com.example.demo.ws.izvestaj;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.IzvestajService;

@javax.jws.WebService(serviceName = "IzvestajService", portName = "IzvestajPort", targetNamespace = "http://demo.example.com/ws/izvestaj", wsdlLocation = "classpath:wsdl/Izvestaj.wsdl", endpointInterface = "com.example.demo.ws.izvestaj.Izvestaj")
@Component
public class IzvestajPortImpl implements Izvestaj {

	private static final Logger LOG = Logger.getLogger(IzvestajPortImpl.class.getName());

	@Autowired
	private IzvestajService izvestajService;

	public void createIzvestaj(java.lang.String createIzvestaj) {
		LOG.info("Executing operation createIzvestaj");
		try {
			this.izvestajService.add(createIzvestaj);
		} 
		catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
