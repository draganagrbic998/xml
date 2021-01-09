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
	
	public void sendSOAPMessage(Document document, TipDokumenta tipDokumenta) {
		try {
			SOAPMessage message = this.messageFactory.createMessage();
			SOAPBody body = message.getSOAPBody();
			
			Name name = null;
			URL endpoint = null;
			if (tipDokumenta.equals(TipDokumenta.odgovor)) {
				name = this.soapFactory.createName(SOAPConstants.CREATE_ODGOVOR_ELEMENT, "m", SOAPConstants.CREATE_ODGOVOR_NAMESPACE);
				endpoint = new URL(SOAPConstants.CREATE_ODGOVOR_SERVICE);
			}
			else {
				name = this.soapFactory.createName(SOAPConstants.CREATE_ODGOVOR_ELEMENT, "m", SOAPConstants.CREATE_ODGOVOR_NAMESPACE);
				endpoint = new URL(SOAPConstants.CREATE_ODGOVOR_SERVICE);
			}

			SOAPElement symbol = body.addChildElement(name);
			symbol.addTextNode(this.domParser.buildXml(document));
			SOAPConnection connection = this.soapConnectionFactory.createConnection();
			connection.call(message, endpoint);

		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
}
