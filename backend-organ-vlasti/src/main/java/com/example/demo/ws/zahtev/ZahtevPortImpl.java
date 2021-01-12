package com.example.demo.ws.zahtev;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.parser.DOMParser;
import com.example.demo.service.ZahtevService;

@javax.jws.WebService(serviceName = "ZahtevService", portName = "ZahtevPort", targetNamespace = "http://demo.example.com/ws/zahtev", wsdlLocation = "classpath:wsdl/Zahtev.wsdl", endpointInterface = "com.example.demo.ws.zahtev.Zahtev")
@Component
public class ZahtevPortImpl implements Zahtev {

	private static final Logger LOG = Logger.getLogger(ZahtevPortImpl.class.getName());

	@Autowired
	private ZahtevService zahtevService;

	@Autowired
	private DOMParser domParser;

	public java.lang.String getZahtev(java.lang.String getZahtevRequest) {
		LOG.info("Executing operation getZahtev");
		try {
			java.lang.String _return = this.domParser.buildXml(this.zahtevService.load(getZahtevRequest));
			return _return;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public byte[] getZahtevView(java.lang.String getZahtevViewRequest) {
		LOG.info("Executing operation getZahtevView");
		System.out.println(getZahtevViewRequest);
		try {
			byte[] _return = new byte[0];
			return _return;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
