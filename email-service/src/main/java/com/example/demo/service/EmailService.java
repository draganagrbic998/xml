package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.model.Email;
import com.example.demo.parser.JAXBParser;

@Service
public class EmailService {

	@Autowired
	private JavaMailSenderImpl sender;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Async
	public void sendEmail(String xml) {
		Email email = (Email) this.jaxbParser.unmarshal(xml, Email.class);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getText());
		this.sender.send(message);
	}
	
}
