
package com.example.demo.ws.odluka;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.odluka.OdlukaService;

@javax.jws.WebService(
                      serviceName = "OdlukaService",
                      portName = "OdlukaPort",
                      targetNamespace = "http://demo.example.com/ws/odluka",
                      wsdlLocation = "classpath:wsdl/Odluka.wsdl",
                      endpointInterface = "com.example.demo.ws.odluka.Odluka")

@Component
public class OdlukaPortImpl implements Odluka {

    private static final Logger LOG = Logger.getLogger(OdlukaPortImpl.class.getName());
    
    @Autowired
    private OdlukaService odlukaService;
    
    public java.lang.String getOdluka(java.lang.String getOdlukaRequest) {
        LOG.info("Executing operation getOdluka");
        try {
            java.lang.String _return = this.odlukaService.load(getOdlukaRequest);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
