package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.odgovor.OdgovorService;

@RestController
@RequestMapping(value = "/api/odgovori")
public class OdgovorController {
	
	@Autowired
	private OdgovorService odgovorService;
	
	@PostMapping(consumes = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<Void> save( @RequestBody String xml) {		
		this.odgovorService.add(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
