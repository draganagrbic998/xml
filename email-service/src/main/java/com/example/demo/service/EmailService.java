package com.example.demo.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Service
public class EmailService {

	@Autowired
	private JavaMailSenderImpl sender;
	
	@Autowired
	private DOMParser domParser;
	
	@Async
	public void sendEmail(String xml) throws ParserConfigurationException, SAXException, IOException {
		Document document = this.domParser.buildDocument(xml);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(document.getElementsByTagName("to").item(0).getTextContent());
		message.setSubject(document.getElementsByTagName("subject").item(0).getTextContent());
		message.setText(document.getElementsByTagName("text").item(0).getTextContent());
		this.sender.send(message);
	}
	
}
