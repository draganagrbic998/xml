package com.example.demo.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.example.demo.service.EmailService;

@RestController
@RequestMapping(value = "/email", consumes = MediaType.TEXT_XML_VALUE)
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping(produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<Void> sendEmail(@RequestBody String xml) throws ParserConfigurationException, SAXException, IOException {
		this.emailService.sendEmail(xml);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
