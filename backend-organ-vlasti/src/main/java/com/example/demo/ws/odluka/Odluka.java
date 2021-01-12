package com.example.demo.ws.odluka;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://demo.example.com/ws/odluka", name = "Odluka")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Odluka {

    @WebMethod
    @WebResult(name = "getOdlukaResponse", targetNamespace = "http://demo.example.com/ws/odluka", partName = "getOdlukaResponse")
    public java.lang.String getOdluka(
        @WebParam(partName = "getOdlukaRequest", name = "getOdlukaRequest", targetNamespace = "http://demo.example.com/ws/odluka")
        java.lang.String getOdlukaRequest
    );
    
    @WebMethod
    @WebResult(name = "getOdlukaViewResponse", targetNamespace = "http://demo.example.com/ws/odluka", partName = "getOdlukaViewResponse")
    public byte[] getOdlukaView(
        @WebParam(partName = "getOdlukaViewRequest", name = "getOdlukaViewRequest", targetNamespace = "http://demo.example.com/ws/odluka")
        java.lang.String getOdlukaViewRequest
    );
}
