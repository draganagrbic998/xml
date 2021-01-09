package com.example.demo.ws.utils;

import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;

import com.example.demo.common.MyException;

@Service
public class SOAPService {
	
	private MessageFactory messageFactory;
	private SOAPFactory soapFactory;
	private SOAPConnectionFactory soapConnectionFactory;
	
	public SOAPService() throws SOAPException {
		super();
		this.messageFactory = MessageFactory.newInstance();
		this.soapFactory = SOAPFactory.newInstance();
		this.soapConnectionFactory = SOAPConnectionFactory.newInstance();
	}
	
	
	public String sendSOAPMessage(String xml, TipDokumenta tipDokumenta) {
		try {
			SOAPMessage message = this.messageFactory.createMessage();
			SOAPBody body = message.getSOAPBody();
			
			Name name = null;
			URL endpoint = null;
			if (tipDokumenta.equals(TipDokumenta.zalba)) {
				name = this.soapFactory.createName(SOAPConstants.CREATE_ZALBA_ELEMENT, "m", SOAPConstants.CREATE_ZALBA_NAMESPACE);
				endpoint = new URL(SOAPConstants.CREATE_ZALBA_SERVICE);
			}
			else if (tipDokumenta.equals(TipDokumenta.resenje)) {
				name = this.soapFactory.createName(SOAPConstants.CREATE_RESENJE_ELEMENT, "m", SOAPConstants.CREATE_RESENJE_NAMESPACE);
				endpoint = new URL(SOAPConstants.CREATE_RESENJE_SERVICE);
			}
			else if (tipDokumenta.equals(TipDokumenta.zahtev)) {
				name = this.soapFactory.createName(SOAPConstants.GET_ZAHTEV_ELEMENT, "m", SOAPConstants.GET_ZAHTEV_NAMESPACE);
				endpoint = new URL(SOAPConstants.GET_ZAHTEV_SERVICE);
			}
			else {
				name = this.soapFactory.createName(SOAPConstants.GET_ODLUKA_ELEMENT, "m", SOAPConstants.GET_ODLUKA_NAMESPACE);
				endpoint = new URL(SOAPConstants.GET_ODLUKA_SERVICE);
			}
			

			SOAPElement symbol = body.addChildElement(name);
			symbol.addTextNode(xml);
			SOAPConnection connection = this.soapConnectionFactory.createConnection();
			SOAPMessage response = connection.call(message, endpoint);
			try {
				return response.getSOAPBody().getTextContent();
			}
			catch(Exception e) {
				return null;
			}

		}
		catch(Exception e) {
			e.printStackTrace();
			throw new MyException(e);
		}
	}
}
