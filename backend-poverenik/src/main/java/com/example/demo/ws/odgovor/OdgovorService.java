package com.example.demo.ws.odgovor;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.3.0
 * 2021-01-06T14:32:22.912+01:00
 * Generated source version: 3.3.0
 *
 */
@WebServiceClient(name = "OdgovorService",
                  wsdlLocation = "classpath:wsdl/Odgovor.wsdl",
                  targetNamespace = "http://demo.example.com/ws/odgovor")
public class OdgovorService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://demo.example.com/ws/odgovor", "OdgovorService");
    public final static QName OdgovorPort = new QName("http://demo.example.com/ws/odgovor", "OdgovorPort");
    static {
        URL url = OdgovorService.class.getClassLoader().getResource("wsdl/Odgovor.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(OdgovorService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/Odgovor.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public OdgovorService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OdgovorService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OdgovorService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public OdgovorService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public OdgovorService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public OdgovorService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns Odgovor
     */
    @WebEndpoint(name = "OdgovorPort")
    public Odgovor getOdgovorPort() {
        return super.getPort(OdgovorPort, Odgovor.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Odgovor
     */
    @WebEndpoint(name = "OdgovorPort")
    public Odgovor getOdgovorPort(WebServiceFeature... features) {
        return super.getPort(OdgovorPort, Odgovor.class, features);
    }

}