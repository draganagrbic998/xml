package com.example.demo.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.example.demo.service.KorisnikService;

@RestController
@RequestMapping(value = "/auth", consumes = MediaType.TEXT_XML_VALUE)
public class AuthController {
			
	@Autowired
	private KorisnikService korisnikService;
	
	@PostMapping(value = "/login")
	public ResponseEntity<TokenDTO> login(@RequestBody String xml) throws ParserConfigurationException, SAXException, IOException{
		return new ResponseEntity<>(new TokenDTO(this.korisnikService.login(xml)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/test", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> test() {
		return new ResponseEntity<>("<test>asd</test>", HttpStatus.OK);
	}

}