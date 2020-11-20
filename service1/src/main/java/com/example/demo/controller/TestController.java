package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.parser.KorisnikParser;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
	
	@Autowired
	private KorisnikParser korisnikParser;

	@GetMapping(value = "/test1")
	public void test1() {
		this.korisnikParser.parse(null);
		
	}
	
}
