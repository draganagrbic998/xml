package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.MetadataType;
import com.example.demo.service.OdlukaService;
import com.example.demo.transformer.OdlukaTransformer;

@RestController
@RequestMapping(value = "/api/odluke")
@PreAuthorize("isAuthenticated()")
public class OdlukaController {
	
	@Autowired
	private OdlukaService odlukaService;
	
	@Autowired
	private OdlukaTransformer odlukaTransfomer;
			
	@PostMapping(consumes = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("hasAuthority('sluzbenik')")
	public ResponseEntity<Void> add( @RequestBody String xml) {		
		this.odlukaService.add(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> findAll() {
		return new ResponseEntity<>(this.odlukaService.retrieve(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}")
	public ResponseEntity<Object> find(@PathVariable String broj, @RequestHeader("Accept") String format) {
		if (format.equals("text/html")) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8")
					.body(this.odlukaTransfomer.html(broj));
		}
		else if (format.equals("application/pdf")) {
			Resource resource = this.odlukaTransfomer.pdf(broj);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "application/pdf")
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/{broj}/metadata")
	public ResponseEntity<String> metadata(@PathVariable String broj, @RequestHeader("Accept") String format) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, format + "; charset=utf-8")
				.body(this.odlukaTransfomer.metadata(broj,
						format.equals("text/xml") ? MetadataType.xml : MetadataType.json));
	}

	@PostMapping(value="obicna_pretraga", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> regularSearch(@RequestBody String xml) {		
		return new ResponseEntity<>(this.odlukaService.regularSearch(xml), HttpStatus.OK);
	}

	@PostMapping(value="napredna_pretraga", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> advancedSearch(@RequestBody String xml) {		
		return new ResponseEntity<>(this.odlukaService.advancedSearch(xml), HttpStatus.OK);
	}

}
