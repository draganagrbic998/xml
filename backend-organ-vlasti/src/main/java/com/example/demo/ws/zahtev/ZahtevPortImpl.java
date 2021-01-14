package com.example.demo.ws.zahtev;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.parser.DOMParser;
import com.example.demo.service.ZahtevService;
import com.example.demo.transformer.ZahtevTransformer;

@javax.jws.WebService(serviceName = "ZahtevService", portName = "ZahtevPort", targetNamespace = "http://demo.example.com/ws/zahtev", wsdlLocation = "classpath:wsdl/Zahtev.wsdl", endpointInterface = "com.example.demo.ws.zahtev.Zahtev")
@Component
public class ZahtevPortImpl implements Zahtev {

	private static final Logger LOG = Logger.getLogger(ZahtevPortImpl.class.getName());

	@Autowired
	private ZahtevService zahtevService;

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private ZahtevTransformer zahtevTransformer;

	public java.lang.String getZahtev(java.lang.String getZahtevRequest) {
		LOG.info("Executing operation getZahtev");
		try {
			String documentId = this.domParser.buildDocument(getZahtevRequest)
					.getElementsByTagName("broj").item(0).getTextContent();
			java.lang.String _return = this.domParser.buildXml(this.zahtevService.load(documentId));
			return _return;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public java.lang.String getZahtevView(java.lang.String getZahtevViewRequest) {
		LOG.info("Executing operation getZahtevView");
		System.out.println(getZahtevViewRequest);
		try {
			String documentId = this.domParser.buildDocument(getZahtevViewRequest)
					.getElementsByTagName("broj").item(0).getTextContent();
			String tip = this.domParser.buildDocument(getZahtevViewRequest)
					.getElementsByTagName("tip").item(0).getTextContent();
			java.lang.String _return;
			if (tip.equals("html")) {
				_return = this.zahtevTransformer.html(documentId);
			}
			else {
				_return = this.domParser.buildXml(this.domParser.buildDocument(String.format("<pdf>%s</pdf>", this.zahtevTransformer.plainPdf(documentId))));
				System.out.println(_return);
				_return = "<asd></asd>";

			}
			return _return;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
