package com.example.demo.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.core.io.Resource;

import com.example.demo.service.ResenjeService;

@RestController
@RequestMapping(value = "/api/resenja")
public class ResenjeContoller {

	@Autowired
	private ResenjeService resenjeService;
			
	@PostMapping(consumes = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<Void> save(@RequestBody String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException, SOAPException {		
		this.resenjeService.save(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> list() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, ParserConfigurationException, SAXException, IOException, TransformerException{
		return new ResponseEntity<>(this.resenjeService.retrieve(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	public ResponseEntity<String> html(@PathVariable String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		return new ResponseEntity<>(this.resenjeService.generateHtml(broj), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> pdf(@PathVariable String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Resource resource = this.resenjeService.generatePdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
}


