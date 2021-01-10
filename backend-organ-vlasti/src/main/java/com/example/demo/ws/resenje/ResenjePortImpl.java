package com.example.demo.ws.resenje;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.ResenjeExist;

@javax.jws.WebService(serviceName = "ResenjeService", portName = "ResenjePort", targetNamespace = "http://demo.example.com/ws/resenje", wsdlLocation = "classpath:wsdl/Resenje.wsdl", endpointInterface = "com.example.demo.ws.resenje.Resenje")
@Component
public class ResenjePortImpl implements Resenje {

	private static final Logger LOG = Logger.getLogger(ResenjePortImpl.class.getName());

	@Autowired
	private ResenjeExist resenjeRepository;

	@Autowired
	private DOMParser domParser;

	public void createResenje(java.lang.String createResenje) {
		LOG.info("Executing operation createResenje");
		try {
			Document resenjeDocument = this.domParser.buildDocument(createResenje);
			this.resenjeRepository.save(null, resenjeDocument);
		} 
		catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
