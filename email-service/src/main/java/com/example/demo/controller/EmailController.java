package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.EmailService;

@RestController
@RequestMapping(value = "/email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping(consumes = "text/xml; charset=utf-8")
	public ResponseEntity<Void> sendEmail(@RequestBody String xml) {
		this.emailService.sendEmail(xml);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
