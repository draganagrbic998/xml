package com.example.demo.ws.odgovor;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://demo.example.com/ws/odgovor", name = "Odgovor")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Odgovor {

    @WebMethod
    @Oneway
    public void createOdgovor(
        @WebParam(partName = "createOdgovor", name = "createOdgovor", targetNamespace = "http://demo.example.com/ws/odgovor")
        java.lang.String createOdgovor
    );
}
