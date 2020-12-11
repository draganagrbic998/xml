package com.example.demo.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
		
	@PostMapping(value = "")
	public void save(@RequestBody String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException, TransformerException, XMLDBException {		
		this.zahtevService.save(xml);
	}
	
}
