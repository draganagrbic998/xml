package com.example.demo.ws.odgovor;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.3.0
 * 2021-01-06T14:32:22.380+01:00
 * Generated source version: 3.3.0
 *
 */
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