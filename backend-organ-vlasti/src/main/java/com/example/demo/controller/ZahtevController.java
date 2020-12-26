package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
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

import com.example.demo.service.ZahtevService;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping(value = "/api/zahtevi")
public class ZahtevController {

	@Autowired
	private ZahtevService zahtevService;
			
	@PostMapping(consumes = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<Void> save(@RequestBody String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException {		
		this.zahtevService.save(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/*
	@GetMapping(value = "/{documentIndex}", produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> details(@PathVariable int documentIndex) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		return new ResponseEntity<>(this.zahtevService.getHtml(documentIndex), HttpStatus.OK);
	}*/
	
	@GetMapping
	public ResponseEntity<List<ZahtevDTO>> list() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, ParserConfigurationException, SAXException, IOException{
		return new ResponseEntity<>(this.zahtevService.list(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> generatePdf(@PathVariable("broj") String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Resource resource = this.zahtevService.generatePdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
}
