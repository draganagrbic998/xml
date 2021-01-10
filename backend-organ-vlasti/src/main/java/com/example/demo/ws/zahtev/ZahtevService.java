package com.example.demo.ws.zahtev;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

@WebServiceClient(name = "ZahtevService",
                  wsdlLocation = "classpath:wsdl/Zahtev.wsdl",
                  targetNamespace = "http://demo.example.com/ws/zahtev")
public class ZahtevService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://demo.example.com/ws/zahtev", "ZahtevService");
    public final static QName ZahtevPort = new QName("http://demo.example.com/ws/zahtev", "ZahtevPort");
    static {
        URL url = ZahtevService.class.getClassLoader().getResource("wsdl/Zahtev.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(ZahtevService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/Zahtev.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ZahtevService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ZahtevService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ZahtevService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ZahtevService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ZahtevService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ZahtevService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "ZahtevPort")
    public Zahtev getZahtevPort() {
        return super.getPort(ZahtevPort, Zahtev.class);
    }

    @WebEndpoint(name = "ZahtevPort")
    public Zahtev getZahtevPort(WebServiceFeature... features) {
        return super.getPort(ZahtevPort, Zahtev.class, features);
    }

}
