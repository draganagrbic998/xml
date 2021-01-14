package com.example.demo.ws.odluka;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://demo.example.com/ws/odluka", name = "Odluka")
@XmlSeeAlso({ ObjectFactory.class })
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Odluka {

	@WebMethod
	@WebResult(name = "getOdlukaResponse", targetNamespace = "http://demo.example.com/ws/odluka", partName = "getOdlukaResponse")
	public java.lang.String getOdluka(
			@WebParam(partName = "getOdlukaRequest", name = "getOdlukaRequest", targetNamespace = "http://demo.example.com/ws/odluka") java.lang.String getOdlukaRequest);

	@WebMethod
	@WebResult(name = "getOdlukaPdfResponse", targetNamespace = "http://demo.example.com/ws/odluka", partName = "getOdlukaPdfResponse")
	public byte[] getOdlukaPdf(
			@WebParam(partName = "getOdlukaPdfRequest", name = "getOdlukaPdfRequest", targetNamespace = "http://demo.example.com/ws/odluka") java.lang.String getOdlukaPdfRequest);

	@WebMethod
	@WebResult(name = "getOdlukaHtmlResponse", targetNamespace = "http://demo.example.com/ws/odluka", partName = "getOdlukaHtmlResponse")
	public java.lang.String getOdlukaHtml(
			@WebParam(partName = "getOdlukaHtmlRequest", name = "getOdlukaHtmlRequest", targetNamespace = "http://demo.example.com/ws/odluka") java.lang.String getOdlukaHtmlRequest);
}
