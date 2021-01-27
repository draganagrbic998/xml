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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.MetadataType;
import com.example.demo.service.OdlukaService;
import com.example.demo.transformer.OdlukaTransformer;

@RestController
@RequestMapping(value = "/api/odluke")
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
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> retrieve() {
		return new ResponseEntity<>(this.odlukaService.retrieve(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> html(@PathVariable String broj) {
		return new ResponseEntity<>(this.odlukaTransfomer.html(broj), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> pdf(@PathVariable String broj) {
		Resource resource = this.odlukaTransfomer.pdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/{broj}/metadata_xml", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> xmlMetadata(@PathVariable String broj) {
		Resource resource = this.odlukaTransfomer.metadata(broj, MetadataType.xml);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/{broj}/metadata_json", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> jsonMetadata(@PathVariable String broj) {
		Resource resource = this.odlukaTransfomer.metadata(broj, MetadataType.json);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@PostMapping(value="obicna_pretraga", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> regularSearch(@RequestBody String xml) {		
		return new ResponseEntity<>(this.odlukaService.regularSearch(xml), HttpStatus.OK);
	}

	@PostMapping(value="napredna_pretraga", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> advancedSearch(@RequestBody String xml) {		
		return new ResponseEntity<>(this.odlukaService.advancedSearch(xml), HttpStatus.OK);
	}

}
