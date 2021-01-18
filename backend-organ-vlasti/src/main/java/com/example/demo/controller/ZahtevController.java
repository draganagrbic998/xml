package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.enums.MetadataTip;
import com.example.demo.service.ZahtevService;
import com.example.demo.transformer.ZahtevTransformer;

import org.springframework.core.io.Resource;

@RestController
@RequestMapping(value = "/api/zahtevi")
public class ZahtevController {

	@Autowired
	private ZahtevService zahtevService;
	
	@Autowired
	private ZahtevTransformer zahtevTransformer;
					
	@PostMapping(consumes = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("hasAuthority('gradjanin')")
	public ResponseEntity<Void> add(@RequestBody String xml) {		
		this.zahtevService.add(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(produces = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> retrieve() {
		return new ResponseEntity<>(this.zahtevService.retrieve(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> html(@PathVariable String broj) {
		return new ResponseEntity<>(this.zahtevTransformer.html(broj), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> generatePdf(@PathVariable String broj) {
		Resource resource = this.zahtevTransformer.generatePdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/{broj}/metadata_xml", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> xmlMetadata(@PathVariable String broj) {
		Resource resource = this.zahtevTransformer.generateMetadata(broj, MetadataTip.xml);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/{broj}/metadata_json", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> jsonMetadata(@PathVariable String broj) {
		Resource resource = this.zahtevTransformer.generateMetadata(broj, MetadataTip.json);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@PostMapping(value="obicna_pretraga", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("hasAuthority('sluzbenik')")
	public ResponseEntity<String> regularSearch(@RequestBody String xml) {		
		return new ResponseEntity<>(this.zahtevService.regularSearch(xml), HttpStatus.OK);
	}

	@PostMapping(value="napredna_pretraga", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("hasAuthority('sluzbenik')")
	public ResponseEntity<String> advancedSearch(@RequestBody String xml) {		
		return new ResponseEntity<>(this.zahtevService.advancedSearch(xml), HttpStatus.OK);
	}

}
