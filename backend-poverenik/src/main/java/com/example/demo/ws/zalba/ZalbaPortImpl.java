package com.example.demo.ws.zalba;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.parser.DOMParser;
import com.example.demo.service.ZalbaService;

@javax.jws.WebService(serviceName = "ZalbaService", portName = "ZalbaPort", targetNamespace = "http://demo.example.com/ws/zalba", wsdlLocation = "classpath:wsdl/Zalba.wsdl", endpointInterface = "com.example.demo.ws.zalba.Zalba")
@Component
public class ZalbaPortImpl implements Zalba {

	private static final Logger LOG = Logger.getLogger(ZalbaPortImpl.class.getName());
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private ZalbaService zalbaService;

	public void otkaziZalbu(java.lang.String otkaziZalbu) {
		LOG.info("Executing operation otkaziZalbu");
		try {
			this.zalbaService.otkazi(this.domParser.buildDocument(otkaziZalbu).getElementsByTagName("broj").item(0).getTextContent());
		} 
		catch (java.lang.Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
