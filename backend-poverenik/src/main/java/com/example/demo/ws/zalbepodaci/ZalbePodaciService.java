package com.example.demo.ws.zalbepodaci;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

@WebServiceClient(name = "ZalbePodaciService",
                  wsdlLocation = "classpath:wsdl/ZalbePodaci.wsdl",
                  targetNamespace = "http://demo.example.com/ws/zalbePodaci")
public class ZalbePodaciService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://demo.example.com/ws/zalbePodaci", "ZalbePodaciService");
    public final static QName ZalbePodaciPort = new QName("http://demo.example.com/ws/zalbePodaci", "ZalbePodaciPort");
    static {
        URL url = ZalbePodaciService.class.getClassLoader().getResource("wsdl/ZalbePodaci.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(ZalbePodaciService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/ZalbePodaci.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ZalbePodaciService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ZalbePodaciService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ZalbePodaciService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ZalbePodaciService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ZalbePodaciService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ZalbePodaciService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "ZalbePodaciPort")
    public ZalbePodaci getZalbePodaciPort() {
        return super.getPort(ZalbePodaciPort, ZalbePodaci.class);
    }

    @WebEndpoint(name = "ZalbePodaciPort")
    public ZalbePodaci getZalbePodaciPort(WebServiceFeature... features) {
        return super.getPort(ZalbePodaciPort, ZalbePodaci.class, features);
    }

}
