package com.example.demo.ws.zalba;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

@WebServiceClient(name = "ZalbaService",
                  wsdlLocation = "classpath:wsdl/Zalba.wsdl",
                  targetNamespace = "http://demo.example.com/ws/zalba")
public class ZalbaService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://demo.example.com/ws/zalba", "ZalbaService");
    public final static QName ZalbaPort = new QName("http://demo.example.com/ws/zalba", "ZalbaPort");
    static {
        URL url = ZalbaService.class.getClassLoader().getResource("wsdl/Zalba.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(ZalbaService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/Zalba.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ZalbaService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ZalbaService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ZalbaService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ZalbaService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ZalbaService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ZalbaService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "ZalbaPort")
    public Zalba getZalbaPort() {
        return super.getPort(ZalbaPort, Zalba.class);
    }

    @WebEndpoint(name = "ZalbaPort")
    public Zalba getZalbaPort(WebServiceFeature... features) {
        return super.getPort(ZalbaPort, Zalba.class, features);
    }

}
