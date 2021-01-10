package com.example.demo.ws.zalba;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://demo.example.com/ws/zalba", name = "Zalba")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Zalba {

    @WebMethod
    @Oneway
    public void createZalba(
        @WebParam(partName = "createZalba", name = "createZalba", targetNamespace = "http://demo.example.com/ws/zalba")
        java.lang.String createZalba
    );
}
