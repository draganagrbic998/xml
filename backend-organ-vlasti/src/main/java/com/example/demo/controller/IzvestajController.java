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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.MetadataTip;
import com.example.demo.service.IzvestajService;
import com.example.demo.transformer.IzvestajTransformer;

@RestController
@RequestMapping(value = "/api/izvestaji")
public class IzvestajController {
	
	@Autowired
	private IzvestajService izvestajService;
	
	@Autowired
	private IzvestajTransformer izvestajTransformer;
	
	@PostMapping(value = "/{godina}")
	@PreAuthorize("hasAuthority('sluzbenik')")
	public ResponseEntity<Void> add(@PathVariable String godina) {
		this.izvestajService.add(godina);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(produces = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("hasAuthority('sluzbenik')")
	public ResponseEntity<String> retrieve() {
		return new ResponseEntity<>(this.izvestajService.retrieve(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	@PreAuthorize("hasAuthority('sluzbenik')")
	public ResponseEntity<String> html(@PathVariable String broj) {
		return new ResponseEntity<>(this.izvestajTransformer.html(broj), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> generatePdf(@PathVariable String broj) {
		Resource resource = this.izvestajTransformer.generatePdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/{broj}/metadata/xml", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> xmlMetadata(@PathVariable String broj) {
		Resource resource = this.izvestajTransformer.generateMetadata(broj, MetadataTip.xml);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/{broj}/metadata/json", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> jsonMetadata(@PathVariable String broj) {
		Resource resource = this.izvestajTransformer.generateMetadata(broj, MetadataTip.json);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
