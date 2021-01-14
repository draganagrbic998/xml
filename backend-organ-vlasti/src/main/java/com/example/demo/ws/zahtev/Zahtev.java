package com.example.demo.ws.zahtev;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://demo.example.com/ws/zahtev", name = "Zahtev")
@XmlSeeAlso({ ObjectFactory.class })
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Zahtev {

	@WebMethod
	@WebResult(name = "getZahtevResponse", targetNamespace = "http://demo.example.com/ws/zahtev", partName = "getZahtevResponse")
	public java.lang.String getZahtev(
			@WebParam(partName = "getZahtevRequest", name = "getZahtevRequest", targetNamespace = "http://demo.example.com/ws/zahtev") java.lang.String getZahtevRequest);

	@WebMethod
	@WebResult(name = "getZahtevPdfResponse", targetNamespace = "http://demo.example.com/ws/zahtev", partName = "getZahtevPdfResponse")
	public byte[] getZahtevPdf(
			@WebParam(partName = "getZahtevPdfRequest", name = "getZahtevPdfRequest", targetNamespace = "http://demo.example.com/ws/zahtev") java.lang.String getZahtevPdfRequest);

	@WebMethod
	@WebResult(name = "getZahtevHtmlResponse", targetNamespace = "http://demo.example.com/ws/zahtev", partName = "getZahtevHtmlResponse")
	public java.lang.String getZahtevHtml(
			@WebParam(partName = "getZahtevHtmlRequest", name = "getZahtevHtmlRequest", targetNamespace = "http://demo.example.com/ws/zahtev") java.lang.String getZahtevHtmlRequest);
}
