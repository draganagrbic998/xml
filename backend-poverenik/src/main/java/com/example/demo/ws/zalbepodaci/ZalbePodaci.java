package com.example.demo.ws.zalbepodaci;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://demo.example.com/ws/zalbePodaci", name = "ZalbePodaci")
@XmlSeeAlso({com.example.demo.ws.zalbepodaci.data.ObjectFactory.class})
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ZalbePodaci {

    @WebMethod
    @WebResult(name = "result", targetNamespace = "http://demo.example.com/ws/zalbePodaci", partName = "result")
    public com.example.demo.ws.zalbepodaci.data.ZalbePodaciData getZalbePodaci(
        @WebParam(partName = "godina", name = "godina")
        java.math.BigInteger godina
    );
}
