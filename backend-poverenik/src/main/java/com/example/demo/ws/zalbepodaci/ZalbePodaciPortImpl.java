package com.example.demo.ws.zalbepodaci;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.service.zalba.ZalbaService;


@javax.jws.WebService(
                      serviceName = "ZalbePodaciService",
                      portName = "ZalbePodaciPort",
                      targetNamespace = "http://demo.example.com/ws/zalbePodaci",
                      wsdlLocation = "classpath:wsdl/ZalbePodaci.wsdl",
                      endpointInterface = "com.example.demo.ws.zalbepodaci.ZalbePodaci")

public class ZalbePodaciPortImpl implements ZalbePodaci {

	@Autowired
	private ZalbaService zalbaService;
	
    private static final Logger LOG = Logger.getLogger(ZalbePodaciPortImpl.class.getName());

    public com.example.demo.ws.zalbepodaci.data.ZalbePodaciData getZalbePodaci(java.math.BigInteger godina) {
        LOG.info("Executing operation getZalbePodaci");
        try {
            com.example.demo.ws.zalbepodaci.data.ZalbePodaciData _return = this.zalbaService.retrieveZalbePodaci();
            return _return;
        } 
        catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
