package com.example.demo.ws.odgovor;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.OdgovorService;

@javax.jws.WebService(serviceName = "OdgovorService", portName = "OdgovorPort", targetNamespace = "http://demo.example.com/ws/odgovor", wsdlLocation = "classpath:wsdl/Odgovor.wsdl", endpointInterface = "com.example.demo.ws.odgovor.Odgovor")
@Component
public class OdgovorPortImpl implements Odgovor {

    private static final Logger LOG = Logger.getLogger(OdgovorPortImpl.class.getName());
    
    @Autowired
    private OdgovorService odgovorService;

    public void createOdgovor(java.lang.String createOdgovor) {
        LOG.info("Executing operation createOdgovor");
        try {
            this.odgovorService.add(createOdgovor);
        } 
        catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
