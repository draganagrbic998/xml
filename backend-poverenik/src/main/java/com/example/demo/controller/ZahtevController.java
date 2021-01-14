package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import com.example.demo.parser.DOMParser;
import com.example.demo.ws.utils.SOAPDocument;
import com.example.demo.ws.utils.SOAPService;

@RestController
@RequestMapping(value = "/api/zahtevi")
public class ZahtevController {
	
	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private DOMParser domParser;

	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> html(@PathVariable String broj) {
		Document document = this.domParser.buildDocument(String.format("<pregled><broj>%s</broj><tip>html</tip></pregled>", broj));
		return new ResponseEntity<>(this.soapService.sendSOAPMessage(document, SOAPDocument.zahtev_html), HttpStatus.OK);
	}
		
}
