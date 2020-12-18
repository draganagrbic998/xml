package com.example.demo.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.service.ZahtevService;

@RestController
@RequestMapping(value = "/api/zahtevi", consumes = MediaType.TEXT_XML_VALUE)
public class ZahtevController {

	@Autowired
	private ZahtevService zahtevService;
			
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, ParserConfigurationException, SAXException, IOException {		
		this.zahtevService.save(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{documentIndex}", produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> details(@PathVariable int documentIndex) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		return new ResponseEntity<>(this.zahtevService.getHtml(documentIndex), HttpStatus.OK);
	}
	
}
