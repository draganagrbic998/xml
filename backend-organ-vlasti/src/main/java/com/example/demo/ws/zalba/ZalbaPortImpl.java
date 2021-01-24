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
	
    public void odustaniZalba(java.lang.String odustaniZalba) {
        LOG.info("Executing operation odustaniZalba");
        try {
        	this.zalbaService.odustani(this.domParser.buildDocument(odustaniZalba).getElementsByTagName("broj").item(0).getTextContent());
        } 
        catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void obustaviZalba(java.lang.String obustaviZalba) {
        LOG.info("Executing operation obustaviZalba");
        try {
        	//this.zalbaService.obustavi(this.domParser.buildDocument(obustaviZalba).getElementsByTagName("broj").item(0).getTextContent());
        } 
        catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
