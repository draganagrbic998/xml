package com.example.demo.ws.zalba;

import java.util.logging.Logger;

@javax.jws.WebService(serviceName = "ZalbaService", portName = "ZalbaPort", targetNamespace = "http://demo.example.com/ws/zalba", wsdlLocation = "classpath:wsdl/Zalba.wsdl", endpointInterface = "com.example.demo.ws.zalba.Zalba")

public class ZalbaPortImpl implements Zalba {

	private static final Logger LOG = Logger.getLogger(ZalbaPortImpl.class.getName());

	public void otkaziZalbu(java.lang.String otkaziZalbu) {
		LOG.info("Executing operation otkaziZalbu");
		System.out.println(otkaziZalbu);
		try {
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
