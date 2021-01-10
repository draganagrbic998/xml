package com.example.demo.ws.resenje;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://demo.example.com/ws/resenje", name = "Resenje")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Resenje {

    @WebMethod
    @Oneway
    public void createResenje(
        @WebParam(partName = "createResenje", name = "createResenje", targetNamespace = "http://demo.example.com/ws/resenje")
        java.lang.String createResenje
    );
}
