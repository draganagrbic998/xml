
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.example.demo.ws.odluka;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.odluka.OdlukaService;

/**
 * This class was generated by Apache CXF 3.3.0
 * 2021-01-07T22:55:58.530+01:00
 * Generated source version: 3.3.0
 *
 */

@javax.jws.WebService(
                      serviceName = "OdlukaService",
                      portName = "OdlukaPort",
                      targetNamespace = "http://demo.example.com/ws/odluka",
                      wsdlLocation = "classpath:wsdl/Odluka.wsdl",
                      endpointInterface = "com.example.demo.ws.odluka.Odluka")

@Component
public class OdlukaPortImpl implements Odluka {

    private static final Logger LOG = Logger.getLogger(OdlukaPortImpl.class.getName());

    /* (non-Javadoc)
     * @see com.example.demo.ws.odluka.Odluka#createOdluka(java.lang.String createOdlukaRequest)*
     */
    
    @Autowired
    private OdlukaService odlukaService;
    
    public java.lang.String createOdluka(java.lang.String createOdlukaRequest) {
        LOG.info("Executing operation createOdluka");
        System.out.println(createOdlukaRequest);
        try {
            java.lang.String _return = this.odlukaService.load(createOdlukaRequest);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}