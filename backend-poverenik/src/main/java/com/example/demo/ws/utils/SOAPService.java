package com.example.demo.ws.utils;

import java.io.StringWriter;
import java.net.MalformedURLException;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.example.demo.constants.Constants;

public class SOAPService {
	
	public static void sendSOAPMessage(Document document, String tipDokumenta) throws SOAPException, MalformedURLException, TransformerException {
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();

		SOAPBody body = message.getSOAPBody();

		SOAPFactory soapFactory = SOAPFactory.newInstance();
		
		Name name = null;
		if (tipDokumenta.equalsIgnoreCase("zalba"))
			name = soapFactory.createName(Constants.CREATE_ZALBA_ELEMENT, "m", Constants.CREATE_ZALBA_NAMESPACE);
		else
			name = soapFactory.createName(Constants.CREATE_RESENJE_ELEMENT, "m", Constants.CREATE_RESENJE_NAMESPACE);

		SOAPElement symbol = body.addChildElement(name);
		
	    DOMSource domSource = new DOMSource(document);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		StringWriter sw = new StringWriter();
		trans.transform(domSource, new StreamResult(sw));
		symbol.addTextNode(sw.toString());

		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection connection = soapConnectionFactory.createConnection();

		// Create an endpint point which is either URL or String type
		java.net.URL endpoint = null;
		
		if (tipDokumenta.equalsIgnoreCase("zalba"))
			endpoint = new URL(Constants.CREATE_ZALBA_SERVICE);
		else
			endpoint = new URL(Constants.CREATE_RESENJE_SERVICE);

		// Send a SOAPMessage (request) and then wait for SOAPMessage (response)
		connection.call(message, endpoint);
	}
}
