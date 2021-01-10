package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.KorisnikService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
			
	@Autowired
	private KorisnikService korisnikService;
	
	@PostMapping(value = "/login", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> login(@RequestBody String xml) {
		return new ResponseEntity<>(this.korisnikService.login(xml), HttpStatus.OK);
	}
	
	@PostMapping(value = "/register", consumes = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<Void> register(@RequestBody String xml) {
		this.korisnikService.register(xml);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
