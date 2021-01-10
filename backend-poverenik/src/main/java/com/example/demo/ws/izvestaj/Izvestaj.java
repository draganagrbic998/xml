package com.example.demo.ws.izvestaj;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://demo.example.com/ws/izvestaj", name = "Izvestaj")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Izvestaj {

    @WebMethod
    @Oneway
    public void createIzvestaj(
        @WebParam(partName = "createIzvestaj", name = "createIzvestaj", targetNamespace = "http://demo.example.com/ws/izvestaj")
        java.lang.String createIzvestaj
    );
}
