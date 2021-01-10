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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.example.demo.common.MyException;
import com.example.demo.parser.DOMParser;

@Service
public class SOAPService {
	
	@Autowired
	private DOMParser domParser;
	
	private MessageFactory messageFactory;
	private SOAPFactory soapFactory;
	private SOAPConnectionFactory soapConnectionFactory;
	
	public SOAPService() throws SOAPException {
		super();
		this.messageFactory = MessageFactory.newInstance();
		this.soapFactory = SOAPFactory.newInstance();
		this.soapConnectionFactory = SOAPConnectionFactory.newInstance();
	}
	
	public String sendSOAPMessage(String brojDokumenta, Document document, SOAPDocument tipDokumenta) {
		try {
			SOAPMessage message = this.messageFactory.createMessage();
			SOAPBody body = message.getSOAPBody();
			Name name = null;
			URL endpoint = null;
			String request;
			
			if (tipDokumenta.equals(SOAPDocument.zalba)) {
				name = this.soapFactory.createName(SOAPConstants.CREATE_ZALBA_ELEMENT, "m", SOAPConstants.CREATE_ZALBA_NAMESPACE);
				endpoint = new URL(SOAPConstants.CREATE_ZALBA_SERVICE);
				request = this.domParser.buildXml(document);
			}
			else if (tipDokumenta.equals(SOAPDocument.resenje)) {
				name = this.soapFactory.createName(SOAPConstants.CREATE_RESENJE_ELEMENT, "m", SOAPConstants.CREATE_RESENJE_NAMESPACE);
				endpoint = new URL(SOAPConstants.CREATE_RESENJE_SERVICE);
				request = this.domParser.buildXml(document);
			}
			else if (tipDokumenta.equals(SOAPDocument.zahtev)) {
				name = this.soapFactory.createName(SOAPConstants.GET_ZAHTEV_ELEMENT, "m", SOAPConstants.GET_ZAHTEV_NAMESPACE);
				endpoint = new URL(SOAPConstants.GET_ZAHTEV_SERVICE);
				request = brojDokumenta;
			}
			else {
				name = this.soapFactory.createName(SOAPConstants.GET_ODLUKA_ELEMENT, "m", SOAPConstants.GET_ODLUKA_NAMESPACE);
				endpoint = new URL(SOAPConstants.GET_ODLUKA_SERVICE);
				request = brojDokumenta;
			}

			SOAPElement element = body.addChildElement(name);
			element.addTextNode(request);
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
			throw new MyException(e);
		}
	}
}
