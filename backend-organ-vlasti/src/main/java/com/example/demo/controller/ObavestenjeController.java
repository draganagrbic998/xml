package com.example.demo.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.service.ObavestenjeService;
import com.example.demo.service.ZahtevService;

@RestController
@RequestMapping(value = "/api/obavestenja")
public class ObavestenjeController {
	
	@Autowired
	private ObavestenjeService obavestenjeService;
			
	@PostMapping(value="/{brojZahteva}", consumes = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<Void> save(@PathVariable("brojZahteva") String brojZahteva, @RequestBody String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException {		
		this.obavestenjeService.save(brojZahteva, xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
