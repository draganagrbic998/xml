package com.example.demo.ws.odluka;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

@WebServiceClient(name = "OdlukaService",
                  wsdlLocation = "classpath:wsdl/Odluka.wsdl",
                  targetNamespace = "http://demo.example.com/ws/odluka")
public class OdlukaService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://demo.example.com/ws/odluka", "OdlukaService");
    public final static QName OdlukaPort = new QName("http://demo.example.com/ws/odluka", "OdlukaPort");
    static {
        URL url = OdlukaService.class.getClassLoader().getResource("wsdl/Odluka.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(OdlukaService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/Odluka.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public OdlukaService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OdlukaService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OdlukaService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public OdlukaService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public OdlukaService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public OdlukaService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "OdlukaPort")
    public Odluka getOdlukaPort() {
        return super.getPort(OdlukaPort, Odluka.class);
    }

    @WebEndpoint(name = "OdlukaPort")
    public Odluka getOdlukaPort(WebServiceFeature... features) {
        return super.getPort(OdlukaPort, Odluka.class, features);
    }

}
