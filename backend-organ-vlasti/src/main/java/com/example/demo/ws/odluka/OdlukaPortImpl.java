
package com.example.demo.ws.odluka;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.parser.DOMParser;
import com.example.demo.service.OdlukaService;
import com.example.demo.transformer.OdlukaTransformer;

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
    
    @Autowired
    private OdlukaTransformer odlukaTransformer;
    
    public java.lang.String getOdluka(java.lang.String getOdlukaRequest) {
        LOG.info("Executing operation getOdluka");
        try {
			String documentId = this.domParser.buildDocument(getOdlukaRequest)
					.getElementsByTagName("broj").item(0).getTextContent();
            java.lang.String _return = this.domParser.buildXml(this.odlukaService.load(documentId));
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public java.lang.String getOdlukaView(java.lang.String getOdlukaViewRequest) {
        LOG.info("Executing operation getOdlukaView");
        System.out.println(getOdlukaViewRequest);
        try {
			String documentId = this.domParser.buildDocument(getOdlukaViewRequest)
					.getElementsByTagName("broj").item(0).getTextContent();
			String tip = this.domParser.buildDocument(getOdlukaViewRequest)
					.getElementsByTagName("tip").item(0).getTextContent();
			java.lang.String _return;
			if (tip.equals("html")) {
				_return = this.odlukaTransformer.html(documentId);
			}
			else {
				_return = this.odlukaTransformer.plainPdf(documentId);
			}
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
