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

import com.example.demo.exception.MyException;
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
	
	public String sendSOAPMessage(Document document, SOAPActions action) {
		try {
			SOAPMessage message = this.messageFactory.createMessage();
			SOAPBody body = message.getSOAPBody();
			Name name = null;
			URL endpoint = null;
			
			if (action.equals(SOAPActions.create_zalba)) {
				name = this.soapFactory.createName(SOAPConstants.CREATE_ZALBA_ELEMENT, "m", SOAPConstants.ZALBA_NAMESPACE);
				endpoint = new URL(SOAPConstants.ZALBA_SERVICE);
			}
			else if (action.equals(SOAPActions.create_resenje)) {
				name = this.soapFactory.createName(SOAPConstants.CREATE_RESENJE_ELEMENT, "m", SOAPConstants.RESENJE_NAMESPACE);
				endpoint = new URL(SOAPConstants.RESENJE_SERVICE);
			}
			else if (action.equals(SOAPActions.get_zahtev)) {
				name = this.soapFactory.createName(SOAPConstants.GET_ZAHTEV_ELEMENT, "m", SOAPConstants.ZAHTEV_NAMESPACE);
				endpoint = new URL(SOAPConstants.ZAHTEV_SERVICE);
			}
			else if (action.equals(SOAPActions.get_odluka)) {
				name = this.soapFactory.createName(SOAPConstants.GET_ODLUKA_ELEMENT, "m", SOAPConstants.ODLUKA_NAMESPACE);
				endpoint = new URL(SOAPConstants.ODLUKA_SERVICE);
			}
			else if (action.equals(SOAPActions.zahtev_html)) {
				name = this.soapFactory.createName(SOAPConstants.GET_ZAHTEV_HTML_ELEMENT, "m", SOAPConstants.ZAHTEV_NAMESPACE);
				endpoint = new URL(SOAPConstants.ZAHTEV_SERVICE);
			}
			else if (action.equals(SOAPActions.zahtev_pdf)) {
				name = this.soapFactory.createName(SOAPConstants.GET_ZAHTEV_PDF_ELEMENT, "m", SOAPConstants.ZAHTEV_NAMESPACE);
				endpoint = new URL(SOAPConstants.ZAHTEV_SERVICE);
			}
			else if (action.equals(SOAPActions.odluka_html)) {
				name = this.soapFactory.createName(SOAPConstants.GET_ODLUKA_HTML_ELEMENT, "m", SOAPConstants.ODLUKA_NAMESPACE);
				endpoint = new URL(SOAPConstants.ODLUKA_SERVICE);
			}
			else if (action.equals(SOAPActions.odluka_pdf)) {
				name = this.soapFactory.createName(SOAPConstants.GET_ODLUKA_PDF_ELEMENT, "m", SOAPConstants.ODLUKA_NAMESPACE);
				endpoint = new URL(SOAPConstants.ODLUKA_SERVICE);
			}
			else if (action.equals(SOAPActions.zalba_odustani)) {
				name = this.soapFactory.createName(SOAPConstants.ODUSTANI_ZALBA_ELEMENT, "m", SOAPConstants.ZALBA_NAMESPACE);
				endpoint = new URL(SOAPConstants.ZALBA_SERVICE);
			}
			else {
				name = this.soapFactory.createName(SOAPConstants.OBUSTAVI_ZALBA_ELEMENT, "m", SOAPConstants.ZALBA_NAMESPACE);
				endpoint = new URL(SOAPConstants.ZALBA_SERVICE);
			}

			SOAPElement element = body.addChildElement(name);
			element.addTextNode(this.domParser.buildXml(document));
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
