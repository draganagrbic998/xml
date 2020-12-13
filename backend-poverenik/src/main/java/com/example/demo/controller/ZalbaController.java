package com.example.demo.controller;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.service.ZalbaService;

@RestController
@RequestMapping(value = "/api/zalbe", consumes = MediaType.TEXT_XML_VALUE)
public class ZalbaController {

	@Autowired
	private ZalbaService zalbaService;
		
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException {		
		this.zalbaService.save(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
