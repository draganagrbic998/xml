package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OdgovorService;
import com.example.demo.transformer.OdgovorTransformer;

@RestController
@RequestMapping(value = "/api/odgovori")
public class OdgovorController {
	
	@Autowired
	private OdgovorService odgovorService;
	
	@Autowired
	private OdgovorTransformer odgovorTransformer;
	
	@PostMapping(consumes = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<Void> add( @RequestBody String xml) {		
		this.odgovorService.add(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	public ResponseEntity<String> html(@PathVariable String broj) {
		return new ResponseEntity<>(this.odgovorTransformer.html(broj), HttpStatus.OK);
	}

}
