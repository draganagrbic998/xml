
package com.example.demo.ws.odluka;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.parser.DOMParser;
import com.example.demo.service.OdlukaService;

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
    
    @Autowired
    private DOMParser domParser;
    
    public java.lang.String getOdluka(java.lang.String getOdlukaRequest) {
        LOG.info("Executing operation getOdluka");
        try {
            java.lang.String _return = this.domParser.buildXml(this.odlukaService.load(getOdlukaRequest));
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public byte[] getOdlukaView(java.lang.String getOdlukaViewRequest) {
        LOG.info("Executing operation getOdlukaView");
        System.out.println(getOdlukaViewRequest);
        try {
            byte[] _return = new byte[0];
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
